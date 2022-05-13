package com.example.e_scheduler

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.*
import kotlin.collections.ArrayList

class NoticeActivity : AppCompatActivity() {

    private val users = FirebaseFirestore.getInstance().collection("users")

    private lateinit var adapter: ReceipesAdapter


    override fun onStart() {
//
//        val bottomNavBar = findViewById<BottomNavigationView>(R.id.bottom_nav_bar)
//        bottomNavBar.menu.findItem(R.id.notes).isChecked = true
//        getUpdatedList()
        super.onStart()
    }


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notice)
        //createNotificationChannel()
        setStatusBarTransparent()




        //lvNotes.adapter = adapter



        val bottomNavBar = findViewById<BottomNavigationView>(R.id.bottom_nav_bar)

        bottomNavBar.selectedItemId = R.id.notice
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
        val fabAddNote = findViewById<FloatingActionButton>(R.id.fab_add_note)

        CoroutineScope(Dispatchers.Main).launch {
            val user = users.document(FirebaseAuth.getInstance().currentUser!!.uid).get().await().toObject(User::class.java)!!
            Toast.makeText(this@NoticeActivity, user.Role, Toast.LENGTH_SHORT).show()
            fabAddNote.isVisible = user.Role.trim() != "Student"
        }
        fabAddNote.setOnClickListener {
            val dialog = Dialog(this)
            dialog.setContentView(R.layout.add_edit_note_dialog)
            val noteTitle = dialog.findViewById<TextInputEditText>(R.id.et_note_title)
            val noteSubTitle = dialog.findViewById<TextInputEditText>(R.id.et_note_sub_title)
            val noteDescription = dialog.findViewById<TextInputEditText>(R.id.et_note_description)
            val reciepeLink = dialog.findViewById<TextInputEditText>(R.id.et_note_link)
            val btnOk = dialog.findViewById<TextView>(R.id.btn_ok)
            btnOk.setOnClickListener {
                if (noteTitle.text.toString().isEmpty() or noteSubTitle.text.toString()
                        .isEmpty() or noteDescription.text.toString().isEmpty() or reciepeLink.text!!.isBlank()
                ) {
                    Toast.makeText(
                        this,
                        "Please enter all fields\nAll fields are required",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
//                    val receipe = Receipes(
//                        uid = id,
//                        receipeName = noteTitle.text.toString(),
//                        typeOfReceipe = noteSubTitle.text.toString(),
//                        receipeDescription = noteDescription.text.toString(),
//                        owner = Firebase.auth.currentUser!!.uid,
//                        link = reciepeLink.text.toString()
//                    )

                    CoroutineScope(Dispatchers.Main).launch {



                    }
                    getUpdatedList()
                    dialog.dismiss()
                }
            }
            dialog.show()

    }}

    private fun getUpdatedList() {
//        CoroutineScope(Dispatchers.Main).launch {
//            val abc = receipes.get().await().toObjects(Receipes::class.java) as ArrayList
//            adapter = ReceipesAdapter(application, abc)
//            val lvNotes: ListView = findViewById(R.id.lv_notes)
//            lvNotes.adapter = adapter
//        }
    }

//    private fun createNotificationChannel() {
//        val notificationManager =
//            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val description = "Channel for sending notes notification"
//            val importance = NotificationManager.IMPORTANCE_HIGH
//            val channel = NotificationChannel(channelId, channelName, importance)
//            channel.description = description
//            channel.vibrationPattern = longArrayOf(0, 1000, 500, 1000)
//            channel.enableVibration(true)
//            notificationManager.createNotificationChannel(channel)
//        }
//    }

    private fun setStatusBarTransparent() {
        if (Build.VERSION.SDK_INT in 19..20) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true)
            }
        }
        if (Build.VERSION.SDK_INT >= 19) {
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
            window.statusBarColor = Color.TRANSPARENT
        }
    }

    private fun setWindowFlag(bits: Int, on: Boolean) {
        val winParameters = window.attributes
        if (on) {
            winParameters.flags = winParameters.flags or bits
        } else {
            winParameters.flags = winParameters.flags and bits.inv()
        }
        window.attributes = winParameters
    }

    override fun onDestroy() {
        super.onDestroy()
        finishAffinity()
    }
}

const val channelId = "notesChannel"
const val channelName = "Notes Channel"

