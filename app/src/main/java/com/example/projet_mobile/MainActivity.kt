package com.example.projet_mobile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projet_mobile.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var adapter: RecycleAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val recyclerView = findViewById(R.id.recyclerView) as RecyclerView
        recyclerView.hasFixedSize() /* pour améliorer les pérformances*/
        recyclerView.layoutManager = LinearLayoutManager(this)
        val model= ViewModelProvider(this).get(MyViewModel::class.java)
        model.allPlante()
        model.allPlante.observe(this){
            adapter = RecycleAdapter(it.toMutableList())
            recyclerView.adapter = adapter
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
                model.partialNomPays(s.toString())
                model.certainsPlante.observe(this@MainActivity) {
                    adapter = RecycleAdapter(it.toMutableList())
                    recyclerView.adapter = adapter
                }
            }
        })

    }
}