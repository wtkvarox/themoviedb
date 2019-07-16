package com.williamoviedo.themoviedb.service

import com.williamoviedo.themoviedb.domain.ItemTvSerie
import com.williamoviedo.themoviedb.domain.ResultsTvSerie
import com.williamoviedo.themoviedb.interfaces.DataInterfaceMovies
import com.williamoviedo.themoviedb.interfaces.DataInterfaceTvSeries
import com.williamoviedo.themoviedb.util.Constant
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ServiceSeries(private var dataInterfaceTvSeries: DataInterfaceTvSeries) {
    private var callService: EntityServiceSeries

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        callService = retrofit.create(EntityServiceSeries::class.java)
    }


    fun getTvSerie(categoryTvSerie: String, pageNum: Int) {

        val call: Call<ResultsTvSerie> =
            callService.getTvSeries(categoryTvSerie, Constant.API_KEY_TMDB, Constant.LANGUAGE_DEFAULT, pageNum)

        call.enqueue(object : Callback<ResultsTvSerie> {
            override fun onResponse(call: Call<ResultsTvSerie>, response: Response<ResultsTvSerie>) {
                if (response.code() == 200) {
                    val tvSerieDetail = response.body()!!
                    dataInterfaceTvSeries.mainTvSeries(tvSerieDetail, categoryTvSerie)
                }
            }

            override fun onFailure(call: Call<ResultsTvSerie>, t: Throwable) {
                // Sin implementacion
            }
        })
    }


    fun getTvSerieDetail(idTvSerie: Long) {

        val call: Call<ItemTvSerie> =
            callService.getTvSerieDetail(idTvSerie, Constant.API_KEY_TMDB, Constant.LANGUAGE_DEFAULT)

        call.enqueue(object : Callback<ItemTvSerie> {
            override fun onResponse(call: Call<ItemTvSerie>, response: Response<ItemTvSerie>) {
                if (response.code() == 200) {
                    val tvSerieDetail = response.body()!!
                    dataInterfaceTvSeries.detailTvSeries(tvSerieDetail)
                }
            }

            override fun onFailure(call: Call<ItemTvSerie>, t: Throwable) {
                // Sin implementacion
            }
        })
    }
}