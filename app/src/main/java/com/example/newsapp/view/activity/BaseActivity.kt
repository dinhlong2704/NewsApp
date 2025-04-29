package com.example.newsapp.view.activity

import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding

import kotlin.reflect.KClass

abstract class BaseActivity<T : ViewBinding, M : ViewModel> : AppCompatActivity(),
    View.OnClickListener {
    protected lateinit var binding: T
    lateinit var viewModel: M

    // cho newsAct cần khoi tao viewModel dùng factory
    open fun getViewModelFactory(): ViewModelProvider.Factory? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = initViewBinding()
        val factory = getViewModelFactory()
        viewModel = if (factory != null) {
            ViewModelProvider(this, factory)[initViewModel().java]
        } else {
            ViewModelProvider(this)[initViewModel().java]
        }
        setContentView(binding.root)

        initView()
    }

    protected abstract fun initView()

    protected abstract fun initViewBinding(): T

    protected abstract fun initViewModel(): KClass<M>

    override fun onClick(v: View) {
        v.startAnimation(AnimationUtils.loadAnimation(this, androidx.appcompat.R.anim.abc_fade_in))
        clickView(v)
    }

    protected open fun clickView(v: View) {
    }

}