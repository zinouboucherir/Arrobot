package com.example.projet_mobile
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.service.voice.VoiceInteractionSession
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import androidx.lifecycle.ViewModelProvider
import com.example.projet_mobile.databinding.ActivityAddBinding
import java.io.File
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
        image.setImageURI(localUri)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)
/////////////////////////////////// datepicker for date d'arrosage simple ///////////////////////////////////
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        var date=""
        binding.arrSimple.setOnClickListener{
            val dpd = DatePickerDialog(this,
                DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDay ->
                    date = "" + mDay + "-" + (mMonth.toInt() + 1) + "-" + mYear
                    binding.dateSimple.setText(date)
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
        binding.arrNutr.setOnClickListener{
            val dpd = DatePickerDialog(this,
                DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDay ->
                    date1 = "" + mDay + "-" + (mMonth.toInt() + 1) + "-" + mYear
                    binding.dateNutr.setText(date1)
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
            val nom1=binding.nom1.text.toString().trim()
            val nom2=binding.nom2.text.toString().trim()
            val dateSimple=c.time
            val dateNutr=c1.time
            if ((nom1.isEmpty() && nom2.isEmpty()) ) {
                Toast.makeText(this, "Entrez au mois un nom", Toast.LENGTH_SHORT).show()
            } else {
                val plante = Plante(nom1, nom2, dateSimple, dateNutr,false,0,localUri.toString())
                val model= ViewModelProvider(this).get(MyViewModel::class.java)
                model.insertPlante(plante)
                Toast.makeText(this, "Plante inséré avec succées", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }

    }
}