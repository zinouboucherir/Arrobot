package com.example.projet_mobile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import androidx.room.Dao
import java.sql.Date
import java.time.LocalDate

@Dao
interface Dao {
    @Query("select * from Plante")
    fun loadAllPlant():LiveData<Array<Plante>>

    @Query("select * from Plante where nom1 like :name||'%' or nom2 like:name||'%'")
    fun loadPartialNamePlant(name: String):Array<Plante>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addPlante(vararg plante: Plante):List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addFrequence(vararg frequence: Frequence):List<Long>

    @Query("UPDATE Plante SET nbrFrequence=nbrFrequence+1 WHERE planteId = :id")
    fun augmenterNbrFrequence(id: Int)

    @Query("UPDATE Plante SET nbrFrequence=nbrFrequence-1 WHERE planteId = :id")
    fun dimibierNbrFrequence(id: Int)

    @Query("SELECT * FROM Frequence where (planteId=:id and MoisDebut <= :EndB and :StartB <= MoisFin and MoisDebut <= MoisFin and :StartB <= :EndB)")
    fun verifierIntersectdesPeriode(id:Int,StartB:Int,EndB:Int):Array<Frequence>

    @Query("SELECT * FROM Plante natural join Frequence where :date between MoisDebut and MoisFin")
    fun frequencesDesPlanteActuelle(date:Int):LiveData<Array<FullInfo>>

    @Query("SELECT * FROM Plante natural join Frequence where :date between MoisDebut and MoisFin")
    fun frequencesDesPlanteActuelleForService(date:Int):Array<FullInfo>

    @Query("SELECT * FROM Frequence where planteId=:id")
    fun allFrequenceOfplant(id:Int):LiveData<Array<Frequence>>

    @Update
    fun updatePlante(vararg plante: Plante) : Int

    @Update
    fun updateFrequenece(vararg frequence: Frequence) : Int

    @Query("SELECT * FROM Frequence where (planteId=:id and MoisDebut <= :EndB and :StartB <= MoisFin and MoisDebut <= MoisFin and :StartB <= :EndB and FrequenceId!=:Freqid)")
    fun verifyIntersectForUpdate(id:Int,StartB:Int,EndB:Int,Freqid:Int):Array<Frequence>

    @Delete
    fun deleteFrequence( vararg frequence: Frequence) : Int

    @Query("UPDATE Plante SET dateProchainArrSimple=:date WHERE planteId = :id")
    fun updateDateArrosage(id: Int,date: LocalDate)

    @Query("UPDATE Plante SET dateProchainArrNutr=:date WHERE planteId = :id")
    fun updateDateArrosageNutr(id: Int,date: LocalDate)

    @Delete
    fun deletePlant( vararg plante: Plante) : Int

    @Query("DELETE FROM Frequence where planteId=:id")
    fun deletePlanteFrequences(id:Int)

}