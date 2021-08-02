package com.example.rateofcurrency.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.rateofcurrency.R
import com.example.rateofcurrency.databinding.ActivityMainBinding
import com.example.rateofcurrency.ui.fragments.CurrencyInformationFragmentDirections
import com.example.rateofcurrency.utilits.Const

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()

        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(navController.graph)

        appBarConfiguration.topLevelDestinations.addAll(
            setOf(
                R.id.authorizationFragment, R.id.currencyInformationFragment
            )
        )
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.currencyInformationFragment) {
                binding.toolbar.inflateMenu(R.menu.toggle_menu)

                binding.toolbar.setOnMenuItemClickListener {
                    when (it.itemId) {
                        R.id.user_info -> navController.navigate(
                            CurrencyInformationFragmentDirections.actionCurrencyInformationFragmentToUserProfileFragment()
                        )
                        R.id.exit_account -> {
                            clearAuthData()
                            navController.navigate(
                                R.id.action_currencyInformationFragment_to_authorizationFragment
                            )
                        }
                    }
                    return@setOnMenuItemClickListener false
                }
            } else {
                binding.toolbar.menu.clear()
            }
        }
    }

    @SuppressLint("CommitPrefEdits")
    private fun clearAuthData() {
        val sharedPref = this.getSharedPreferences(Const.SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editorSharedPref: SharedPreferences.Editor = sharedPref.edit()
        editorSharedPref.clear().apply()
    }
}