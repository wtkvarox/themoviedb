package com.williamoviedo.themoviedb.service

import com.williamoviedo.themoviedb.domain.ItemTvSerie
import com.williamoviedo.themoviedb.domain.ResultsMovie
import com.williamoviedo.themoviedb.domain.ResultsTvSerie
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface EntityServiceSeries {
    @GET("tv/{series}")
    fun getTvSeries(
        @Path("series") series: String,
        @Query("api_key") apiKey: String?,
        @Query("language") language: String,
        @Query("pageNum") id: Int?
    ): Call<ResultsTvSerie>

    @GET("tv/{tv_id}")
    fun getTvSerieDetail(
        @Path("tv_id") tvId: Long,
        @Query("api_key") apiKey: String?,
        @Query("language") language: String
    ): Call<ItemTvSerie>
}