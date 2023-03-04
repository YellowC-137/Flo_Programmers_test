package yellowc.app.flo_clone.data.remote.api

import retrofit2.Response
import retrofit2.http.GET
import yellowc.app.flo_clone.data.remote.response.FloMusicResponse

interface MusicService {
    @GET("song.json")
    suspend fun getMusic():Response<FloMusicResponse>
}