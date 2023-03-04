package yellowc.app.flo_clone.ui.main


import android.os.Bundle
import android.util.DisplayMetrics
import android.view.ViewTreeObserver
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.util.Util
import com.google.android.material.bottomsheet.BottomSheetBehavior
import timber.log.Timber
import yellowc.app.flo_clone.R
import yellowc.app.flo_clone.databinding.ActivityMainBinding
import yellowc.app.flo_clone.domain.model.Music
import yellowc.app.flo_clone.domain.model.Lyric

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()
    var height = 0
    private lateinit var music: Music
    private lateinit var binding: ActivityMainBinding
    private var player: ExoPlayer? = null
    private lateinit var adapter: LyricAdapter
    var lastindex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        music = intent.getSerializableExtra("music") as Music
        binding.vm = viewModel
        binding.music = music
        binding.btmSheetLyric.music = music
        binding.lifecycleOwner = this
    }

    override fun onStart() {
        super.onStart()
        setPlayer()
        setBottomSheet()
    }

    private fun setBottomSheet() {
        binding.apply {
            val bottomSheetBehaviors = BottomSheetBehavior.from(binding.btmSheetLyric.root)
            binding.bottomSheetBehavior = bottomSheetBehaviors
            if (height == 0) {
                playercontrol.viewTreeObserver.addOnGlobalLayoutListener {
                    height = playercontrol.height
                }
            }

            btmSheetLyric.apply {
                bottomSheetBehaviors.peekHeight = 0
                btmPlayer.player = binding.playercontrol.player
                btmPlayer.viewTreeObserver.addOnGlobalLayoutListener(object :
                    ViewTreeObserver.OnGlobalLayoutListener {
                    override fun onGlobalLayout() {
                        btmPlayer.viewTreeObserver.removeOnGlobalLayoutListener(this)
                        val displayMetrics = DisplayMetrics()
                        windowManager.defaultDisplay.getMetrics(displayMetrics)
                        btmPlayer.layoutParams.height = height
                        btmPlayer.requestLayout()
                    }
                })
                btmPlayer.controllerShowTimeoutMs = 0
                bottomSheetBehavior = bottomSheetBehaviors
            }
        }
    }

    private fun setPlayer() {
        if (player == null) {
            player = ExoPlayer.Builder(this).build()
        }
        binding.apply {

            playercontrol.player = player
            val musicItem = MediaItem.fromUri(music!!.file)
            player!!.setMediaItem(musicItem)
            var lyricsList = arrayListOf<Lyric>()

            music!!.lyrics.split("\n").map { cue ->
                val time = viewModel.parseTime(cue.substring(1, 10))
                val text = cue.substring(11)
                lyricsList.add(Lyric(text, time, false))
            }
            setAdapter(lyricsList)

            playercontrol.setProgressUpdateListener { position, bufferedPosition ->
                val lyrics = viewModel.getCurrentLyric(position, lyricsList)
                if (lyrics.index != lastindex && lyrics.index !=0) {
                    tvLyricBefore.text = lyrics.firstLyric
                    tvLyricNow.text = lyrics.secondLyric
                    tvLyricAfter.text = lyrics.thirdLyric
                    lyricsList[lyrics.index] =
                        Lyric(lyricsList[lyrics.index].text, lyricsList[lyrics.index].start, true)
                    lyricsList[lastindex] =
                        Lyric(lyricsList[lastindex].text, lyricsList[lastindex].start, false)
                    adapter.notifyDataSetChanged()
                    lastindex = lyrics.index
                }
            }

            playercontrol.showTimeoutMs = 0
            playercontrol.show()
        }
    }


    private fun setAdapter(lyricsList: ArrayList<Lyric>) {
        adapter = LyricAdapter(itemClicked = {
            player!!.seekTo(it.start)
        })
        adapter.submitList(lyricsList)
        binding.btmSheetLyric.btmRcv.adapter = adapter
    }


    override fun onResume() {
        super.onResume()
        if (Util.SDK_INT <= 23 || player == null) {
            setPlayer()
        }
    }

    override fun onPause() {
        super.onPause()
        if (Util.SDK_INT <= 23) {
            releasePlayer()
        }
    }

    private fun releasePlayer() {
        player?.release()
        player = null
    }

    override fun onStop() {
        super.onStop()
        if (Util.SDK_INT <= 23) {
            releasePlayer()
        }
    }

}