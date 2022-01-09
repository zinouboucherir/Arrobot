package com.example.projet_mobile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import androidx.room.Dao
import java.sql.Date

@Dao
interface Dao {
    @Query("select * from Plante")
    fun loadAll():Array<Plante>

    @Query("select * from Plante where nom1 like :name||'%' or nom2 like:name||'%'")
    fun loadPartialName(name: String):Array<Plante>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addPlante(vararg plante: Plante):List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addFrequence(vararg frequence: Frequence):List<Long>

    @Query("UPDATE Plante SET nbrFrequence=nbrFrequence+1 WHERE planteId = :id")
    fun updateNbrFrequence(id: Int)

    @Query("SELECT * FROM Frequence where (planteId=:id and MoisDebut <= :EndB and :StartB <= MoisFin and MoisDebut <= MoisFin and :StartB <= :EndB)")
    fun verifyIntersect(id:Int,StartB:Int,EndB:Int):Array<Frequence>

    @Query("SELECT * FROM Plante natural join Frequence where :date between MoisDebut and MoisFin")
    fun PlantFrequence(date:Int):LiveData<Array<FullInfo>>

    @Query("SELECT * FROM Plante natural join Frequence where :date between MoisDebut and MoisFin")
    fun PlantFrequences(date:Int):Array<FullInfo>

    @Update
    fun updatePlante(vararg plante: Plante) : Int
}