package com.example.e_scheduler

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_announcement.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class AnnouncementFragment : Fragment(R.layout.fragment_announcement) {

    private val users = FirebaseFirestore.getInstance().collection("users")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        CoroutineScope(Dispatchers.Main).launch {
            val user = users.document(FirebaseAuth.getInstance().currentUser!!.uid).get().await()
                .toObject(User::class.java)!!
            Toast.makeText(requireActivity(), user.Role, Toast.LENGTH_SHORT).show()
            fab_add_note.isVisible = user.Role.trim() != "Student"
        }

    }
}