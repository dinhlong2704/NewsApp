package com.example.newsapp.view.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.newsapp.databinding.FragmentArticleBinding
import com.example.newsapp.db.ArticleDatabase
import com.example.newsapp.repository.NewsRepository
import com.example.newsapp.view.activity.NewsActivity
import com.example.newsapp.viewmodel.NewsViewModel
import com.example.newsapp.viewmodel.NewsViewModelProviderFactory

class ArticleFragment : BaseFragment<FragmentArticleBinding, NewsViewModel>() {
    private val args: ArticleFragmentArgs by navArgs<ArticleFragmentArgs>()

    // Khởi tạo Factory ngay khi cần
    override fun getViewModelFactory(): ViewModelProvider.Factory? {
        val repository = NewsRepository(ArticleDatabase(requireContext()))
        return NewsViewModelProviderFactory(requireActivity().application, repository)
    }

    override fun initView() {
        viewModel = (activity as NewsActivity).viewModel
        val article = args.article
        binding.webView.apply {
            webViewClient = WebViewClient()
            loadUrl(article.url)
        }
        binding.fab.setOnClickListener {
            binding.fab.isEnabled = false
            viewModel.saveArticle(article)
            // Kiểm tra xem bài viết đã tồn tại chưa
            viewModel.isArticleExist(article.url).observe(viewLifecycleOwner) { isExist ->
                val message = if (isExist) {
                    "Article already saved"
                } else {
                    "Article saved successfully"
                }
                Toast.makeText(this.context, message, Toast.LENGTH_SHORT).show()
                binding.fab.isEnabled = true
            }
        }
    }

    override fun getClassViewModel(): Class<NewsViewModel> {
        return NewsViewModel::class.java
    }

    override fun initViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentArticleBinding {
        return FragmentArticleBinding.inflate(inflater, container, false)
    }


}