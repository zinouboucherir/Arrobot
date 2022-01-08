package com.example.projet_mobile

import android.app.DatePickerDialog
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.example.projet_mobile.databinding.ActivityDetailBinding
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*


@RequiresApi(Build.VERSION_CODES.O)
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

        /////////////////////////////////// datepicker for date d'arrosage simple ///////////////////////////////////
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        var date=""
        var dd=""
        var mm=""
        binding.arrSimple.setOnClickListener{

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
                    modelPlant.plante.value?.dateProchainArrSimple=LocaldateSimple
                    // observer  pour changer la date à afficher
                    modelPlant.plante.observe(this) {
                        binding.dateSimple.setText(modelPlant.plante.value?.dateProchainArrSimple?.toString())
                    }
                }, year, month, day
            )
            dpd.getDatePicker().setMinDate(System.currentTimeMillis());
            dpd.show()
        }

        /////////////////////////////// datepicker for date d'arrosage nutriment ///////////////////////////////////
        val c1 = Calendar.getInstance()
        val year1 = c1.get(Calendar.YEAR)
        val month1 = c1.get(Calendar.MONTH)
        val day1 = c1.get(Calendar.DAY_OF_MONTH)
        var date1=""
        var dd1=""
        var mm1=""
        binding.arrNutr.setOnClickListener{
            val dpd = DatePickerDialog(this,
                DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDay ->
                    binding.dateNutr.setText(date1)
                    dd1 = "$mDay"
                    mm1 = "${(mMonth + 1)}"
                    if (mDay <= 9) {
                        dd1 = "0${mDay}"
                    }
                    if (mMonth <= 9) {
                        mm1= "0${(mMonth + 1)}"
                    }
                    date1 = "$dd1-$mm1-$mYear"
                    val LocaldateNuttr = LocalDate.parse(date1, DateTimeFormatter.ofPattern("dd-MM-yyyy"))
                    //dateNutr = Date.from(Instant.from(LocaldateSimple.atStartOfDay(ZoneId.of("GMT"))))
                    // utilisation de view model pour changer la date
                    modelPlant.plante.value?.dateProchainArrNutr=LocaldateNuttr
                    // observer  pour changer la date à afficher
                    modelPlant.plante.observe(this) {
                        binding.dateNutr.setText(modelPlant.plante.value?.dateProchainArrNutr?.toString())
                    }
                }, year1, month1, day1
            )
            dpd.getDatePicker().setMinDate(System.currentTimeMillis());
            dpd.show()
        }


    }
}