package com.example.newsapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentArticleBinding
import com.example.newsapp.databinding.FragmentBreakingNewsBinding
import com.example.newsapp.ui.NewsActivity
import com.example.newsapp.ui.NewsViewModel
import com.google.android.material.snackbar.Snackbar

class ArticleFragment : Fragment(R.layout.fragment_article) {
    private lateinit var viewModel: NewsViewModel
    private lateinit var binding: FragmentArticleBinding
    private val args: ArticleFragmentArgs by navArgs<ArticleFragmentArgs>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentArticleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
                Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
                binding.fab.isEnabled = true
            }
        }
    }


}