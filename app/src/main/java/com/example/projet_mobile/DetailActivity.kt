package com.example.projet_mobile

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.net.toUri
import androidx.lifecycle.ViewModelProvider
import com.example.projet_mobile.databinding.ActivityAddBinding
import com.example.projet_mobile.databinding.ActivityDetailBinding
import java.io.File
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class DetailActivity : AppCompatActivity() {
    var localUri: Uri?=null
    val getContent=registerForActivityResult(ActivityResultContracts.GetContent())
    {
            uri:Uri?->/*siurinullrienàfaire*/
        if(uri==null)
            return@registerForActivityResult
        /*inputStreamavecl’imagedelaplante*/
        val inputStream=getContentResolver().openInputStream(uri)
        /*fabriquerlenomdefichierlocalpourstockerl’image*/
        val fileNamePrefix="plante"
        val preferences=getSharedPreferences("numImage", Context.MODE_PRIVATE)
        val numImage=preferences.getInt("numImage",1)
        val fileName="$fileNamePrefix$numImage"
        /*ouvriroutputStreamversunfichierlocal*/
        val file= File(this.filesDir,fileName)
        val outputStream=file.outputStream()
        /* sauvegarder le nouveau compteur d’image */
        preferences.edit().putInt("numImage",numImage+1).commit()
        /* copier inputStream qui pointe sur l’image de la galerie * vers le fichier local */
        inputStream?.copyTo(outputStream)
        /* mémoriser Uri de fichier local dans la propriété localUri */
        localUri = file.toUri()
        outputStream.close()
        inputStream?.close()
        //éventuellement afficher l’image dans ImageView
        val image=findViewById<ImageView>(R.id.planteimage)
        var modelPlant = ViewModelProvider(this).get(PlantViewModel::class.java)
        modelPlant.plante.value?.image=localUri.toString()
        // observer  pour changer l'image à afficher
        modelPlant.plante.observe(this) {
            image.setImageURI(modelPlant.plante.value?.image?.toUri())
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //récupérer la plante
        val i = intent
        val plante: Plante? = i.getSerializableExtra("plante") as Plante?

        var modelPlant = ViewModelProvider(this).get(PlantViewModel::class.java)

        //utilisation de view model et initialisation avec les informations de la plante
        modelPlant.plante.setValue(plante)
        binding.nomfr.setText(modelPlant.plante.value?.nom1)
        binding.nomL.setText(modelPlant.plante.value?.nom2)
        binding.dateSimple.text=modelPlant.plante.value?.dateProchainArrSimple.toString()
        binding.dateNutr.text=modelPlant.plante.value?.dateProchainArrNutr.toString()
        binding.planteimage.setImageURI(Uri.parse(modelPlant.plante.value?.image))
        binding.sauvgarder.isEnabled = modelPlant.isenable.value!!
        binding.nomfr.isEnabled = modelPlant.isenable.value!!
        binding.nomL.isEnabled = modelPlant.isenable.value!!
        binding.arrSimple.isEnabled = modelPlant.isenable.value!!
        binding.arrNutr.isEnabled = modelPlant.isenable.value!!
        binding.modifierImage.isEnabled = modelPlant.isenable.value!!
        binding.modifierPlante.isEnabled = !modelPlant.isenable.value!!
        binding.modifierPlante.setOnClickListener {
            modelPlant.isenable.setValue(binding.modifierPlante.isEnabled)
            binding.sauvgarder.isEnabled = modelPlant.isenable.value!!
            binding.nomfr.isEnabled = modelPlant.isenable.value!!
            binding.nomL.isEnabled = modelPlant.isenable.value!!
            binding.arrSimple.isEnabled = modelPlant.isenable.value!!
            binding.arrNutr.isEnabled = modelPlant.isenable.value!!
            binding.modifierImage.isEnabled = modelPlant.isenable.value!!
            binding.modifierPlante.isEnabled = !modelPlant.isenable.value!!
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
/////////////////////////////////// datepicker for date d'arrosage nutriment ///////////////////////////////////
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
        //modifier l'image
        binding.modifierImage.setOnClickListener {
            getContent.launch("image/*")
        }
        // sauvgarder les modifications
        binding.sauvgarder.setOnClickListener {

            modelPlant.plante.value?.nom1=binding.nomfr.text.toString()
            modelPlant.plante.value?.nom2=binding.nomL.text.toString()


            modelPlant.plante.observe(this)
            {

                val model= ViewModelProvider(this).get(MyViewModel::class.java)
                modelPlant.plante.observe(this) {
                    if ((it.nom1.isEmpty() && it.nom2.isEmpty()) ) {
                        Toast.makeText(this, "Entrez au mois un nom", Toast.LENGTH_SHORT).show()
                    }
                    else if (it.dateProchainArrSimple==null || it.dateProchainArrNutr==null)
                    {
                        Toast.makeText(this, "Entrez les dates de dernier arrosage (simple et nutriments", Toast.LENGTH_SHORT).show()
                    }
                    else
                    {
                        //update plante
                        model.updatePlante(modelPlant.plante.value!!)
                        Toast.makeText(this, "Plante modifiée avec succées", Toast.LENGTH_SHORT).show()
                        modelPlant.isenable.setValue(binding.modifierPlante.isEnabled!!)
                        binding.sauvgarder.isEnabled = modelPlant.isenable.value!!
                        binding.nomfr.isEnabled = modelPlant.isenable.value!!
                        binding.nomL.isEnabled = modelPlant.isenable.value!!
                        binding.arrSimple.isEnabled = modelPlant.isenable.value!!
                        binding.arrNutr.isEnabled = modelPlant.isenable.value!!
                        binding.modifierImage.isEnabled = modelPlant.isenable.value!!
                        binding.modifierPlante.isEnabled = !modelPlant.isenable.value!!

                    }
                }

            }
        }

        binding.freq.setOnClickListener {
            val intent = Intent(this, ListeFrequenceActivity::class.java)
            intent.putExtra("idPlante",plante?.planteId)
            startActivity(intent)
        }
        binding.deleteFrequence.setOnClickListener {
            AlertDialog.Builder(this)
                .setMessage("vous voulez supprimer cette plante?")
                .setCancelable(false)
                .setPositiveButton("OK") { d, _ ->
                    val model= ViewModelProvider(this).get(MyViewModel::class.java)
                    model.deletePlante(modelPlant.plante.value!!)
                    model.deletPlantFrequences(modelPlant.plante.value!!.planteId!!)
                    Toast.makeText(this, "Plante supprimée avec succées", Toast.LENGTH_SHORT).show()
                    d.dismiss()
                    val intent = Intent(this, MainActivity::class.java)
                    finish()
                    startActivity(intent)
                }
                .setNegativeButton("NON") { d, _ -> d.dismiss() }
                .show()

        }

    }

}