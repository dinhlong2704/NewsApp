package com.example.newsapp.view.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.newsapp.databinding.ActivitySplashBinding
import com.example.newsapp.viewmodel.CommonVM
import kotlin.reflect.KClass


class SplashActivity : BaseActivity<ActivitySplashBinding, CommonVM>() {

    override fun initView() {
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
            finish() // kết thúc SplashActivity
        }, 2000) // delay 2 giây
    }

    override fun initViewBinding(): ActivitySplashBinding {
        return ActivitySplashBinding.inflate(layoutInflater)
    }

    override fun initViewModel(): KClass<CommonVM> {
        return CommonVM::class
    }
}