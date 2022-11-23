package com.example.e_scheduler.fragments

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.example.e_scheduler.R
import com.example.e_scheduler.adapter.ResourceAdapter
import com.example.e_scheduler.adapter.UserAdapter
import com.example.e_scheduler.entity.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_browse_profile.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class BrowseProfileFragment : Fragment(R.layout.fragment_browse_profile) {

    private val users = FirebaseFirestore.getInstance().collection("users")
    private lateinit var adapter: UserAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val branch = requireActivity().resources.getStringArray(R.array.branch)
        val arrayAdapterBranch = ArrayAdapter(
            requireContext(),
            androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item,
            branch
        )

        val role = requireActivity().resources.getStringArray(R.array.roles)
        val arrayAdapterRoles = ArrayAdapter(
            requireContext(),
            androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item,
            role
        )

        autoCompleteTvBranch.setAdapter(arrayAdapterBranch)
        autoCompleteTvSem.setAdapter(arrayAdapterRoles)

        btn_search.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                val user =
                    users.whereEqualTo("branch", autoCompleteTvBranch.text.trim().toString())
                        .whereEqualTo("role", autoCompleteTvSem.text.trim().toString()).get()
                        .await()
                        .toObjects(User::class.java) as ArrayList
                adapter = UserAdapter(requireContext(), user)
                lv_resource.adapter = adapter
            }
        }
    }

}