package com.example.newsapp.view.activity

import androidx.lifecycle.ViewModelProvider
import com.example.newsapp.databinding.ActivityForgetPassBinding
import com.example.newsapp.viewmodel.CommonVM
import kotlin.reflect.KClass

class ForgetPassActivity : BaseActivity<ActivityForgetPassBinding, CommonVM>() {

    override fun initView() {
        binding.btnUpdateForPass.setOnClickListener {
            updatePass()
        }
    }

    private fun updatePass() {
        //do nothing
    }

    override fun initViewBinding(): ActivityForgetPassBinding {
        return ActivityForgetPassBinding.inflate(layoutInflater)
    }

    override fun initViewModel(): KClass<CommonVM> {
        return CommonVM::class
    }

}