package com.example.newsapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment

import androidx.navigation.ui.setupWithNavController

import com.example.newsapp.databinding.ActivityNewsBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Sử dụng View Binding để ánh xạ layout
        binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Lấy NavController từ NavHostFragment
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.newsNavHostFragment) as NavHostFragment
        val navController = navHostFragment.navController

        // Thiết lập BottomNavigationView với NavController
        binding.bottomNavigationView.setupWithNavController(navController)
    }
}