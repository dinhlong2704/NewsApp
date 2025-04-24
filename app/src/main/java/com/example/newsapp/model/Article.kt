package com.example.newsapp.model


import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "articles")
data class Article(
    @PrimaryKey
    @NonNull
    @SerializedName("url")
    val url: String,
    @SerializedName("author")
    val author: String?,
    @SerializedName("content")
    val content: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("publishedAt")
    val publishedAt: String?,
    @SerializedName("source")
    val source: Source? = null,
    @SerializedName("title")
    val title: String?,
    @SerializedName("urlToImage")
    val urlToImage: String?
) : Serializable