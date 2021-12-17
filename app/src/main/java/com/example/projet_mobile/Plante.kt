package com.example.projet_mobile

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
        var arrose:Boolean
        )
{
    @PrimaryKey(autoGenerate = true)
    var planteId:Int?=null
}