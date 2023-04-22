package com.peacemaker.android.spare

import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.FirebaseApp
import com.peacemaker.android.spare.databinding.ActivityMainBinding
import com.peacemaker.android.spare.databinding.SendModalSheetBinding
import com.peacemaker.android.spare.ui.util.changeBackNavigationIcon


class MainActivity : AppCompatActivity() {

     lateinit var binding: ActivityMainBinding

    private val barMenuItems = setOf(R.id.splashScreenFragment,R.id.landingPageFragment)//disable nav back arrows
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        FirebaseApp.initializeApp(this)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        // Set a transparent drawable as the default icon to avoid it being displayed
        //actionBar?.setHomeAsUpIndicator(ColorDrawable(Color.TRANSPARENT))
        //changeBackNavigationIcon(actionBar)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        val navController = navHostFragment.navController
        setupBottomNavMenu(navController)
        setSupportActionBar(binding.toolbar)
        setupActionBar(navController = navController, appBarConfig = barMenuItems)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            val showBottomNavOnIDs = listOf(R.id.navigation_home,R.id.navigation_wallet,R.id.navigation_chat, R.id.navigation_profile, R.id.addMoneyFragment)
            if (destination.id in showBottomNavOnIDs) {
                showVisibilityForBottomNav(true)
            } else showVisibilityForBottomNav(false)
        }

        binding.fab.setOnClickListener {
            showModalSheet(navController)
        }
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
            binding.progressBar.indeterminateDrawable = ResourcesCompat.getDrawable(resources,R.drawable.progress_bar, null)
        }else{
            binding.progressBar.visibility = View.GONE
            binding.dimView.visibility = View.GONE
        }
    }

    private fun showVisibilityForBottomNav(visibility: Boolean){
        if (visibility){
            binding.coordinatorLayout.visibility = View.VISIBLE
        }else{
            binding.coordinatorLayout.visibility = View.GONE
        }
    }

    private fun onApplyWindowInsetsListenerOnBottomNav(){
        ViewCompat.setOnApplyWindowInsetsListener(binding.navView) { view, insets ->
            // Check if the keyboard is visible
            if (insets.isVisible(WindowInsetsCompat.Type.ime())) {
                // Hide the BottomNavigationView
                view.visibility = View.GONE
            } else {
                // Show the BottomNavigationView
                view.visibility = View.VISIBLE
            }
            // Update the padding of the BottomNavigationView to match the system insets
            view.updatePadding(bottom = insets.systemWindowInsetBottom)
            insets
        }
    }
    private fun showModalSheet(navController: NavController) {
        val binding = SendModalSheetBinding.inflate(layoutInflater)
        val dialog = BottomSheetDialog(this)
        val makePayment = binding.pay2AnotherUser
        val requestPayment = binding.requestPayment
        val payBills = binding.payBills

        makePayment.setOnClickListener {
            navController.navigate(R.id.action_global_sendFragment)
            dialog.cancel()
        }
        requestPayment.setOnClickListener {

        }
        payBills.setOnClickListener {

        }

        dialog.setContentView(binding.root)
        dialog.show()
    }

}