package com.example.projet_mobile

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Frequence(
    var NbrFois:Int,
    var Par:Int,   // la exemple Par 1 jours ,7 jours......
    var MoisDebut:Int,
    var MoisFin:Int,
    var planteId:Int,
    var FrequenceNutrr:Int // la fréquence d'arrrosage avec nutriment est multiple de fréquence d'arrosage simple donc on a besoin de la multiplicité
):Serializable
{
    @PrimaryKey(autoGenerate = true)
    var FrequenceId:Int?=null
}