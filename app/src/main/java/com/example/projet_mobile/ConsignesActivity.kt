package com.example.projet_mobile

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.example.projet_mobile.databinding.ActivityConsignesBinding
import com.example.projet_mobile.databinding.ActivityDetailBinding
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class ConsignesActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityConsignesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //récuperer les différentes informations de la plante à arroser
        val i = intent
        val plantefullInfo: FullInfo? = i.getSerializableExtra("plantefullInfo") as FullInfo?

        val model = ViewModelProvider(this).get(MyViewModel::class.java)

        //arroser la plante
        binding.arroser.setOnClickListener {

            AlertDialog.Builder(this)
                    .setMessage("vous voulez arroser cette plante?")
                    .setCancelable(false)
                    .setPositiveButton("OK") { d, _ ->
                        if (plantefullInfo?.dateProchainArrNutr==LocalDate.now()) //verifier s'il s'agit d'un arrosage avec nutriments et on mis à jour la date de prochain arrosage avec nutriments
                        {
                            model.updateDateArrosageNutr(plantefullInfo?.planteId!!,plantefullInfo.dateProchainArrNutr?.plusDays((plantefullInfo.Par*plantefullInfo.FrequenceNutrr/ plantefullInfo.NbrFois).toLong())!!,true)
                        }
                        if (plantefullInfo?.dateProchainArrSimple==LocalDate.now())//verifier s'il s'agit d'un arrosage simple et on mis à jour la date de prochain arrosage simple
                        {
                            model.updateDateArrosage(plantefullInfo?.planteId!!,plantefullInfo.dateProchainArrSimple?.plusDays((plantefullInfo.Par/ plantefullInfo.NbrFois).toLong())!!,true)
                        }
                        Toast.makeText(this, "arrosage effectuer avec succées ! ", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, PlantArroseActivity::class.java)
                        finish()
                        startActivity(intent)
                    }
                    .setNegativeButton("NON") { d, _ -> d.dismiss() }
                    .show()
        }

        //ne pas arroser et replanifier le prochain arrosage automatiquement
        binding.newDateAut.setOnClickListener {
            AlertDialog.Builder(this)
                    .setMessage("vous ne voulez pas arroser cette plante ?")
                    .setCancelable(false)
                    .setPositiveButton("OK") { d, _ ->
                        if (plantefullInfo?.dateProchainArrNutr==LocalDate.now()) //verifier s'il s'agit d'un arrosage avec nutriments et on mis à jour la date de prochain arrosage avec nutriments
                        {
                            model.updateDateArrosageNutr(plantefullInfo?.planteId!!,plantefullInfo.dateProchainArrNutr?.plusDays((plantefullInfo.Par*plantefullInfo.FrequenceNutrr/ plantefullInfo.NbrFois).toLong())!!,false)
                        }
                        if (plantefullInfo?.dateProchainArrSimple==LocalDate.now())//verifier s'il s'agit d'un arrosage simple et on mis à jour la date de prochain arrosage simple
                        {
                            model.updateDateArrosage(plantefullInfo?.planteId!!,plantefullInfo.dateProchainArrSimple?.plusDays((plantefullInfo.Par/ plantefullInfo.NbrFois).toLong())!!,false)
                        }

                        Toast.makeText(this, "arrosage planifier automatiquement ! ", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, PlantArroseActivity::class.java)
                        finish()
                        startActivity(intent)
                    }
                    .setNegativeButton("NON") { d, _ -> d.dismiss() }
                    .show()
        }

        //replanifier l'arrosage avec datePicker
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        var date=""
        var dd=""
        var mm=""
        binding.newDateArr.setOnClickListener{
            val dpd = DatePickerDialog(this,
                    DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDay ->
                        dd = "$mDay"
                        mm = "${(mMonth + 1)}"
                        if (mDay <= 9) {
                            dd = "0${mDay}"
                        }
                        if (mMonth <= 9) {
                            mm = "0${(mMonth + 1)}"
                        }
                        date = "$dd-$mm-$mYear"
                        val LocaldateSimple = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy"))

                        AlertDialog.Builder(this)
                                .setMessage("vous voulez replanifierl'arrosage dans la date choisi ?")
                                .setCancelable(false)
                                .setPositiveButton("OK") { d, _ ->
                                    if (plantefullInfo?.dateProchainArrNutr== LocalDate.now()) //verifier s'il s'agit d'un arrosage avec nutriments et on mis à jour la date de prochain arrosage avec nutriments
                                    {
                                        model.updateDateArrosageNutr(plantefullInfo?.planteId!!,LocaldateSimple,false)
                                    }
                                    if(plantefullInfo?.dateProchainArrSimple== LocalDate.now()) //verifier s'il s'agit d'un arrosage simple et on mis à jour la date de prochain arrosage simple
                                    {
                                        model.updateDateArrosage(plantefullInfo?.planteId!!,LocaldateSimple,false)
                                    }
                                    Toast.makeText(this, "arrosage replanifier avec succées  ! ", Toast.LENGTH_SHORT).show()
                                    val intent = Intent(this, PlantArroseActivity::class.java)
                                    finish()
                                    startActivity(intent)
                                }
                                .setNegativeButton("NON") { d, _ -> d.dismiss() }
                                .show()

                    }, year, month, day
            )
            dpd.getDatePicker().setMinDate(System.currentTimeMillis());
            dpd.show()
        }


    }
}