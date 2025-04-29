package com.example.newsapp.view.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding


abstract class BaseFragment<B : ViewBinding, V : ViewModel> : Fragment(), View.OnClickListener {

    var data: Any? = null
    protected lateinit var contextMain: Context
    protected lateinit var binding: B
    protected lateinit var viewModel: V

    // cho articleFragment cần khoi tao viewModel dùng factory
    open fun getViewModelFactory(): ViewModelProvider.Factory? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        contextMain = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = initViewBinding(inflater, container)
        viewModel = if (getViewModelFactory() != null) {
            ViewModelProvider(this, getViewModelFactory()!!)[getClassViewModel()]
        } else {
            ViewModelProvider(this)[getClassViewModel()]
        }
        initView()
        return binding.root
    }

    override fun onClick(v: View) {
        v.startAnimation(AnimationUtils.loadAnimation(contextMain, androidx.appcompat.R.anim.abc_fade_in))
        clickView(v)
    }

    protected abstract fun initView()
    protected abstract fun getClassViewModel(): Class<V>
    protected abstract fun initViewBinding(inflater: LayoutInflater, container: ViewGroup?): B

    protected open fun clickView(v: View) {
        // Default empty
    }

}
