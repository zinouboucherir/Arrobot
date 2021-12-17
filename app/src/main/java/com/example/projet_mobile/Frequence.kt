package com.example.projet_mobile

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity
data class Frequence(
    val NbrFois:Int,
    val Par:String,
    val saison:String,
    val planteId:Int
)
{
    @PrimaryKey(autoGenerate = true)
    var FrequenceId:Int?=null
}