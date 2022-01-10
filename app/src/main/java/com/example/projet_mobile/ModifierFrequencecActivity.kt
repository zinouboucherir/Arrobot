package com.example.projet_mobile

import android.R
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.projet_mobile.databinding.ActivityListeFrequenceBinding
import com.example.projet_mobile.databinding.ActivityModifierFrequencecBinding

class ModifierFrequencecActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityModifierFrequencecBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val i = intent
        val frequence: Frequence? = i.getSerializableExtra("frequence") as Frequence?
        var modelFrequence = ViewModelProvider(this).get(FrequenceViewModel::class.java)
        modelFrequence.frequence.setValue(frequence)
        binding.nbFois.setText(modelFrequence.frequence.value?.NbrFois.toString())
        binding.periode.setText(modelFrequence.frequence.value?.Par.toString())
        binding.debutMois.setSelection(modelFrequence.frequence.value?.MoisDebut!!)
        binding.finMois.setSelection(modelFrequence.frequence.value?.MoisFin!!)
        binding.addFrequence.isEnabled=false

        val months = arrayListOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)
        binding.debutMois.adapter = ArrayAdapter(this, R.layout.simple_list_item_1, months)
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
        binding.finMois.adapter = ArrayAdapter(this, R.layout.simple_list_item_1, months)
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
            binding.addFrequence.isEnabled=true
            val model = ViewModelProvider(this).get(MyViewModel::class.java)
            //verifier si l'intervalle est valide (y a au plus une fréquence dans cet intervale )
            model.verifyIbtersectForUpdate(modelFrequence.frequence.value?.planteId!!, modelFrequence.frequence.value?.MoisDebut!!, modelFrequence.frequence.value?.MoisFin!!,modelFrequence.frequence.value?.FrequenceId!!)
            model.listFreqIntersectForUpdate.observe(this){
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
                model.updateFreqence(modelFrequence.frequence.value!!)
                Toast.makeText(this, "Frequence modifier avec succées", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, ListeFrequenceActivity::class.java)
                intent.putExtra("idPlante",frequence?.planteId)
                finish()
                startActivity(intent)
            }
        }
        binding.deleteFrequence.setOnClickListener {
            AlertDialog.Builder(this)
                    .setMessage("vous voulez supprimer cette fréquence?")
                    .setCancelable(false)
                    .setPositiveButton("OK") { d, _ ->
                        val model = ViewModelProvider(this).get(MyViewModel::class.java)
                        model.deleteFreqence(modelFrequence.frequence.value!!)
                        model.diminierNbrFreq(frequence?.planteId!!)
                        Toast.makeText(this, "Frequence supprimer avec succées", Toast.LENGTH_SHORT).show()
                        d.dismiss()
                        val intent = Intent(this, ListeFrequenceActivity::class.java)
                        intent.putExtra("idPlante",frequence?.planteId)
                        finish()
                        startActivity(intent)
                    }
                    .setNegativeButton("NON") { d, _ -> d.dismiss() }
                    .show()
        }


    }
}