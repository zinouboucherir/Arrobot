package com.example.projet_mobile


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PlantViewModel:ViewModel() {
    val plante: MutableLiveData<Plante> by lazy {
        MutableLiveData<Plante>().apply { value = Plante("","",null,null,false,0,"") }
    }
}