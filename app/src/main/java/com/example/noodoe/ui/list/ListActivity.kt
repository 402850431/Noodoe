package com.example.noodoe.ui.list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.example.noodoe.R

class ListActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration : AppBarConfiguration
    private lateinit var navController : NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        navController = this.findNavController(R.id.list_fragment_container)
        NavigationUI.setupActionBarWithNavController(this, navController)
        appBarConfiguration = AppBarConfiguration(navController.graph)

        menu?.add(0, 0, 0, R.string.time_zone)?.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, appBarConfiguration)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            0 -> {
                navigate()
            }
            else -> {}
        }

        return super.onOptionsItemSelected(item)
    }

    private fun navigate() {
        when (navController.currentDestination?.id) {
            R.id.listFragment -> {
                navController.navigate(ListFragmentDirections.actionListFragmentToUpdateUserFragment())
            }
            R.id.updateUserFragment -> {

            }
        }
    }
}