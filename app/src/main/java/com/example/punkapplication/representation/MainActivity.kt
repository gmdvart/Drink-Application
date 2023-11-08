package com.example.punkapplication.representation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.punkapplication.R
import com.example.punkapplication.representation.home.HomeFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.nav_host_fragment, HomeFragment())
            .commit()
    }
}