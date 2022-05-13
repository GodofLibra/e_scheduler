package com.example.e_scheduler

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView

class AnnouncementActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_announcement)

        val bottomNavBar = findViewById<BottomNavigationView>(R.id.bottom_nav_bar)

        bottomNavBar.selectedItemId = R.id.Announcement
        bottomNavBar.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.home -> {
                    true
                }
                R.id.Announcement -> {
                    Intent(this, AnnouncementActivity::class.java).also {
                        startActivity(it)
                        finish()
                    }
                    true
                }
                R.id.notice -> {
                    Intent(this, NoticeActivity::class.java).also {
                        startActivity(it)
                        finish()
                    }
                    true
                }
                R.id.profile -> {
                    Intent(this, DashboardActivity::class.java).also {
                        startActivity(it)
                        finish()
                    }
                    true
                }
                else -> false
            }
        }
    }
}