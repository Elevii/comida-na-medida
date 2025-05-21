package com.elevii.comidanamedida.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.setupWithNavController
import com.elevii.comidanamedida.R
import com.elevii.comidanamedida.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.nav_host_fragment
        ) as androidx.navigation.fragment.NavHostFragment

        val navController = navHostFragment.navController

        binding.bottomNavigation.setupWithNavController(navController)

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_menu -> {
                    navController.navigate(R.id.menuFragment)

                    true
                }
                R.id.action_historic -> {
                    Toast.makeText(this, "Essa funcionalidade estará disponível em breve!", Toast.LENGTH_LONG).show()
//                    navController.navigate(R.id.historicFragment)

                    false
                }
                R.id.action_settings -> {
                    Toast.makeText(this, "Disponível em breve!", Toast.LENGTH_SHORT).show()

                    false
                }
                else -> false
            }
        }
    }
}

