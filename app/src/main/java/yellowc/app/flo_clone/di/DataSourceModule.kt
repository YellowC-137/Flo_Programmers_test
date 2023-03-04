package yellowc.app.flo_clone.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import yellowc.app.flo_clone.data.remote.datasource.RemoteDataSource
import yellowc.app.flo_clone.data.remote.datasourceimpl.RemoteDataSourceImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun bindRemoteDataSource(
        RemoteDataSourceImpl: RemoteDataSourceImpl,
    ): RemoteDataSource

}