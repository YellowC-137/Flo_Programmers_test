package yellowc.app.flo_clone.data.remote.datasource

import yellowc.app.flo_clone.data.remote.response.FloMusicResponse
import yellowc.app.flo_clone.domain.model.MyResult

interface RemoteDataSource {

    suspend fun getMusic(): MyResult<FloMusicResponse>

}