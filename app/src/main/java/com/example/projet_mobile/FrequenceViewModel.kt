package com.example.projet_mobile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.time.Instant
import java.time.ZoneId
import java.util.*

class FrequenceViewModel: ViewModel() {
    val frequence: MutableLiveData<Frequence> by lazy {
        MutableLiveData<Frequence>().apply { value = Frequence(0,0,0,0,0) }
    }
}