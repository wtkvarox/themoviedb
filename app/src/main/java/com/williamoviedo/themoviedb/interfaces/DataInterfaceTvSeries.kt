package com.williamoviedo.themoviedb.interfaces

import com.williamoviedo.themoviedb.domain.ItemTvSerie
import com.williamoviedo.themoviedb.domain.ResultsTvSerie

interface DataInterfaceTvSeries {
    fun detailTvSeries(tvSerieDetail: ItemTvSerie)

    fun mainTvSeries(resultsTvSerie: ResultsTvSerie, categoryTvSeries: String)
}