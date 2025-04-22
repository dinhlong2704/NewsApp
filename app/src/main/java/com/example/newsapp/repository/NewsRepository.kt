package com.example.newsapp.repository

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newsapp.api.RetrofitInstance
import com.example.newsapp.db.ArticleDatabase
import com.example.newsapp.ui.NewsViewModel

class NewsRepository(private val db: ArticleDatabase) {
    private val api = RetrofitInstance.api // Truy cập API từ Retrofit
}