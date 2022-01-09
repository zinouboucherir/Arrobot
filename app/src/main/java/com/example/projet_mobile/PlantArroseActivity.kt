package com.example.projet_mobile

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projet_mobile.databinding.ActivityPlantArroseBinding
import java.time.LocalDate
import java.util.*

class PlantArroseActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityPlantArroseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.recycler2.hasFixedSize() /* pour améliorer les pérformances*/
        binding.recycler2.layoutManager = LinearLayoutManager(this)
        val model= ViewModelProvider(this).get(MyViewModel::class.java)
        val now = Calendar.getInstance()
        model.PlantFrequence( (now.get(Calendar.MONTH) + 1))
        model.plantFreq.observe(this){
            val adapter = RecyclerAdapter2(this, it.filter { it.dateProchainArrSimple == java.time.LocalDate.now()}.toMutableList())
            binding.recycler2.adapter = adapter
        }
    }
}