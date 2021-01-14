package id.train.sportaria.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import id.train.sportaria.data.local.MainDatabase
import id.train.sportaria.data.local.dao.FootballDao
import id.train.sportaria.data.local.dao.SportDao
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object LocalDatabaseModule {

    @Singleton
    @Provides
    fun providesRoomDatabase(@ApplicationContext context: Context): MainDatabase {
        return Room.databaseBuilder(
            context,
            MainDatabase::class.java,
            MainDatabase.DB_NAME
        ).fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    fun providesSportDao(database: MainDatabase): SportDao {
        return database.getSportDao()
    }

    @Singleton
    @Provides
    fun providesFootballDao(database: MainDatabase): FootballDao {
        return database.getFootballDao()
    }
}