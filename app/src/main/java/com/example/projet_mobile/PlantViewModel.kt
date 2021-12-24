package com.example.projet_mobile

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
class PlantViewModel:ViewModel() {
    @RequiresApi(Build.VERSION_CODES.O)
    var date = LocalDate.of(2018, 12, 31)
    val plante: MutableLiveData<Plante> by lazy {
        MutableLiveData<Plante>().apply { value = Plante("","",Date.from(Instant.from(date.atStartOfDay(ZoneId.of("GMT")))),Date.from(Instant.from(date.atStartOfDay(ZoneId.of("GMT")))),false,0,"") }
    }
}