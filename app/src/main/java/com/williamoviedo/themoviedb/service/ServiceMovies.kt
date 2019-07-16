package com.williamoviedo.themoviedb.service

import com.williamoviedo.themoviedb.domain.ItemsMovie
import com.williamoviedo.themoviedb.domain.ResultsMovie
import com.williamoviedo.themoviedb.interfaces.DataInterfaceMovies
import com.williamoviedo.themoviedb.util.Constant
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ServiceMovies(private var dataInterfaceMovies: DataInterfaceMovies) {

    private var callService: EntityServiceMovies

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        callService = retrofit.create(EntityServiceMovies::class.java)
    }

    fun getMovies(categoryMovie: String, pageNum: Int) {

        val call: Call<ResultsMovie> =
            callService.getMovies(categoryMovie, Constant.API_KEY_TMDB, Constant.LANGUAGE_DEFAULT, pageNum)

        call.enqueue(object : Callback<ResultsMovie> {
            override fun onResponse(call: Call<ResultsMovie>, response: Response<ResultsMovie>) {
                if (response.code() == 200) {
                    val movieDetail = response.body()!!
                    dataInterfaceMovies.mainMovies(movieDetail, categoryMovie)
                }
            }

            override fun onFailure(call: Call<ResultsMovie>, t: Throwable) {
                // Sin implementacion
            }
        })
    }

    fun getMovieDetail(idMovie: Long) {

        val call: Call<ItemsMovie> =
            callService.getMovieDetail(idMovie, Constant.API_KEY_TMDB, Constant.LANGUAGE_DEFAULT)

        call.enqueue(object : Callback<ItemsMovie> {
            override fun onResponse(call: Call<ItemsMovie>, response: Response<ItemsMovie>) {
                if (response.code() == 200) {
                    val movieDetail = response.body()!!
                    dataInterfaceMovies.detailMovies(movieDetail)
                }
            }

            override fun onFailure(call: Call<ItemsMovie>, t: Throwable) {
                // Sin implementacion
            }
        })
    }
}