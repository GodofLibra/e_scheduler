package com.example.e_scheduler

import android.app.Dialog
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_notice.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class NoticeActivity : Fragment(R.layout.fragment_notice) {

    private val users = FirebaseFirestore.getInstance().collection("users")

    private lateinit var adapter: ReceipesAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //createNotificationChannel()
        setStatusBarTransparent()


        //lvNotes.adapter = adapter

        CoroutineScope(Dispatchers.Main).launch {
            val user = users.document(FirebaseAuth.getInstance().currentUser!!.uid).get().await()
                .toObject(User::class.java)!!
            Toast.makeText(requireActivity(), user.Role, Toast.LENGTH_SHORT).show()
            fab_add_note.isVisible = user.Role.trim() != "Student"
        }
        fab_add_note.setOnClickListener {
            val dialog = Dialog(requireActivity())
            dialog.setContentView(R.layout.add_edit_note_dialog)
            val noteTitle = dialog.findViewById<TextInputEditText>(R.id.et_note_title)
            val noteSubTitle = dialog.findViewById<TextInputEditText>(R.id.et_note_sub_title)
            val noteDescription = dialog.findViewById<TextInputEditText>(R.id.et_note_description)
            val reciepeLink = dialog.findViewById<TextInputEditText>(R.id.et_note_link)
            val btnOk = dialog.findViewById<TextView>(R.id.btn_ok)
            btnOk.setOnClickListener {
                if (noteTitle.text.toString().isEmpty() or noteSubTitle.text.toString()
                        .isEmpty() or noteDescription.text.toString()
                        .isEmpty() or reciepeLink.text!!.isBlank()
                ) {
                    Toast.makeText(
                        requireActivity(),
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

        }
    }

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

const val channelId = "notesChannel"
const val channelName = "Notes Channel"

