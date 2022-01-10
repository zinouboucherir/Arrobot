package com.example.projet_mobile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import androidx.room.Dao
import java.sql.Date
import java.time.LocalDate

@Dao
interface Dao {
    @Query("select * from Plante") //la liste de toutes les plantes
    fun loadAllPlant():LiveData<Array<Plante>>

    @Query("select * from Plante where nom1 like :name||'%' or nom2 like:name||'%'") //la liste partiel des palntes
    fun loadPartialNamePlant(name: String):Array<Plante>

    @Insert(onConflict = OnConflictStrategy.REPLACE) //inserer une plante
    fun addPlante(vararg plante: Plante):List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE) //inserer une fréquence
    fun addFrequence(vararg frequence: Frequence):List<Long>

    @Query("UPDATE Plante SET nbrFrequence=nbrFrequence+1 WHERE planteId = :id") //incrémenter le nbr de fréquence
    fun augmenterNbrFrequence(id: Int)

    @Query("UPDATE Plante SET nbrFrequence=nbrFrequence-1 WHERE planteId = :id") //décrementer  le nbr de fréquence
    fun dimibierNbrFrequence(id: Int)

    @Query("SELECT * FROM Frequence where (planteId=:id and MoisDebut <= :EndB and :StartB <= MoisFin and MoisDebut <= MoisFin and :StartB <= :EndB)")//récupérer les fréquence dont les periode s'intersecte avec une certaine période
    fun verifierIntersectdesPeriode(id:Int,StartB:Int,EndB:Int):Array<Frequence>

    @Query("SELECT * FROM Plante natural join Frequence where :date between MoisDebut and MoisFin")//récupérer les fréquence d'une plante dans une période
    fun frequencesDesPlanteActuelle(date:Int):LiveData<Array<FullInfo>>

    @Query("SELECT * FROM Plante natural join Frequence where :date between MoisDebut and MoisFin")
    fun frequencesDesPlanteActuelleForService(date:Int):Array<FullInfo>

    @Query("SELECT * FROM Frequence where planteId=:id") //récuprer les fréquence d'une plante
    fun allFrequenceOfplant(id:Int):LiveData<Array<Frequence>>

    @Update
    fun updatePlante(vararg plante: Plante) : Int //update plante

    @Update
    fun updateFrequenece(vararg frequence: Frequence) : Int //update fréquence

    @Query("SELECT * FROM Frequence where (planteId=:id and MoisDebut <= :EndB and :StartB <= MoisFin and MoisDebut <= MoisFin and :StartB <= :EndB and FrequenceId!=:Freqid)")
    fun verifyIntersectForUpdate(id:Int,StartB:Int,EndB:Int,Freqid:Int):Array<Frequence>

    @Delete
    fun deleteFrequence( vararg frequence: Frequence) : Int //delete fréquence

    @Query("UPDATE Plante SET dateProchainArrSimple=:date,arrose=:arrose WHERE planteId = :id")
    fun updateDateArrosage(id: Int,date: LocalDate,arrose:Boolean)

    @Query("UPDATE Plante SET dateProchainArrNutr=:date,arrose=:arrose WHERE planteId = :id")
    fun updateDateArrosageNutr(id: Int,date: LocalDate,arrose:Boolean)

    @Delete
    fun deletePlant( vararg plante: Plante) : Int

    @Query("DELETE FROM Frequence where planteId=:id") //delete les fréquence d'une plante
    fun deletePlanteFrequences(id:Int)

}