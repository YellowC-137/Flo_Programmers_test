package yellowc.app.flo_clone.ui.main


import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import yellowc.app.flo_clone.domain.model.Lyric
import yellowc.app.flo_clone.domain.model.Music

class MainViewModel() : ViewModel() {

    fun parseTime(timecode: String): Long {
        val parts = timecode.split(":").map { it.toLong() }
        val minutes = parts[0]
        val seconds = parts[1]
        val milliseconds = parts[2]
        return ((minutes * 60 + seconds) * 1000 + milliseconds)
    }

    fun getCurrentLyric(
        currentTime: Long,
        lyrics: ArrayList<Lyric>
    ): Four {
        var before = ""
        var now = ""
        var after = ""
        var index = 0
        for (i in 0 until lyrics.size) {
            if (lyrics[i].start <= currentTime) {
                now = lyrics[i].text
                index = i
                if (i > 0) {
                    before = lyrics[i - 1].text
                }
                if (i < lyrics.size - 1) {
                    after = lyrics[i + 1].text
                }
            } else {
                break
            }
        }
        return Four(before, now, after, index)
    }

}

data class Four(
    val firstLyric: String,
    val secondLyric: String,
    val thirdLyric: String,
    val index: Int
)