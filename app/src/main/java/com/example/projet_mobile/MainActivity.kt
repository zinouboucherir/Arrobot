package com.example.projet_mobile

import android.content.Intent
import android.database.CursorWindow
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projet_mobile.databinding.ActivityMainBinding
import java.lang.reflect.Field
import java.time.LocalDate


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lateinit var adapter: RecycleAdapter
        binding.recyclerView.hasFixedSize() /* pour améliorer les pérformances*/
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        val model= ViewModelProvider(this).get(MyViewModel::class.java)
        model.allPlante()
        model.allPlante.observe(this){
            adapter = RecycleAdapter(this,it.toMutableList())
            binding.recyclerView.adapter = adapter
        }

        binding.search.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(
                s: CharSequence, start: Int, before: Int,
                count: Int
            ) {
                // TODO Auto-generated method stub

                // System.out.println("onTextChanged"+s);
            }
            override fun beforeTextChanged(
                s: CharSequence, start: Int, count: Int,
                after: Int
            ) {
                // TODO Auto-generated method stub
                // System.out.println("beforeTextChanged"+s);
            }

            override fun afterTextChanged(s: Editable) {
                model.partialNomPlante(s.toString())
                model.certainsPlante.observe(this@MainActivity) {
                    adapter = RecycleAdapter(this@MainActivity,it.toMutableList())
                    binding.recyclerView.adapter = adapter
                }
            }
        })

        binding.addBtn.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            startActivity(intent)
        }
        binding.arrBtn.setOnClickListener {
            val intent = Intent(this, PlantArroseActivity::class.java)
            startActivity(intent)
        }
        binding.instalAlarm.setOnClickListener {
            val intent = Intent(this, AlarmActivity::class.java)
            startActivity(intent)
        }
    }
}