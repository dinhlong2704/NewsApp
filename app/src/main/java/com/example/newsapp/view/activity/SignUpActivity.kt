package com.example.newsapp.view.activity

import androidx.lifecycle.ViewModelProvider
import com.example.newsapp.databinding.ActivitySignUpBinding
import com.example.newsapp.viewmodel.CommonVM
import kotlin.reflect.KClass

class SignUpActivity : BaseActivity<ActivitySignUpBinding, CommonVM>() {
    override fun initView() {
        //do Nothing
    }
    override fun initViewBinding(): ActivitySignUpBinding {
        return ActivitySignUpBinding.inflate(layoutInflater)
    }

    override fun initViewModel(): KClass<CommonVM> {
        return CommonVM::class
    }
}