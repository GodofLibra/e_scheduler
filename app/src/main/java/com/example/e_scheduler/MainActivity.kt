package com.example.e_scheduler

import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.e_scheduler.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.app_bar_dashboard_side_nav_avtivity.*
import kotlinx.android.synthetic.main.content_main_activity.*
import kotlinx.android.synthetic.main.content_main_activity.view.*
import kotlinx.android.synthetic.main.fragment_notice.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private val users = FirebaseFirestore.getInstance().collection("users")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarDashboardSideNavAvtivity.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController =
            findNavController(R.id.nav_host_fragment_content_dashboard_side_nav_avtivity)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.home, R.id.announcementFragment, R.id.noticeActivity, R.id.dashboardActivity
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        bottomt_nav_bar.setupWithNavController(navController)
        navView.setupWithNavController(navController)


        CoroutineScope(Dispatchers.Main).launch {
            val user = users.document(FirebaseAuth.getInstance().currentUser!!.uid).get().await()
                .toObject(User::class.java)!!

            val navMenu: Menu = navView.menu
            navMenu.findItem(R.id.admin).isVisible = user.role != "Student"
            navMenu.findItem(R.id.manage_users).isVisible = user.role != "Student"
            navMenu.findItem(R.id.manage_ann).isVisible = user.role != "Student"
            navMenu.findItem(R.id.manage_not).isVisible = user.role != "Student"

        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id in listOf(R.id.loginActivity, R.id.signUpActivity)) {
                bottomt_nav_bar.visibility = View.GONE
                toolbar.visibility = View.GONE
            } else {
                bottomt_nav_bar.visibility = View.VISIBLE
                toolbar.visibility = View.VISIBLE
            }
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController =
            findNavController(R.id.nav_host_fragment_content_dashboard_side_nav_avtivity)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}