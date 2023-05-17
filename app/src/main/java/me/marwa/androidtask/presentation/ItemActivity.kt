package me.marwa.androidtask.presentation

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.fragment.NavHostFragment
import dagger.hilt.android.AndroidEntryPoint
import me.marwa.androidtask.R
import me.marwa.androidtask.data.model.Product
import me.marwa.androidtask.databinding.ActivityItemBinding

@AndroidEntryPoint
class ItemActivity : AppCompatActivity() {
    private lateinit var navGraph: NavGraph
    private lateinit var navController: NavController
    private lateinit var binding: ActivityItemBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment =
            (supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_item) as NavHostFragment)
        navController = navHostFragment.navController
        val inflater = navController.navInflater
        navGraph = inflater.inflate(R.navigation.item_navigation)
        navigate()
    }

    private fun navigate() {
        if (intent.hasExtra("product")) {
            val product = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent.extras?.getParcelable("product", Product::class.java)
            } else {
                intent.extras?.getParcelable("product")!!
            }
            navGraph.setStartDestination(R.id.navigation_details)
            val bundle = Bundle().apply { putParcelable("product", product) }
            navController.setGraph(navGraph, bundle)
        } else {
            navGraph.setStartDestination(R.id.navigation_search)
            navController.graph = navGraph
        }
    }

    companion object {
        private const val TAG = "ItemActivity"
    }
}