package com.williamoviedo.themoviedb.database

import io.realm.Realm
import io.realm.RealmObject

class SaveDataBase() {

    private var realm: Realm

    init {
        realm = Realm.getDefaultInstance()
    }

    fun saveDataBase(movieDetail: RealmObject) {
        realm = Realm.getDefaultInstance()
        realm.beginTransaction()
        realm.copyToRealmOrUpdate(movieDetail)
        realm.commitTransaction()
    }
}