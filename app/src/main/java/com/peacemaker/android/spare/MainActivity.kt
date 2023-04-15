package com.peacemaker.android.spare

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.FirebaseApp
import com.peacemaker.android.spare.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

     lateinit var binding: ActivityMainBinding

    private val barMenuItems = setOf(R.id.splashScreenFragment,R.id.landingPageFragment, R.id.navigation_home)//set nav back arrows
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        val navController = navHostFragment.navController
        setupBottomNavMenu(navController)
       // binding.toolbar.setNavigationIcon(R.drawable.navigate_up)

        setSupportActionBar(binding.toolbar)
        setupActionBar(navController = navController, appBarConfig = barMenuItems)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            val showBottomNavOnIDs = listOf(R.id.navigation_home,R.id.navigation_wallet,R.id.navigation_chat, R.id.navigation_profile)
            if (destination.id in showBottomNavOnIDs) showVisibilityForBottomNav(true) else showVisibilityForBottomNav(false)
        }

        FirebaseApp.initializeApp(this)
    }

    //Using NavigationUI to configure Bottom Navigation
    private fun setupBottomNavMenu(navController: NavController) {
        val bottomNav: BottomNavigationView = binding.navView
        bottomNav.background = null
        bottomNav.setupWithNavController(navController)
    }

    private fun setupActionBar(
        navController: NavController,
        drawerLayoutId: Int?=null,
        appBarConfig : Set<Int>, ) {
        val drawerLayout : DrawerLayout? = drawerLayoutId?.let { findViewById(it) }
        val appBarConfiguration = AppBarConfiguration(appBarConfig,drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }


    //Using NavigationUI with an Options menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(findNavController(R.id.nav_host_fragment_activity_main)) || super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        val appBarConfiguration = AppBarConfiguration(barMenuItems)
        return findNavController(R.id.nav_host_fragment_activity_main).navigateUp(appBarConfiguration)
    }

    private fun onBackPress(){
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() { binding.navView.visibility = View.GONE }
        }
        this.onBackPressedDispatcher.addCallback(this, callback)
    }

    fun navigateToFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment_activity_main, fragment)
            .commit()
    }

    fun setProgressBar(visibility :Boolean){
        if (visibility){
            binding.progressBar.visibility = View.VISIBLE
            // Set the alpha value of the main layout to dim the screen
            binding.dimView.visibility = View.VISIBLE
            binding.progressBar.indeterminateDrawable = getDrawable(R.drawable.progress_bar)
        }else{
            binding.progressBar.visibility = View.GONE
            binding.dimView.visibility = View.GONE
        }
    }

    private fun showVisibilityForBottomNav(visibility: Boolean){
        if (visibility){
            binding.navView.visibility = View.VISIBLE
            binding.bottomAppBar.visibility = View.VISIBLE
            binding.fab.visibility = View.VISIBLE
        }else{
            binding.navView.visibility = View.GONE
            binding.bottomAppBar.visibility = View.GONE
            binding.fab.visibility = View.GONE
        }

    }
}