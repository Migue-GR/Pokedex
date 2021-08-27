package com.pokedex.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.transition.TransitionManager
import com.google.android.material.snackbar.Snackbar
import com.pokedex.R
import com.pokedex.databinding.ActivityMainBinding
import com.pokedex.model.enums.PokeError
import com.pokedex.model.enums.PokeError.*
import com.pokedex.utils.UiEventsManager
import com.pokedex.utils.UiEventsManager.shouldShowRemotePokes
import com.pokedex.utils.UiEventsManager.showError
import com.pokedex.utils.extensions.launchWithDelay
import com.pokedex.utils.extensions.showToast
import com.pokedex.view.pokelist.PokeListFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navHost: NavHostFragment
    private lateinit var navController: NavController
    private lateinit var errorSnackBar: Snackbar
    private var doubleBackToExitPressedOnce: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navHost = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHost.navController
        setUpErrorSnackBar()
        observeUiEvents()
    }

    private fun setUpErrorSnackBar() {
        @ColorInt
        val bgColor = ContextCompat.getColor(this@MainActivity, R.color.red)
        val textColor = ContextCompat.getColor(this@MainActivity, R.color.white)
        errorSnackBar = Snackbar.make(binding.root, "", Snackbar.LENGTH_SHORT)
        errorSnackBar.setBackgroundTint(bgColor)
        errorSnackBar.setTextColor(textColor)
    }

    private fun observeUiEvents() {
        UiEventsManager.run {
            showLoading.observe(this@MainActivity, {
                TransitionManager.beginDelayedTransition(binding.layoutActivityMain)
                binding.pgbMain.visibility = if (it) View.VISIBLE else View.GONE
                binding.bgProgressBar.visibility = if (it) View.VISIBLE else View.GONE
                binding.blockerViewsScreen.visibility = if (it) View.VISIBLE else View.GONE
            })
            error.observe(this@MainActivity, errorObserver)
        }
    }

    private val errorObserver = Observer<PokeError> { error ->
        when (error) {
            UNKNOWN -> {
            }
            SOMETHING_WENT_WRONG -> {
                errorSnackBar.setText(R.string.something_went_wrong).show()
                showError(UNKNOWN)
            }
            ERROR_WHILE_GETTING_POKES -> {
                errorSnackBar.setText(R.string.error_while_getting_pokes).show()
                showError(UNKNOWN)
            }
            ERROR_POKEMON_NOT_FOUND -> {
                errorSnackBar.setText(R.string.error_pokemon_not_found).show()
                showError(UNKNOWN)
            }
        }
    }

    override fun onBackPressed() {
        when (navController.currentDestination?.label) {
            PokeListFragment::class.simpleName -> pressAgainToExit()
            else -> super.onBackPressed()
        }
    }

    private fun pressAgainToExit() {
        if (doubleBackToExitPressedOnce) {
            shouldShowRemotePokes = true
            finishAffinity()
            return
        } else {
            doubleBackToExitPressedOnce = true
            showToast(getString(R.string.press_again_to_exit))
            lifecycleScope.launchWithDelay(2000) { doubleBackToExitPressedOnce = false }
        }
    }
}