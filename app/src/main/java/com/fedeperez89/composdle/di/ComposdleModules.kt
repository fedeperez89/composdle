package com.fedeperez89.composdle.di

import android.content.Context
import androidx.room.Room
import com.fedeperez89.composdle.feature_game.data.data_source.WordClient
import com.fedeperez89.composdle.feature_game.data.data_source.WordDatabase
import com.fedeperez89.composdle.feature_game.data.data_source.WordsDao
import com.fedeperez89.composdle.feature_game.data.repository.WordRepositoryImpl
import com.fedeperez89.composdle.feature_game.domain.repository.WordRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ComposdleModules {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): WordDatabase {
        return Room.databaseBuilder(
            context,
            WordDatabase::class.java, WordDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    fun provideShotsDao(database: WordDatabase): WordsDao {
        return database.shotsDao
    }

    @Provides
    fun provideIODispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    fun provideWordClient(): WordClient {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://raw.githubusercontent.com/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()

        return retrofit.create(WordClient::class.java)
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class FeatureGameModule {

    @Binds
    abstract fun bindWordRepository(shotRepositoryImpl: WordRepositoryImpl): WordRepository
}