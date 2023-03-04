package yellowc.app.flo_clone.domain.repositories

import yellowc.app.flo_clone.domain.model.Music

interface MusicRepository {
    suspend fun getMusic():Music
}