package com.example.projet_mobile

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.util.*

@Entity
data class Plante (
        val nom1:String,
        val nom2:String,
        var dateDernierArrSimple:Date,
        var dateDernierArrNutr:Date,
        var arrose:Boolean,
        var nbrFrequence:Int,
        var image:String
        )
{
    @PrimaryKey(autoGenerate = true)
    var planteId:Int?=null
}