package com.example.projet_mobile


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
class PlantViewModel:ViewModel() {
    val isenable: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>().apply { value = false }  // cette variable pour preserver l'etats des inputs et bouton (disable or enable)
    }
    val plante: MutableLiveData<Plante> by lazy {
        MutableLiveData<Plante>().apply { value = Plante("","", LocalDate.now(), LocalDate.now(),false,0,"") }
    }
}