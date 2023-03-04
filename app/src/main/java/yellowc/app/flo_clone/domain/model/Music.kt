package yellowc.app.flo_clone.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@kotlinx.serialization.Serializable
data class Music(
    val album: String,
    val duration: Int,
    val `file`: String,
    val image: String,
    val lyrics: String,
    val singer: String,
    val title: String
) : Serializable