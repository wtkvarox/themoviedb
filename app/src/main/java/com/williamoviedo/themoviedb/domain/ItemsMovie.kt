package com.williamoviedo.themoviedb.domain

import com.google.gson.annotations.SerializedName
import com.williamoviedo.themoviedb.util.Constant
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

@RealmClass
open class ItemsMovie : RealmObject() {
    @SerializedName("vote_count")
    var vote_count: Int = 0
    @PrimaryKey
    @SerializedName("id")
    var id: Long = 0
    @SerializedName("video")
    var video: Boolean = false
    @SerializedName("vote_average")
    var voteAverage: Float = 0.0f
    @SerializedName("title")
    var title: String? = Constant.EMPTY_STRING
    @SerializedName("popularity")
    var popularity: Float = 0.0f
    @SerializedName("poster_path")
    var poster_path: String? = Constant.EMPTY_STRING
    @SerializedName("original_language")
    var originalLanguage: String? = Constant.EMPTY_STRING
    @SerializedName("original_title")
    var originalTitle: String? = Constant.EMPTY_STRING
    @SerializedName("genre_ids")
    var genreIds = RealmList<Int>()
    @SerializedName("backdrop_path")
    var backdropPath: String? = Constant.EMPTY_STRING
    @SerializedName("adult")
    var adult: Boolean = false
    @SerializedName("overview")
    var overview: String? = Constant.EMPTY_STRING
    @SerializedName("release_date")
    var releaseDate: String? = Constant.EMPTY_STRING
    @SerializedName("homepage")
    var homepage: String? = Constant.EMPTY_STRING
    @SerializedName("imdb_id")
    var imdbId: String? = Constant.EMPTY_STRING
    @SerializedName("budget")
    var budget: Int? = 0
}