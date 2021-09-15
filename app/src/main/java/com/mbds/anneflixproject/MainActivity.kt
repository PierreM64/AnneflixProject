package com.mbds.anneflixproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mbds.anneflixproject.adapter.ListMoviesAdapter
import com.mbds.anneflixproject.fragments.*
import com.mbds.anneflixproject.models.Movie
import com.mbds.anneflixproject.repository.MoviesRepository
import kotlin.reflect.KClass

class MainActivity : AppCompatActivity(), NavigationListener {

    //lateinit var navController: NavController
    lateinit var bottomNav : BottomNavigationView
    //lateinit var appBarConfiguration: AppBarConfiguration

    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //for change title toolbar by title fragment
        toolbar = findViewById(R.id.toolbar_main)
        setSupportActionBar(toolbar)

        /* Bottom Navigation */
        bottomNav = findViewById(R.id.bottom_navigation)
        //navController = findNavController(R.id.fragment_container)
        //bottomNav.setupWithNavController(navController)

        //Navigation UP Bottom
        //appBarConfiguration = AppBarConfiguration(navController.graph)
        //For the arrow back
        //NavigationUI.setupActionBarWithNavController(this, navController)


        //---
        bottomNav.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.homeFragment -> showFragment(HomeFragment())
                R.id.favoriteFragment -> showFragment(FavoriteFragment())
                R.id.seriesFragment -> showFragment(SerieFragment())
                R.id.settingFragment -> showFragment(SettingFragment())
            }
            true
        }

        showFragment(HomeFragment())
        //showHomeFragment()
    }

    // Fonction permettant de définir quel est le fragment au démarrage de l'application
    //override fun -> NavigationListener dans newsletterOthers
    override fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, fragment)
            addToBackStack(null)
        }.commit()
    }
    /*
    fun updateTitleActionBar(title : Int) {
        actionBar?.setTitle(title)
    }*/
    // Fonction pour update le titre selon le fragment
    override fun updateTitle(title: Int) {
        toolbar.setTitle(title)
    }

    private fun showHomeFragment() {
        val transaction = supportFragmentManager.beginTransaction()
        val fragment = supportFragmentManager.findFragmentByTag("home_fragment")
        if (fragment == null) {
            transaction.add(R.id.fragment_container, HomeFragment(), "home_fragment")
        } else {
            transaction.show(fragment)
        }
        transaction.commit()
    }

}

            //  ____________________________________________________________
            // |  -_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_- |
            // ||                                                          ||
            // ||             * * *          * *                           ||
            // ||            /\     *      *    *    *****                 ||
            // ||           /  \     *    *      | **    ***               ||
            // ||          ******      **       **                         ||
            // ||         *      *            **  \                        ||
            // ||        *        *          *     *                       ||
            // ||          *    *             * * *                        ||
            // ||            **                                    P.M.    ||
            // ||                                                          ||
            // |  -_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_- |
            //  ____________________________________________________________