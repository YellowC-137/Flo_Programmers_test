package yellowc.app.flo_clone.domain.usecases

import yellowc.app.flo_clone.domain.repositories.MusicRepository
import javax.inject.Inject

class GetMusicUseCase @Inject constructor(private val musicRepository: MusicRepository) {
    suspend operator fun invoke() = musicRepository.getMusic()
}