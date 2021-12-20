package com.example.projet_mobile

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface Dao {
    @Query("select * from Plante")
    fun loadAll():Array<Plante>

    @Query("select * from Plante where nom1 like :name||'%' or nom2 like:name||'%'")
    fun loadPartialName(name: String):Array<Plante>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addPlante(vararg palante:Plante):List<Long>
}