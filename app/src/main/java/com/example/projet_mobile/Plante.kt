package com.example.projet_mobile

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.io.Serializable
import java.time.LocalDate
import java.util.*

@Entity
data class Plante (
        var nom1:String,
        var nom2:String,
        var dateProchainArrSimple: LocalDate?,
        var dateProchainArrNutr: LocalDate?,
        var arrose:Boolean,
        var nbrFrequence:Int,
        var image:String
        ):Serializable
{
    @PrimaryKey(autoGenerate = true)
    var planteId:Int?=null
}