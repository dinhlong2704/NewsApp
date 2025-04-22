package com.example.newsapp.repository

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newsapp.api.RetrofitInstance
import com.example.newsapp.db.ArticleDatabase
import com.example.newsapp.model.NewsResponse
import com.example.newsapp.ui.NewsViewModel
import retrofit2.Response

class NewsRepository(private val db: ArticleDatabase) {
    private val api = RetrofitInstance.api // Truy cập API từ Retrofit

    suspend fun getBreakingNews(countryCode: String, pageNumber: Int) =
        api.getBreakingNews(countryCode, pageNumber)
}