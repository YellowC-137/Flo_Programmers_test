package yellowc.app.flo_clone.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import yellowc.app.flo_clone.data.remote.repositoryimpl.MusicRepositoryImpl
import yellowc.app.flo_clone.domain.repositories.MusicRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindRemoteRepository(
        RepositoryImpl: MusicRepositoryImpl,
    ): MusicRepository
}