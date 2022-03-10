package com.fedeperez89.composdle.di

import com.fedeperez89.composdle.feature_game.data.repository.InMemoryWordRepository
import com.fedeperez89.composdle.feature_game.domain.repository.WordRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

//@Module
//@InstallIn(SingletonComponent::class)
//object ComposdleModules {
//
//    @Provides
//}

@Module
@InstallIn(SingletonComponent::class)
abstract class FeatureGameModule {

    @Binds
    abstract fun bindWordRepository(shotRepositoryImpl: InMemoryWordRepository): WordRepository
}