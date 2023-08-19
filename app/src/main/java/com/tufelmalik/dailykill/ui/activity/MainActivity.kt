package com.tufelmalik.dailykill.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import com.tufelmalik.dailykill.R
import com.tufelmalik.dailykill.data.repository.NewsRepository
import com.tufelmalik.dailykill.data.utilities.ApiInstance
import com.tufelmalik.dailykill.databinding.ActivityMainBinding
import com.tufelmalik.dailykill.ui.fragments.NewsFragment
import com.tufelmalik.dailykill.ui.fragments.SavedNewsFragment
import com.tufelmalik.dailykill.ui.fragments.WeatherFragment
import com.tufelmalik.dailykill.viewmodel.NewsViewModel
import com.tufelmalik.dailykill.viewmodel.NewsViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.mainHeader.text = getString(R.string.app_name)
        replaceFragment(NewsFragment())

        val apiService = ApiInstance.apiInterface
        val newsRepository = NewsRepository(apiService)
        val viewModel: NewsViewModel by viewModels {
            NewsViewModelFactory(newsRepository)
        }



        binding.bottomNav.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.idNews_nav -> {
                    replaceFragment(NewsFragment())

                    binding.mainHeader.text = "DailyHunt"
                }
                R.id.idWorld_nav -> {
                    binding.mainHeader.text = "Saved News"
                    replaceFragment(SavedNewsFragment())
                }
                R.id.idWether_nav -> {
                    binding.mainHeader.text = "Weather Updates"
                    replaceFragment(WeatherFragment())
                }
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, fragment)
            .addToBackStack(null)
            .commit()
    }
}
