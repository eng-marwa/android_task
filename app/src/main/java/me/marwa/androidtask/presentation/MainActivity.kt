package me.marwa.androidtask.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import me.marwa.androidtask.R
import me.marwa.androidtask.databinding.ActivityMainBinding
import me.marwa.androidtask.utils.CheckConnection
import me.marwa.androidtask.utils.showToast

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    var binding: ActivityMainBinding? = null
    private val productViewModel by viewModels<ProductsViewModel>()
    private var isConnected: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        isConnected = CheckConnection().isNetworkAvailable(this)
        setContentView(binding?.root)
        setupNavigation()
        getProducts()
        binding?.search?.setOnClickListener {
            startActivity(Intent(this, ItemActivity::class.java))
        }
    }


    private fun getProducts() {
        if (isConnected) {
            productViewModel.getProducts()
        } else {
            showToast(getString(R.string.check_internet_connection))
        }
    }

    private fun setupNavigation() {
        val navView = binding?.navView
        navController = findNavController(R.id.nav_host_fragment_activity_main)
        navView?.setupWithNavController(navController)
    }

}
