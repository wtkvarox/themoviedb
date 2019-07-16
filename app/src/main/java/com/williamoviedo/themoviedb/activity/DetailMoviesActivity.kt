package com.williamoviedo.themoviedb.activity

import android.app.Dialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.squareup.picasso.Picasso
import com.williamoviedo.themoviedb.interfaces.DataInterfaceMovies
import com.williamoviedo.themoviedb.R
import com.williamoviedo.themoviedb.database.SaveDataBase
import com.williamoviedo.themoviedb.domain.ItemsMovie
import com.williamoviedo.themoviedb.domain.ItemTvSerie
import com.williamoviedo.themoviedb.domain.ResultsMovie
import com.williamoviedo.themoviedb.domain.ResultsTvSerie
import com.williamoviedo.themoviedb.interfaces.DataInterfaceTvSeries
import com.williamoviedo.themoviedb.service.ServiceMovies
import com.williamoviedo.themoviedb.service.ServiceSeries
import com.williamoviedo.themoviedb.util.Constant
import org.jetbrains.anko.indeterminateProgressDialog
import org.jetbrains.anko.sdk27.coroutines.onClick

class DetailMoviesActivity : AppCompatActivity()
    , DataInterfaceMovies
    , DataInterfaceTvSeries {

    // Vistas
    private lateinit var imgMovie: ImageView
    private lateinit var txtTitleMovie: TextView
    private lateinit var txtTitleBar: TextView
    private lateinit var txtOriginalTitleMovie: TextView
    private lateinit var ratingBar: RatingBar
    private lateinit var imgBackOption: ImageView

    // Globales
    private lateinit var txtOverview: TextView
    private var dataBase = SaveDataBase()
    private var idItem: Long = 0L
    private lateinit var progressDialog: Dialog
    private var isTvSerie: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies_detail)

        idItem = intent.getLongExtra(Constant.KEY_ID_ITEM_DETAIL, 0L)
        isTvSerie = intent.getBooleanExtra(Constant.KEY_ID_ITEM_TYPE, false)

        imgMovie = findViewById(R.id.img_movie)
        txtTitleMovie = findViewById(R.id.txt_title_movie)
        txtTitleBar = findViewById(R.id.txt_title)
        txtOriginalTitleMovie = findViewById(R.id.txt_original_title)
        ratingBar = findViewById(R.id.rating_movie)
        txtOverview = findViewById(R.id.txt_overview)
        imgBackOption = findViewById(R.id.img_arrow)

        imgBackOption.onClick { finish() }

        progressDialog = indeterminateProgressDialog(getString(R.string.dialog_get_info))
        progressDialog.show()

        // Llamar servicios
        val serviceMovies = ServiceMovies(this)
        val serviceSeries = ServiceSeries(this)

        if (!isTvSerie)
            serviceMovies.getMovieDetail(idItem)
        else
            serviceSeries.getTvSerieDetail(idItem)
    }


    /**
     * Al obtener respuesta del servidor, guardar en base de datos y fijar vistas
     */
    override fun detailMovies(movieDetail: ItemsMovie) {

        // Guardar en la base de datos usando Realm
        dataBase.saveDataBase(movieDetail)

        // Fijar datos
        txtTitleMovie.text = movieDetail.title
        txtOriginalTitleMovie.text = movieDetail.originalTitle
        ratingBar.rating = (movieDetail.voteAverage / 2)
        txtOverview.text = movieDetail.overview
        txtTitleBar.text = movieDetail.title

        Picasso.get().load(Constant.BASE_URL_IMAGES_POSTER + movieDetail.backdropPath).into(imgMovie)

        progressDialog.dismiss()
    }


    /**
     * Al obtener respuesta del servidor, guardar en base de datos y fijar vistas
     */
    override fun detailTvSeries(tvSerieDetail: ItemTvSerie) {

        // Guardar en la base de datos usando Realm
        dataBase.saveDataBase(tvSerieDetail)

        // Fijar datos
        txtTitleMovie.text = tvSerieDetail.name
        txtOriginalTitleMovie.text = tvSerieDetail.originalName
        ratingBar.rating = (tvSerieDetail.voteAverage / 2)
        txtOverview.text = tvSerieDetail.overview
        txtTitleBar.text = tvSerieDetail.name

        Picasso.get().load(Constant.BASE_URL_IMAGES_POSTER + tvSerieDetail.backdropPath).into(imgMovie)

        progressDialog.dismiss()
    }

    override fun mainMovies(resultsMovie: ResultsMovie, categoryMovie: String) {
        // Sin implementacion
    }

    override fun mainTvSeries(resultsTvSerie: ResultsTvSerie, categoryTvSeries: String) {
        // Sin implementacion
    }
}