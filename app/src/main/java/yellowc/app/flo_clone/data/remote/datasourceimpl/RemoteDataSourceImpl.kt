package yellowc.app.flo_clone.data.remote.datasourceimpl

import yellowc.app.flo_clone.data.remote.api.MusicService
import yellowc.app.flo_clone.data.remote.datasource.RemoteDataSource
import yellowc.app.flo_clone.data.remote.response.FloMusicResponse
import yellowc.app.flo_clone.domain.model.MyResult
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val musicService: MusicService
) : RemoteDataSource {
    override suspend fun getMusic(): MyResult<FloMusicResponse> {
        val response = musicService.getMusic()
        return try {
            if (response.isSuccessful) {
                MyResult.Success(response.body()!!)
            } else {
                MyResult.Error(java.lang.IllegalArgumentException("ERROR"))
            }
        } catch (e: Exception) {
            MyResult.Error(e)
        }
    }

}