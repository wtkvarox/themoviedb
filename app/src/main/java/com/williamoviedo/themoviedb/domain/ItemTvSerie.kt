package com.williamoviedo.themoviedb.domain

import com.google.gson.annotations.SerializedName
import com.williamoviedo.themoviedb.util.Constant
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

@RealmClass
open class ItemTvSerie : RealmObject() {
    @SerializedName("poster_path")
    var posterpath: String = Constant.EMPTY_STRING
    @SerializedName("popularity")
    var popularity: Float = 0.0f
    @PrimaryKey
    @SerializedName("id")
    var id: Long = 0
    @SerializedName("backdrop_path")
    var backdropPath: String? = Constant.EMPTY_STRING
    @SerializedName("vote_average")
    var voteAverage: Float = 0.0f
    @SerializedName("overview")
    var overview: String? = Constant.EMPTY_STRING
    @SerializedName("origin_country")
    var originCountry = RealmList<String>()
    @SerializedName("genre_ids")
    var genreIds = RealmList<Int>()
    @SerializedName("original_language")
    var originalLanguage: String? = Constant.EMPTY_STRING
    @SerializedName("vote_count")
    var vote_count: Int = 0
    @SerializedName("name")
    var name: String? = Constant.EMPTY_STRING
    @SerializedName("original_name")
    var originalName: String? = Constant.EMPTY_STRING
    @SerializedName("first_air_date")
    var firstAirDate: String? = Constant.EMPTY_STRING
}