package com.example.projet_mobile

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity
data class Frequence(
    var NbrFois:Int,
    var Par:Int,
    var MoisDebut:Int,
    var MoisFin:Int,
    var planteId:Int
)
{
    @PrimaryKey(autoGenerate = true)
    var FrequenceId:Int?=null
}