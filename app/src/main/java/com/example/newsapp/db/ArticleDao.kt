package com.example.newsapp.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.newsapp.model.Article

@Dao
interface ArticleDao {
    // 1. Hàm thêm/cập nhật bài báo
    @Insert(onConflict = OnConflictStrategy.REPLACE) // Nếu bài báo đã tồn tại, ghi đè lên.
    suspend fun upsert(article: Article): Long

    @Query("SELECT * FROM articles")
    fun getAllArticles(): LiveData<List<Article>> // cập nhật UI khi dữ liệu thay đổi dùng liveData

    @Delete
    suspend fun deleteArticle(article: Article)

    // Thêm hàm kiểm tra bài viết theo url
    @Query("SELECT EXISTS(SELECT 1 FROM articles WHERE url = :url)")
    suspend fun isArticleExist(url: String): Long
}