package yellowc.app.flo_clone.ui.splash

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import yellowc.app.flo_clone.R
import yellowc.app.flo_clone.databinding.ActivitySplashBinding
import yellowc.app.flo_clone.domain.model.Music
import yellowc.app.flo_clone.ui.main.MainActivity

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    private val viewModel: SplashViewModel by viewModels()
    private lateinit var music : Music
    private lateinit var binding: ActivitySplashBinding
    val splash_time: Long = 2000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        setContentView(binding.root)
        viewModel.getMusic()
        val intent = Intent(this, MainActivity::class.java)
        collectFlow(intent)

    }
    private fun collectFlow(intent: Intent) {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.music.collectLatest {
                    if (it.isNotEmpty()) {
                        music = it[0]
                            intent.putExtra("music",music!!)
                            Handler().postDelayed({
                                startActivity(intent)
                            }, splash_time)

                    }
                }
            }
        }
    }

}