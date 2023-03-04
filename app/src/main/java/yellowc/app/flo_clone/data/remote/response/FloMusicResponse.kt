package yellowc.app.flo_clone.data.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class FloMusicResponse(
    val album: String,
    val duration: Int,
    val `file`: String,
    val image: String,
    val lyrics: String,
    val singer: String,
    val title: String
)