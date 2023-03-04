package yellowc.app.flo_clone.data.remote.repositoryimpl

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import yellowc.app.flo_clone.data.remote.datasource.RemoteDataSource
import yellowc.app.flo_clone.data.remote.response.FloMusicResponse
import yellowc.app.flo_clone.di.DispatcherModule
import yellowc.app.flo_clone.domain.model.Music
import yellowc.app.flo_clone.domain.model.MyResult
import yellowc.app.flo_clone.domain.repositories.MusicRepository
import javax.inject.Inject

class MusicRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    @DispatcherModule.DispatcherIO private val dispatcherIO: CoroutineDispatcher
) : MusicRepository {
    override suspend fun getMusic(): Music {
        val result = ArrayList<Music>()
        withContext(dispatcherIO) {
            val job = async {
                remoteDataSource.getMusic()
            }
            val music: FloMusicResponse
            when (val responses = job.await()) {
                is MyResult.Success -> {
                    music = responses.data
                }
                is MyResult.Error -> {
                    return@withContext
                }
            }
            val resultMusic = Music(
                album = music.album,
                duration = music.duration,
                `file` = music.file,
                image = music.image,
                lyrics = music.lyrics,
                singer = music.singer,
                title = music.title

            )
            result.add(resultMusic)

        }
        return result[0]
        //현재는 1개의 데이터
    }


}