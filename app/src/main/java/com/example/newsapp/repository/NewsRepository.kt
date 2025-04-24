package com.example.newsapp.repository

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newsapp.api.RetrofitInstance
import com.example.newsapp.db.ArticleDatabase
import com.example.newsapp.model.Article
import com.example.newsapp.model.NewsResponse
import com.example.newsapp.ui.NewsViewModel
import retrofit2.Response

class NewsRepository(private val db: ArticleDatabase) {
    private val api = RetrofitInstance.api // Truy cập API từ Retrofit

    suspend fun getBreakingNews(countryCode: String, pageNumber: Int) =
        api.getBreakingNews(countryCode, pageNumber)

    suspend fun searchBreakingNews(countryCode: String, pageNumber: Int) =
        api.searchForNews(countryCode, pageNumber)

    // Lưu bài viết
    suspend fun upserttArticle(article: Article) {
        val isExist = db.getArticleDao().isArticleExist(article.url) > 0
        if (!isExist) {
            db.getArticleDao().upsert(article)
        }
    }

    // Lấy danh sách bài viết đã lưu (LiveData)
    fun getSavedNews() = db.getArticleDao().getAllArticles()

    // Xóa bài viết
    suspend fun deleteArticle(article: Article) {
        db.getArticleDao().deleteArticle(article)
    }

    //Kiểm tra xem bài viết đã tồn tại chưa
    suspend fun isArticleExist(url: String): Boolean {
        return db.getArticleDao().isArticleExist(url) > 0
    }
}