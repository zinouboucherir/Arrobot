package com.example.projet_mobile

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.PersistableBundle
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)

        val logoAnimation=AnimationUtils.loadAnimation(this,R.anim.logo_animation)

        val logo=findViewById<ImageView>(R.id.imageView)
        logo.startAnimation(logoAnimation)
        val splashScreenTimeout=4000
        val homeIntent=Intent(this,MainActivity::class.java)

        Handler().postDelayed({
            startActivity(homeIntent)
            finish()
        },splashScreenTimeout.toLong())
    }
}