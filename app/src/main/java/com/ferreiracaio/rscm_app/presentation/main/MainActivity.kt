package com.ferreiracaio.rscm_app.presentation.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.fragment.app.Fragment
import com.ferreiracaio.rscm_app.R
import com.ferreiracaio.rscm_app.databinding.ActivityMainBinding
import com.ferreiracaio.rscm_app.presentation.main.home.HomeFragment
import com.ferreiracaio.rscm_app.presentation.main.profile.ProfileFragment
import com.ferreiracaio.rscm_app.presentation.main.search.SearchFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        replaceFragment(HomeFragment())

        binding.bottomNavigation.setOnItemSelectedListener {
            when(it.itemId){
                R.id.homeNav -> replaceFragment(HomeFragment())
                R.id.searchNav -> replaceFragment(SearchFragment())
                R.id.profileNav -> replaceFragment(ProfileFragment())
            }
            true
        }

    }

    private fun replaceFragment(fragment: Fragment) = supportFragmentManager.beginTransaction().apply {
        replace(R.id.nav_host_fragment_content_main, fragment)
        commit()
    }
}