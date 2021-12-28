package com.example.projet_mobile

import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.text.TextUtils.join
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.view.get
import androidx.lifecycle.ViewModelProvider
import com.example.projet_mobile.databinding.ActivityAddFrequenceBinding
import kotlinx.coroutines.NonCancellable.join
import kotlinx.coroutines.awaitAll
import kotlin.properties.Delegates


class AddFrequenceActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityAddFrequenceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //récupérer les information de la plante séléctionné
        binding.addFrequence.isEnabled=false
        val i = intent
        val plante: Plante? = i.getSerializableExtra("plante") as Plante?
        binding.textView4.text = "Fréquence de ${plante?.nom1}"

        //utilisé viewModel pour stocké les information de la fréquence
        var modelFrequence = ViewModelProvider(this).get(FrequenceViewModel::class.java)

        //la liste des mois
        val months = arrayListOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)
        //le mois de début
        binding.debutMois.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, months)
        binding.debutMois.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
            ) {
                modelFrequence.frequence.value?.MoisDebut = months.get(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
        //le mois de la fin
        binding.finMois.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, months)
        binding.finMois.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
            ) {

                modelFrequence.frequence.value?.MoisFin = months.get(position)

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
        var a=true
        binding.verifer.setOnClickListener {
            val model = ViewModelProvider(this).get(MyViewModel::class.java)
            //verifier si l'intervalle est valide (y a au plus une fréquence dans cet intervale )
            model.verifyIbtersect(modelFrequence.frequence.value?.planteId!!, modelFrequence.frequence.value?.MoisDebut!!, modelFrequence.frequence.value?.MoisFin!!)
            model.freq.observe(this){
                a=it.toMutableList().isEmpty()
            }
            modelFrequence.frequence.observe(this){
                if (binding.nbFois.text?.isEmpty()!! || binding.periode.text?.isEmpty()!!)
                {
                    Toast.makeText(this,"Assurer que tout les informations sans saisi!", Toast.LENGTH_SHORT).show()
                }
                else if (it.MoisDebut>it.MoisFin)
                {
                    Toast.makeText(this,"le mois de début doit étre inférieur au mois de la fin", Toast.LENGTH_SHORT).show()
                }
                else
                {
                    binding.addFrequence.isEnabled=true
                    binding.nbFois.isEnabled=false
                    binding.periode.isEnabled=false
                    binding.debutMois.isEnabled=false
                    binding.finMois.isEnabled=false
                }
            }

        }


        modelFrequence.frequence.value?.planteId = plante?.planteId!!
        binding.addFrequence.setOnClickListener {

            modelFrequence.frequence.value?.NbrFois = binding.nbFois.text.toString().toInt()
            modelFrequence.frequence.value?.Par=binding.periode.text.toString().toInt()
            if (!a) {
                Toast.makeText(this,"intervelle croise un autre exite déja !!", Toast.LENGTH_SHORT).show()
                binding.addFrequence.isEnabled=false
                binding.nbFois.isEnabled=true
                binding.periode.isEnabled=true
                binding.debutMois.isEnabled=true
                binding.finMois.isEnabled=true
            }
            else
            {
                val model = ViewModelProvider(this).get(MyViewModel::class.java)
                    model.freq.observe(this) {
                    model.insertFrequence(modelFrequence.frequence.value!!)
                    model.updateNbrFreq(plante.planteId!!)
                }
                Toast.makeText(this, "Frequence inséré avec succées", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)

            }
        }
    }
}