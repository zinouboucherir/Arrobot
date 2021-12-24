package com.example.projet_mobile
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.service.voice.VoiceInteractionSession
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import androidx.lifecycle.ViewModelProvider
import com.example.projet_mobile.databinding.ActivityAddBinding
import java.io.File
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*



class AddActivity : AppCompatActivity() {
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
        val file=File(this.filesDir,fileName)
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
        // utilisation de view model pour changer l'uri de l'image
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
        val binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var modelPlant = ViewModelProvider(this).get(PlantViewModel::class.java)
/////////////////////////////////// datepicker for date d'arrosage simple ///////////////////////////////////
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        var date=""
        var dd=""
        var mm=""
        lateinit var dateSimple:Date
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
                        dateSimple = Date.from(Instant.from(LocaldateSimple.atStartOfDay(ZoneId.of("GMT"))))
                        // utilisation de view model pour changer la date
                        modelPlant.plante.value?.dateDernierArrSimple=dateSimple
                        // observer  pour changer la date à afficher
                        modelPlant.plante.observe(this) {
                            binding.dateSimple.setText(modelPlant.plante.value?.dateDernierArrSimple?.toLocaleString())
                        }

                    }, year, month, day
            )
            dpd.getDatePicker().setMaxDate(System.currentTimeMillis());
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
        lateinit var dateNutr:Date
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
                    val LocaldateSimple = LocalDate.parse(date1, DateTimeFormatter.ofPattern("dd-MM-yyyy"))
                    dateNutr = Date.from(Instant.from(LocaldateSimple.atStartOfDay(ZoneId.of("GMT"))))
                    // utilisation de view model pour changer la date
                    modelPlant.plante.value?.dateDernierArrNutr=dateNutr
                    // observer  pour changer la date à afficher
                    modelPlant.plante.observe(this) {
                        binding.dateNutr.setText(modelPlant.plante.value?.dateDernierArrNutr?.toLocaleString())
                    }
                }, year1, month1, day1
            )
            dpd.getDatePicker().setMaxDate(System.currentTimeMillis());
            dpd.show()
        }
        /////////////////////////////////// ajouter image ///////////////////////////////////
        binding.ajouterImage.setOnClickListener {
        getContent.launch("image/*")
        }

/////////////////////////////////// ajouter plante /////////// ///////////////////////////////////
        binding.ajouterPlante.setOnClickListener {

            val n1=binding.nom1.text.toString().trim()
            val n2=binding.nom2.text.toString().trim()
            if ((n1.isEmpty() && n2.isEmpty()) ) {
                Toast.makeText(this, "Entrez au mois un nom", Toast.LENGTH_SHORT).show()
            } else {

                // utilisation de view model pour changer les noms
                modelPlant.plante.value?.nom1=n1
                modelPlant.plante.value?.nom2=n2

                // utilisation de view model pour accéder à la fonction d'insertion
                val model= ViewModelProvider(this).get(MyViewModel::class.java)
                modelPlant.plante.observe(this) {
                    model.insertPlante(modelPlant.plante.value!!)
                }
                Toast.makeText(this, "Plante inséré avec succées", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }

    }
}