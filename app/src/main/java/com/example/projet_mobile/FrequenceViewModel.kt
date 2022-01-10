package com.example.projet_mobile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.time.Instant
import java.time.ZoneId
import java.util.*

class FrequenceViewModel: ViewModel() {
    val isenable: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>().apply { value = false }
    }
    val frequence: MutableLiveData<Frequence> by lazy {
        MutableLiveData<Frequence>().apply { value = Frequence(0,0,0,0,0,0) }
    }
}