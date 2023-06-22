package com.example.tofuapp.ui.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.tofuapp.R
import com.example.tofuapp.databinding.ActivityNavigationBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NavigationActivity : AppCompatActivity() {
    private val binding by lazy { ActivityNavigationBinding.inflate(layoutInflater) }
    private val navController by lazy {
        (supportFragmentManager.findFragmentById(R.id.navigationFragmentContainerView) as NavHostFragment).navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.navBar.setupWithNavController(navController)
        setBottomNavVisibility()
    }

    private fun setBottomNavVisibility() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.launcherFragment -> binding.navBar.isVisible = false
                R.id.registerScreenFragment -> binding.navBar.isVisible = false
                R.id.addRecipeScreenFragment -> binding.navBar.isVisible = false
                R.id.recipeDetailsScreenFragment -> binding.navBar.isVisible = false
                R.id.loginScreenFragment -> binding.navBar.isVisible = false
                else -> binding.navBar.isVisible = true
            }
        }
    }
}