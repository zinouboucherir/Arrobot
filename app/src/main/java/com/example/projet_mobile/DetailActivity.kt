package com.example.projet_mobile

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.projet_mobile.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val i = intent
        val plante: Plante? = i.getSerializableExtra("plante") as Plante?
        var modelPlant = ViewModelProvider(this).get(PlantViewModel::class.java)
        modelPlant.plante.setValue(plante)
        binding.nomfr.setText(modelPlant.plante.value?.nom1)
        binding.nomL.setText(modelPlant.plante.value?.nom2)
        binding.dateSimple.text=modelPlant.plante.value?.dateProchainArrSimple.toString()
        binding.dateNutr.text=modelPlant.plante.value?.dateProchainArrNutr.toString()
        binding.planteimage.setImageURI(Uri.parse(modelPlant.plante.value?.image))
        binding.sauvgarder.isEnabled=false
        binding.modifierPlante.setOnClickListener {
            binding.sauvgarder.isEnabled=true
            binding.nomfr.isEnabled=true
            binding.nomL.isEnabled=true
            binding.arrSimple.isEnabled=true
            binding.arrNutr.isEnabled=true
            binding.ajouterImage.isEnabled=true
            binding.modifierPlante.isEnabled=false
        }
    }
}