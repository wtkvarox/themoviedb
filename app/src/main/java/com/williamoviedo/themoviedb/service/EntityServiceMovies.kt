package com.williamoviedo.themoviedb.service

import com.williamoviedo.themoviedb.domain.ItemsMovie
import com.williamoviedo.themoviedb.domain.ResultsMovie
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface EntityServiceMovies {
    @GET("movie/{movie}")
    fun getMovies(
        @Path("movie") movie: String,
        @Query("api_key") apiKey: String?,
        @Query("language") language: String,
        @Query("pageNum") id: Int?
    ): Call<ResultsMovie>

    @GET("movie/{movie_id}")
    fun getMovieDetail(
        @Path("movie_id") movie: Long,
        @Query("api_key") apiKey: String?,
        @Query("language") language: String
    ): Call<ItemsMovie>
}