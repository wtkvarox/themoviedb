package com.williamoviedo.themoviedb.activity

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import com.williamoviedo.themoviedb.R
import com.williamoviedo.themoviedb.adapter.ItemMovieAdapter
import com.williamoviedo.themoviedb.adapter.ItemTvSerieAdapter
import com.williamoviedo.themoviedb.database.SaveDataBase
import com.williamoviedo.themoviedb.domain.ItemTvSerie
import com.williamoviedo.themoviedb.domain.ItemsMovie
import com.williamoviedo.themoviedb.domain.ResultsMovie
import com.williamoviedo.themoviedb.domain.ResultsTvSerie
import com.williamoviedo.themoviedb.interfaces.DataInterfaceMovies
import com.williamoviedo.themoviedb.interfaces.DataInterfaceTvSeries
import com.williamoviedo.themoviedb.service.ServiceMovies
import com.williamoviedo.themoviedb.service.ServiceSeries
import com.williamoviedo.themoviedb.util.Constant
import io.realm.Realm
import io.realm.RealmConfiguration
import kotlinx.android.synthetic.main.activity_movies_main.*
import org.jetbrains.anko.indeterminateProgressDialog

class MoviesTvSeriesActivity : AppCompatActivity()
    , NavigationView.OnNavigationItemSelectedListener
    , DataInterfaceMovies
    , DataInterfaceTvSeries {

    // Vistas
    private lateinit var viewPopularMovies: RecyclerView
    private lateinit var viewTopRatedMovies: RecyclerView
    private lateinit var viewUpcomingMovies: RecyclerView
    private lateinit var viewPopularTvSeries: RecyclerView
    private lateinit var viewUpcomingTvSeries: RecyclerView

    // DrawerLayout
    private lateinit var drawer: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle

    // Globales
    private lateinit var progressDialog: Dialog
    private lateinit var realm: Realm
    private val mContext: Context = this
    private lateinit var dataBase : SaveDataBase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar_main)
        toolbar.setNavigationIcon(R.drawable.hamburger)
        setSupportActionBar(toolbar)

        val navigationView: NavigationView = findViewById(R.id.navigation_view)
        navigationView.setNavigationItemSelectedListener(this)
        drawer = findViewById(R.id.drawer_layout)
        toggle = ActionBarDrawerToggle(
            this,
            drawer,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer.addDrawerListener(toggle)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        // Inicializar Realm
        Realm.init(applicationContext)
        dataBase = SaveDataBase()

        // Crear base de datos
        val mRealmConfiguration = RealmConfiguration.Builder()
            .name(Constant.NAME_DB)
            .schemaVersion(Constant.SCHEMA_VERSION)
            .deleteRealmIfMigrationNeeded()
            .build()
        realm = Realm.getInstance(mRealmConfiguration)
        Realm.setDefaultConfiguration(mRealmConfiguration)

        // Referenciar vistas
        viewPopularMovies = findViewById(R.id.view_popular_movies)
        viewTopRatedMovies = findViewById(R.id.view_top_rated_movies)
        viewUpcomingMovies = findViewById(R.id.view_upcoming_movies)
        viewPopularTvSeries = findViewById(R.id.view_popular_tv_series)
        viewUpcomingTvSeries = findViewById(R.id.view_upcoming_tv_series)

        // Crear un Layout Manager Horizontal
        setLayoutManager(viewPopularMovies)
        setLayoutManager(viewTopRatedMovies)
        setLayoutManager(viewUpcomingMovies)
        setLayoutManager(viewPopularTvSeries)
        setLayoutManager(viewUpcomingTvSeries)

        progressDialog = indeterminateProgressDialog(getString(R.string.dialog_get_info))
        progressDialog.show()

        // Llamar servicios
        val serviceMovies = ServiceMovies(this)
        val serviceTvSeries = ServiceSeries(this)

        // Consutar peliculas
        serviceMovies.getMovies(Constant.SERVICE_POPULAR, 1)
        serviceMovies.getMovies(Constant.SERVICE_TOP_RATED, 1)
        serviceMovies.getMovies(Constant.SERVICE_UPCOMING, 1)

        // Consultar series
        serviceTvSeries.getTvSerie(Constant.SERVICE_POPULAR, 1)
        serviceTvSeries.getTvSerie(Constant.SERVICE_TOP_RATED, 1)
    }


    /**
     * Al recibir respuesta del servidor fijar la informacion obtenida
     */
    override fun mainMovies(resultsMovie: ResultsMovie, categoryMovie: String) {

        when (categoryMovie) {
            Constant.SERVICE_POPULAR -> {
                setMoviesInfo(resultsMovie, viewPopularMovies)
            }

            Constant.SERVICE_TOP_RATED -> {
                setMoviesInfo(resultsMovie, viewTopRatedMovies)
            }

            Constant.SERVICE_UPCOMING -> {
                setMoviesInfo(resultsMovie, viewUpcomingMovies)
            }
        }
    }


    /**
     * Fijar la informacion de las peliculas
     */
    private fun setMoviesInfo(movie: ResultsMovie, recyclerView: RecyclerView) {

        // Guardar en la base de datos usando Realm
        dataBase.saveDataBase(movie)

        // Utilizar adaptador para mostrar resultados
        recyclerView.adapter = ItemMovieAdapter(movie.results)

        (recyclerView.adapter as ItemMovieAdapter).onItemClick = { itemsMovie ->
            val intent = Intent(mContext, DetailMoviesActivity::class.java)
            intent.putExtra(Constant.KEY_ID_ITEM_DETAIL, itemsMovie.id)
            startActivity(intent)
        }
    }


    /**
     * Al recibir respuesta del servidor fijar la informacion obtenida
     */
    override fun mainTvSeries(resultsTvSerie: ResultsTvSerie, categoryTvSeries : String) {
        when (categoryTvSeries) {
            Constant.SERVICE_POPULAR -> {
                setTvSeriesInfo(resultsTvSerie, viewPopularTvSeries)
            }

            Constant.SERVICE_TOP_RATED -> {
                setTvSeriesInfo(resultsTvSerie, viewUpcomingTvSeries)
            }
        }
    }


    /**
     * Fijar la informacion de las series
     */
    private fun setTvSeriesInfo(resultsTvSerie: ResultsTvSerie, recyclerView: RecyclerView) {

        // Guardar en la base de datos usando Realm
        dataBase.saveDataBase(resultsTvSerie)

        // Utilizar adaptador para mostrar resultados
        // Utilizar adaptador para mostrar resultados
        recyclerView.adapter = ItemTvSerieAdapter(resultsTvSerie.results)

        (recyclerView.adapter as ItemTvSerieAdapter).onItemClick = { itemsSeries ->
            val intent = Intent(mContext, DetailMoviesActivity::class.java)
            intent.putExtra(Constant.KEY_ID_ITEM_DETAIL, itemsSeries.id)
            intent.putExtra(Constant.KEY_ID_ITEM_TYPE, true)
            startActivity(intent)
        }

        progressDialog.dismiss()
    }


    override fun detailTvSeries(tvSerieDetail: ItemTvSerie) {
        // Sin Implementacion
    }


    override fun detailMovies(movieDetail: ItemsMovie) {
        // Sin Implementacion
    }


    /**
     * Fijar LayoutManager horizontal al RecycleView
     */
    private fun setLayoutManager(recyclerView: RecyclerView) {
        recyclerView.layoutManager = LinearLayoutManager(mContext)
        recyclerView.layoutManager = LinearLayoutManager(
            mContext
            , LinearLayoutManager.HORIZONTAL
            , false
        )
    }


    /**
     * Items en DrawerLayout
     */
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_novedades -> {
            }
        }
        drawer.closeDrawer(GravityCompat.START)
        return true
    }


    /**
     * Si el Drawer esta abierto, cerrarlo al presionar hacia atras
     */
    override fun onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}