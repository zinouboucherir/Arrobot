package com.example.projet_mobile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projet_mobile.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val ListPlante: MutableList<Plante> = arrayListOf()
        for (i in 1..5)
        {
            ListPlante.add(Plante("Red Cactus","Red Cactus"))
        }
        binding.recyclerView.hasFixedSize() /* pour améliorer les pérformances*/
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter=RecycleAdapter(ListPlante)
    }
}