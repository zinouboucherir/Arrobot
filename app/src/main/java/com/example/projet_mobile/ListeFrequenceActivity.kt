package com.example.projet_mobile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projet_mobile.databinding.ActivityListeFrequenceBinding
import com.example.projet_mobile.databinding.ActivityMainBinding

class ListeFrequenceActivity : AppCompatActivity() {
    lateinit var adapter: RecycleAdapter3
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityListeFrequenceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.recycler3.hasFixedSize() /* pour améliorer les pérformances*/
        binding.recycler3.layoutManager = LinearLayoutManager(this)
        val model= ViewModelProvider(this).get(MyViewModel::class.java)
        //récupérer l'id de la plante pour afficher leur fréquences
        val i = intent
        val idPlante: Int=i.getIntExtra("idPlante",0)
        model.allFrequenceOfPlant(idPlante)
        model.allFrequenceOfplant.observe(this){
            adapter = RecycleAdapter3(this,it.toMutableList())
            binding.recycler3.adapter = adapter
        }
    }
}