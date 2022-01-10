package com.example.projet_mobile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.sql.Date
import java.time.LocalDate
import java.util.*

class MyViewModel(application: Application) : AndroidViewModel(application) {
    val dao = PlanteDB.getDatabase(application).myDao()
    var allPlante=MutableLiveData<List<Plante>>()
    var certainsPlante = MutableLiveData<List<Plante>>()
    var listFreqIntersect=MutableLiveData<List<Frequence>>()
    var listFreqIntersectForUpdate=MutableLiveData<List<Frequence>>()
    var listFrequenceActuelle=MutableLiveData<List<FullInfo>>()
    var allFrequenceOfplant=MutableLiveData<List<Frequence>>()


    fun allPlante() {
        dao.loadAllPlant().observeForever{
            Thread {
                allPlante.postValue(it.toList())
            }.start()
        }
    }
    fun partialNomPays(nom: String) {
        Thread {certainsPlante.postValue(dao.loadPartialNamePlant(nom).toList()) }.start()
    }
    fun insertPlante(plante: Plante) {
        Thread { dao.addPlante(plante) }.start()
    }
    fun insertFrequence(frequence: Frequence) {
        Thread { dao.addFrequence(frequence) }.start()
    }
    fun augmenterNbrFreq(id:Int) {
        Thread { dao.augmenterNbrFrequence(id) }.start()
    }
    fun diminierNbrFreq(id:Int) {
        Thread { dao.dimibierNbrFrequence(id) }.start()
    }
    fun verifiertersectdesPeriode(id:Int,debut:Int,fin:Int) {
        val count = Thread {
            listFreqIntersect.postValue(emptyList())
            listFreqIntersect.postValue(dao.verifierIntersectdesPeriode(id,debut,fin).toList())
        }
        count.start()
        count.join()
    }
    fun verifyIbtersectForUpdate(id:Int,debut:Int,fin:Int,freqId:Int) {
        val count = Thread {
            listFreqIntersectForUpdate.postValue(emptyList())
            listFreqIntersectForUpdate.postValue(dao.verifyIntersectForUpdate(id,debut,fin,freqId).toList())
        }
        count.start()
        count.join()
    }
    fun PlantFrequence(date:Int) {
        dao.frequencesDesPlanteActuelle(date).observeForever{
            Thread { listFrequenceActuelle.postValue(it.toList()) }.start()
        }
    }
    fun updatePlante(plante: Plante){
        Thread{ dao.updatePlante(plante) }.start()
    }
    fun allFrequenceOfPlant(id:Int) {
        dao.allFrequenceOfplant(id).observeForever{
            Thread { allFrequenceOfplant.postValue(it.toList()) }.start()
        }
    }
    fun updateFreqence(frequence: Frequence){
        Thread{ dao.updateFrequenece(frequence) }.start()
    }
    fun deleteFreqence(frequence: Frequence){
        Thread{ dao.deleteFrequence(frequence) }.start()
    }
    fun updateDateArrosage(id:Int,date: LocalDate) {
        Thread { dao.updateDateArrosage(id,date) }.start()
    }
    fun updateDateArrosageNutr(id:Int,date: LocalDate) {
        Thread { dao.updateDateArrosageNutr(id,date) }.start()
    }
    fun deletePlante(plante:Plante){
        Thread {dao.deletePlant(plante)}.start()
    }
    fun  deletPlantFrequences(id:Int)
    {
        Thread{dao.deletePlanteFrequences(id)}.start()
    }
}