package com.williamoviedo.themoviedb.interfaces

import com.williamoviedo.themoviedb.domain.ItemsMovie
import com.williamoviedo.themoviedb.domain.ResultsMovie

interface DataInterfaceMovies {
    fun detailMovies(movieDetail : ItemsMovie)

    fun mainMovies(resultsMovie: ResultsMovie, categoryMovie: String)
}