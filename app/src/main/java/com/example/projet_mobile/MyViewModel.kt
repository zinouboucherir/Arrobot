package com.example.projet_mobile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class MyViewModel(application: Application) : AndroidViewModel(application) {
    val dao = PlanteDB.getDatabase(application).myDao()
    var allPlante= MutableLiveData<List<Plante>>()
    var certainsPlante = MutableLiveData<List<Plante>>()
    fun partialNomPays(nom: String) {
        Thread {certainsPlante.postValue(dao.loadPartialName(nom).toList()) }.start()
    }
    fun allPlante() {
        Thread { allPlante.postValue(dao.loadAll().toList()) }.start()
    }
}