package com.williamoviedo.themoviedb.splash

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.williamoviedo.themoviedb.activity.MoviesTvSeriesActivity
import com.williamoviedo.themoviedb.R

class SplashScreenActivity : AppCompatActivity() {

    private val timeOut: Long = 3000
    private val mContext : Context = this

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            startActivity(Intent(mContext, MoviesTvSeriesActivity::class.java))
            finish()
        }, timeOut)
    }
}