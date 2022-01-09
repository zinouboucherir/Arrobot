package com.example.projet_mobile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.sql.Date
import java.util.*

class MyViewModel(application: Application) : AndroidViewModel(application) {
    val dao = PlanteDB.getDatabase(application).myDao()
    var allPlante= MutableLiveData<List<Plante>>()
    var certainsPlante = MutableLiveData<List<Plante>>()
    var freq=MutableLiveData<List<Frequence>>()
    var plantFreq=MutableLiveData<List<FullInfo>>()
    fun partialNomPays(nom: String) {
        Thread {certainsPlante.postValue(dao.loadPartialName(nom).toList()) }.start()
    }
    fun allPlante() {
        Thread { allPlante.postValue(dao.loadAll().toList()) }.start()
    }
    fun insertPlante(plante: Plante) {
        Thread { dao.addPlante(plante) }.start()
    }
    fun insertFrequence(frequence: Frequence) {
        Thread { dao.addFrequence(frequence) }.start()
    }
    fun updateNbrFreq(id:Int) {
        Thread { dao.updateNbrFrequence(id) }.start()
    }
    fun verifyIbtersect(id:Int,debut:Int,fin:Int) {
        val count = Thread {
            freq.postValue(emptyList())
            freq.postValue(dao.verifyIntersect(id,debut,fin).toList())
        }
        count.start()
        count.join()
    }
    fun PlantFrequence(date:Int) {
        Thread { plantFreq.postValue(dao.PlantFrequences(date).toList()) }.start()
    }
    fun updatePlante(plante: Plante){
        Thread{ dao.updatePlante(plante) }.start()
    }
}