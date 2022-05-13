package com.example.e_scheduler

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class DashboardActivity : Fragment(R.layout.activity_dashboard)  {
    lateinit var heart: AnimationDrawable
    private val users = FirebaseFirestore.getInstance().collection("users")
    private val receipes = FirebaseFirestore.getInstance().collection("receipes")


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setStatusBarTransparent()



//

        bottom_nav_bar.selectedItemId = R.id.profile


//        side_nav_view.setNavigationItemSelectedListener {
//            when (it.itemId) {
//                R.id.announcemnets -> {
//                    Intent(this, AnnouncementActivity::class.java).also { intent ->
//                        startActivity(intent)
//                    }
//                    true
//                }
//                R.id.notices ->{
//                    Intent(this, NoticeActivity::class.java).also { intent ->
//                        startActivity(intent)
//                    }
//                    true
//                }
//                R.id.profile ->{
//                    Intent(this, DashboardActivity::class.java).also { intent ->
//                        startActivity(intent)
//                    }
//                    true
//                }
//                else -> {
//                    true
//                }
//            }
//        }

        bottom_nav_bar.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    true
                }
                R.id.Announcement -> {
                    Intent(requireActivity(), AnnouncementActivity::class.java).also {
                        startActivity(it)
                        requireActivity().finish()
                    }
                    true
                }
                R.id.notice -> {
                    Intent(requireActivity(), NoticeActivity::class.java).also {
                        startActivity(it)
                        requireActivity().finish()
                    }
                    true
                }
                R.id.profile -> {
                    Intent(requireActivity(), DashboardActivity::class.java).also {
                        startActivity(it)
                        requireActivity().finish()
                    }
                    true
                }
                else -> false
            }
        }
//        ivHeart.setBackgroundResource(R.drawable.heart_animation)
//        heart = ivHeart.background as AnimationDrawable
//        heart.start()

        CoroutineScope(Dispatchers.Main).launch {
            val user = users.document(Firebase.auth.currentUser!!.uid).get().await()
                .toObject(User::class.java)!!

            Log.d("TAG_DASHBOARD", "onCreate: $user")
            tv_user_name.text = user.userName
            tv_email.text = Firebase.auth.currentUser!!.email
            tv_name.text = user.userName
            tv_user_enr_number.text = user.enrNo
//            tvUserBio.text = user.bio
            tv_email.text = Firebase.auth.currentUser!!.email


        }


        btn_logout.setOnClickListener {

            Firebase.auth.signOut()

            Intent(requireContext(), LoginActivity::class.java).apply {
                startActivity(this)
                requireActivity().finish()
            }
        }

    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//

//
//    }

    private fun setStatusBarTransparent() {
        if (Build.VERSION.SDK_INT in 19..20) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true)
            }
        }
        if (Build.VERSION.SDK_INT >= 19) {
            requireActivity().window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
            requireActivity().window.statusBarColor = Color.TRANSPARENT
        }
    }

    private fun setWindowFlag(bits: Int, on: Boolean) {
        val winParameters = requireActivity().window.attributes
        if (on) {
            winParameters.flags = winParameters.flags or bits
        } else {
            winParameters.flags = winParameters.flags and bits.inv()
        }
        requireActivity().window.attributes = winParameters
    }
}

