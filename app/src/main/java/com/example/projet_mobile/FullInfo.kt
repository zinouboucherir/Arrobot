package com.example.projet_mobile

import java.time.LocalDate
import java.util.*

class FullInfo(
        val planteId:Int,
    var nom1:String,
    var nom2:String,
    var dateDernierArrSimple: LocalDate?,
    var dateDernierArrNutr: LocalDate?,
    var arrose:Boolean,
    var nbrFrequence:Int,
    var image:String,
    var NbrFois:Int,
    var Par:Int,
    var MoisDebut:Int,
    var MoisFin:Int,
    )
{

}