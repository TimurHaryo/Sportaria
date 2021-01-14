package id.train.sportaria.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import id.train.sportaria.abstaction.repository.MainFootballRepository
import id.train.sportaria.abstaction.repository.MainSportRepository
import id.train.sportaria.domain.repository.MainFootballRepositoryImpl
import id.train.sportaria.domain.repository.MainSportRepositoryImpl

@Module
@InstallIn(ApplicationComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindMainSportRepository(repository: MainSportRepositoryImpl): MainSportRepository

    @Binds
    abstract fun bindMainFootballRepository(repository: MainFootballRepositoryImpl): MainFootballRepository

}