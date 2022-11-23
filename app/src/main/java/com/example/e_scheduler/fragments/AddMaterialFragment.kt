package com.example.e_scheduler.fragments

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.e_scheduler.R
import com.example.e_scheduler.entity.Material
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_add_material.*
import kotlinx.android.synthetic.main.fragment_add_material.autoCompleteTvBranch
import kotlinx.android.synthetic.main.fragment_add_material.autoCompleteTvSem
import kotlinx.android.synthetic.main.fragment_additional_profile.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.*

class AddMaterialFragment : Fragment(R.layout.fragment_add_material) {

    private val materials = FirebaseFirestore.getInstance().collection("materials")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val semesters = requireActivity().resources.getStringArray(R.array.semester)
        val arrayAdapterSem = ArrayAdapter(
            requireContext(),
            androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item,
            semesters
        )
        val branch = requireActivity().resources.getStringArray(R.array.branch)
        val arrayAdapterBranch = ArrayAdapter(
            requireContext(),
            androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item,
            branch
        )

        autoCompleteTvSem.setAdapter(arrayAdapterSem)
        autoCompleteTvBranch.setAdapter(arrayAdapterBranch)

        btn_add_material.setOnClickListener {

            CoroutineScope(Dispatchers.Main).launch {
                val uid = UUID.randomUUID().toString()
                val material = Material(
                    uid = uid,
                    sem = autoCompleteTvSem.text.trim().toString(),
                    branch = autoCompleteTvBranch.text.trim().toString(),
                    uploadTime = System.currentTimeMillis(),
                    topic = et_material_topic.text?.trim().toString(),
                    description = et_description.text?.trim().toString(),
                    documentUrl = et_material_url.text?.trim().toString()
                )

                materials.document(uid).set(material).await()
                findNavController().navigate(R.id.homeFragment)
            }

        }

    }

}