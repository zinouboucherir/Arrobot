package com.example.projet_mobile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.example.projet_mobile.fragments.AddFragment
import com.example.projet_mobile.fragments.ArrosageFragment
import com.example.projet_mobile.fragments.HomeFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private val homeFragment=HomeFragment()
    private val arrosageFragment=ArrosageFragment()
    private val addFragment=AddFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        replaceFragment(homeFragment)

        val navigation=findViewById<BottomNavigationView>(R.id.bottom_menu)
        navigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.home->replaceFragment(homeFragment)
                R.id.add->replaceFragment(addFragment)
                R.id.grass->replaceFragment(arrosageFragment)
            }
            true
        }
    }
    private fun replaceFragment(fragment: Fragment){
        if(fragment!=null)
        {
            val transaction=supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container,fragment)
            transaction.commit()
        }
    }
}