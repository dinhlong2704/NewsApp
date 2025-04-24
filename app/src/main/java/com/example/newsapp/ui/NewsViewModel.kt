package com.example.newsapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.model.Article
import com.example.newsapp.model.NewsResponse
import com.example.newsapp.repository.NewsRepository
import com.example.newsapp.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel(private val newsRepository: NewsRepository) : ViewModel() {
    val breakingNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    private var breakingNewsPage = 1

    val searchNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    private var searchNewsNewsPage = 1

    init {
        getBreakingNews("us")
        searchNews("us")
    }

    fun getBreakingNews(countryCode: String) = viewModelScope.launch {
        breakingNews.postValue(Resource.Loading()) // Gửi trạng thái Loading

        val response = newsRepository.getBreakingNews(countryCode, breakingNewsPage)
        breakingNews.postValue(handleBreakingNewsResponse(response)) // Xử lý response
    }

    fun searchNews(searchQuery: String) = viewModelScope.launch {
        searchNews.postValue(Resource.Loading())

        val response = newsRepository.searchBreakingNews(searchQuery, searchNewsNewsPage)
        searchNews.postValue(handleSearchNewsResponse(response)) // Xử lý response khi tìm kếm
    }

    private fun handleBreakingNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
            return Resource.Error("Response body is null")
        }
        return Resource.Error("Error: ${response.message()} (Code: ${response.code()})")
    }

    private fun handleSearchNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
            return Resource.Error("Response body is null")
        }
        return Resource.Error("Error: ${response.message()} (Code: ${response.code()})")
    }

    // Lưu bài viết
    fun saveArticle(article: Article) = viewModelScope.launch {
        newsRepository.upserttArticle(article)
    }

    // Lấy danh sách bài viết đã lưu
    fun getSavedNews() = newsRepository.getSavedNews()

    // Xóa bài viết
    fun deleteArticle(article: Article) = viewModelScope.launch {
        newsRepository.deleteArticle(article)
    }
    // Kiểm tra bài vết có tồn tại không
    fun isArticleExist(url: String): LiveData<Boolean> {
        val result = MutableLiveData<Boolean>()
        viewModelScope.launch {
            val isExist = newsRepository.isArticleExist(url)
            result.postValue(isExist)
        }
        return result
    }
}