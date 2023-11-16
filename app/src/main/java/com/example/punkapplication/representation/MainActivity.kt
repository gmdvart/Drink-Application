package com.example.punkapplication.representation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.punkapplication.R
import com.example.punkapplication.databinding.ActivityMainBinding
import com.example.punkapplication.representation.home.HomeFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.nav_host_fragment, HomeFragment())
            .commit()
    }
}