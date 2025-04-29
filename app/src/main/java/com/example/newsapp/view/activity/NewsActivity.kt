package com.example.newsapp.view.activity

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment

import androidx.navigation.ui.setupWithNavController
import coil.load
import coil.transform.CircleCropTransformation
import com.example.newsapp.R
import com.google.android.gms.auth.api.identity.Identity
import com.example.newsapp.databinding.ActivityNewsBinding
import com.example.newsapp.db.ArticleDatabase
import com.example.newsapp.firebase.GoogleAuthUiClient
import com.example.newsapp.repository.NewsRepository
import com.example.newsapp.viewmodel.NewsViewModelProviderFactory
import com.example.newsapp.viewmodel.NewsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.reflect.KClass


class NewsActivity : BaseActivity<ActivityNewsBinding, NewsViewModel>() {


    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var googleAuthUiClient: GoogleAuthUiClient

    // Khởi tạo Factory ngay khi cần
    override fun getViewModelFactory(): ViewModelProvider.Factory {
        return NewsViewModelProviderFactory(
            application,
            NewsRepository(ArticleDatabase(this))
        )
    }

    override fun initView() {
        // 2. Khởi tạo SignInClient
        val oneTapClient = Identity.getSignInClient(applicationContext)

        // 3. Khởi tạo GoogleAuthUiClient với 2 tham số
        googleAuthUiClient = GoogleAuthUiClient(
            context = applicationContext, oneTapClient = oneTapClient
        )
        // Lấy NavController từ NavHostFragment
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.newsNavHostFragment) as NavHostFragment
        val navController = navHostFragment.navController

        // Thiết lập BottomNavigationView với NavController
        binding.bottomNavigationView.setupWithNavController(navController)

        // Drawer toggle setup
        toggle = ActionBarDrawerToggle(this, binding.drawerLayout, R.string.open, R.string.close)
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setupDrawer()
    }

    override fun initViewBinding(): ActivityNewsBinding {
        return ActivityNewsBinding.inflate(layoutInflater)
    }

    override fun initViewModel(): KClass<NewsViewModel> {
        return NewsViewModel::class
    }

    private fun setupDrawer() {
        val headerView = binding.navigationView.getHeaderView(0)
        val imageViewAvatar = headerView.findViewById<ImageView>(R.id.imageViewAvatar)
        val textViewUsername = headerView.findViewById<TextView>(R.id.textViewUsername)

        // Load ảnh đại diện với Coil và crop hình tròn
        val userData = googleAuthUiClient.getSignedInUser()
        userData?.let {
            textViewUsername.text = it.username ?: "Unknown"
            it.profilePictureUrl?.let { url ->
                imageViewAvatar.load(url) {
                    crossfade(true)
                    placeholder(R.drawable.ic_launcher_background)
                    transformations(CircleCropTransformation())
                }
            }

            binding.navigationView.setNavigationItemSelectedListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.menu_logout -> {
                        logout()
                        true
                    }

                    else -> false
                }
            }
        }


    }

    private fun logout() {
        CoroutineScope(Dispatchers.Main).launch {
            googleAuthUiClient.signOut()
            Toast.makeText(this@NewsActivity, "Signed out", Toast.LENGTH_LONG).show()
            finish()
        }
    }

    override fun onOptionsItemSelected(item: android.view.MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}