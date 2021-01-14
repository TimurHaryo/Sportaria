package id.train.sportaria.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import id.train.sportaria.util.pref.SharedPref
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object SharedPreferenceModule {

    @Singleton
    @Provides
    fun providesSharedPreference(@ApplicationContext context: Context) =  SharedPref(context)
}