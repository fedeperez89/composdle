package com.fedeperez89.composdle.feature_game.data.data_source

import retrofit2.http.GET

interface WordClient {

    @GET("dwyl/english-words/master/words.txt")
    suspend fun getWordList(): String

}