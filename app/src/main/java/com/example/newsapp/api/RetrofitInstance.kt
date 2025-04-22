package com.example.newsapp.api

import com.example.newsapp.util.Constants.Companion.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {
    companion object {
        private val retrofit by lazy {
            // Cấu hình logging để debug
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            // Tạo HTTP client với interceptor
            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()
            // Khởi tạo Retrofita
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()) // Chuyển đổi JSON → Kotlin
                .client(client)
                .build()
        }
        // API service
        val api by lazy {
            retrofit.create(NewsAPI::class.java)
        }

    }
}