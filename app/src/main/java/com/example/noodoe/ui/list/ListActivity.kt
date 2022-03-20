package com.example.noodoe.ui.list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import com.example.noodoe.R

class ListActivity : AppCompatActivity() {

    private val navController  by lazy {  findNavController(R.id.fragment_container) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
//        toolbar.inflateMenu(R.menu.custom_menu)
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_setting -> {
                    navigate()
                }
                else -> {}
            }
            false
        }
        setSupportActionBar(toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menu?.add(R.string.time_zone).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

        return super.onCreateOptionsMenu(menu)
    }

    private fun navigate() {
        when (navController.currentDestination?.id) {
            R.id.listFragment -> {
                navController.navigate(ListFragmentDirections.actionListFragmentToUpdateUserFragment())
            }
        }
    }
}