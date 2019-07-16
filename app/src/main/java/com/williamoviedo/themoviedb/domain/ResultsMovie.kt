package com.williamoviedo.themoviedb.domain

import com.google.gson.annotations.SerializedName
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

@RealmClass
open class ResultsMovie : RealmObject() {
    @PrimaryKey
    var page: Int = 0
    @SerializedName("results")
    var results = RealmList<ItemsMovie>()
    @SerializedName("total_results")
    var totalResults: Int = 0
    @SerializedName("total_pages")
    var totalPages: Int = 0
}