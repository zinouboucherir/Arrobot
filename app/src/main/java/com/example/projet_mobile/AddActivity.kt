package com.example.projet_mobile
import android.app.DatePickerDialog
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.lifecycle.ViewModelProvider
import com.example.projet_mobile.databinding.ActivityAddBinding
import java.io.File
import java.time.LocalDate
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
        // use binding
        val binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // use viewmodel pour stocker les données d'une plante
        var modelPlant = ViewModelProvider(this).get(PlantViewModel::class.java)
        binding.nom1.setText(modelPlant.plante.value?.nom1)
        binding.nom2.setText(modelPlant.plante.value?.nom2)
        binding.dateSimple.text=modelPlant.plante.value?.dateProchainArrSimple.toString()
        binding.dateNutr.text=modelPlant.plante.value?.dateProchainArrNutr.toString()
        binding.planteimage.setImageURI(Uri.parse(modelPlant.plante.value?.image))




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
                        // utilisation de view model pour changer la date
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
        /////////////////////////////////// ajouter image ///////////////////////////////////
        binding.ajouterImage.setOnClickListener {
        getContent.launch("image/*")
        }

/////////////////////////////////// ajouter plante /////////// ///////////////////////////////////
        binding.ajouterPlante.setOnClickListener {

            val n1=binding.nom1.text.toString().trim()
            val n2=binding.nom2.text.toString().trim()
            // utilisation de view model pour changer les noms
            modelPlant.plante.value?.nom1=n1
            modelPlant.plante.value?.nom2=n2
            // utilisation de view model (observer) pour récupérer les information de la plante à inserer
            modelPlant.plante.observe(this)
            {
                    // utilisation de view model pour accéder à la fonction d'insertion
                    val model= ViewModelProvider(this).get(MyViewModel::class.java)
                    modelPlant.plante.observe(this) {
                        if ((it.nom1.isEmpty() && it.nom2.isEmpty()) ) {
                            Toast.makeText(this, "Entrez au mois un nom", Toast.LENGTH_SHORT).show()
                        }
                        else if (it.dateProchainArrSimple==null || it.dateProchainArrNutr==null)
                        {
                            Toast.makeText(this, "Entrez les dates de prochains arrosage (simple et nutriments", Toast.LENGTH_SHORT).show()
                        }
                        else
                        {
                            //insertion
                            model.insertPlante(modelPlant.plante.value!!)
                            Toast.makeText(this, "Plante inséré avec succées", Toast.LENGTH_SHORT).show()
                            //vider les champs
                            binding.nom1.text?.clear()
                            binding.nom2.text?.clear()
                            binding.dateNutr.setText("Prochain Arrosage nutriment")
                            binding.dateSimple.setText("Prochain Arrosage simple")
                            binding.planteimage.setImageResource(R.drawable.plante1)
                        }
                    }

            }
        }

    }
}