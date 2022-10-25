package com.example.e_scheduler.fragments

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.e_scheduler.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_additional_profile.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class AdditionalProfileFragment : Fragment(R.layout.fragment_additional_profile) {

    private val users = FirebaseFirestore.getInstance().collection("users")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setStatusBarTransparent()

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
        val blood = requireActivity().resources.getStringArray(R.array.blood)
        val arrayAdapterBlood = ArrayAdapter(
            requireContext(),
            androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item,
            blood
        )

        autoCompleteTvSem.setAdapter(arrayAdapterSem)
        autoCompleteTvBranch.setAdapter(arrayAdapterBranch)
        autoCompleteTvBloodGrp.setAdapter(arrayAdapterBlood)

        autoCompleteTvSem.setOnItemClickListener { parent, view, position, id ->
            val arrayAdapterClass = ArrayAdapter(
                requireContext(),
                androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item,
                names[position].first
            )
            val arrayAdapterLab = ArrayAdapter(
                requireContext(),
                androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item,
                names[position].second
            )

            autoCompleteTvClass.setAdapter(arrayAdapterClass)
            autoCompleteTvLab.setAdapter(arrayAdapterLab)

            tl_class.isVisible = true
            tl_lab.isVisible = true
        }

        btn_go.setOnClickListener {
            updateCredential(FirebaseAuth.getInstance().currentUser!!.uid)
        }

    }

    private fun updateCredential(uid: String) {
        val values: MutableMap<String, Any> =
            mutableMapOf(
                "branch" to autoCompleteTvBranch.text.trim().toString(),
                "semester" to autoCompleteTvSem.text.trim().toString(),
                "className" to autoCompleteTvClass.text.trim().toString(),
                "batch" to autoCompleteTvLab.text.trim().toString(),
                "address" to et_address.text?.trim().toString(),
                "fatherName" to et_fathername.text?.trim().toString(),
                "bloodGrp" to autoCompleteTvBloodGrp.text.trim().toString(),
                "fatherNumber" to et_fathernumber.text?.trim().toString(),
                "mobileNumber" to et_phone.text?.trim().toString(),
                "additionalDetailsAdded" to true
            )
        CoroutineScope(Dispatchers.Main).launch {
            try {
                users.document(uid).update(values).await()
                findNavController().navigate(R.id.homeFragment)
            } catch (e: Exception) {
                Toast.makeText(
                    requireActivity(),
                    e.message.toString(),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    }

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

val names: ArrayList<Pair<ArrayList<String>, ArrayList<String>>> =
    arrayListOf(
        Pair(
            arrayListOf("1CEIT-A", "1CEIT-B", "1CEIT-C", "1CEIT-D"),
            arrayListOf(
                "1AB1",
                "1AB2",
                "1AB3",
                "1AB4",
                "1AB5",
                "1AB6",
                "1AB7",
                "1AB8",
                "1AB9",
                "1AB10",
                "1AB11",
                "1AB12"
            )
        ),
        Pair(
            arrayListOf("2CEIT-A", "2CEIT-B", "2CEIT-C", "2CEIT-D"),
            arrayListOf(
                "2AB1",
                "2AB2",
                "2AB3",
                "2AB4",
                "2AB5",
                "2AB6",
                "2AB7",
                "2AB8",
                "2AB9",
                "2AB10",
                "2AB11",
                "2AB12"
            )
        ),
        Pair(
            arrayListOf("3CEIT-A", "3CEIT-B", "3CEIT-C", "3CEIT-D"),
            arrayListOf(
                "3AB1",
                "3AB2",
                "3AB3",
                "3AB4",
                "3AB5",
                "3AB6",
                "3AB7",
                "3AB8",
                "3AB9",
                "3AB10",
                "3AB11",
                "3AB12"
            )
        ),
        Pair(
            arrayListOf("4CEIT-A", "4CEIT-B", "4CEIT-C", "4CEIT-D"),
            arrayListOf(
                "4AB1",
                "4AB2",
                "4AB3",
                "4AB4",
                "4AB5",
                "4AB6",
                "4AB7",
                "4AB8",
                "4AB9",
                "4AB10",
                "4AB11",
                "4AB12"
            )
        ),
        Pair(
            arrayListOf("5CEIT-A", "5CEIT-B", "5CEIT-C", "5CEIT-D"),
            arrayListOf(
                "5AB1",
                "5AB2",
                "5AB3",
                "5AB4",
                "5AB5",
                "5AB6",
                "5AB7",
                "5AB8",
                "5AB9",
                "5AB10",
                "5AB11",
                "5AB12"
            )
        ),
        Pair(
            arrayListOf("6CEIT-A", "6CEIT-B", "6CEIT-C", "6CEIT-D"),
            arrayListOf(
                "6AB1",
                "6AB2",
                "6AB3",
                "6AB4",
                "6AB5",
                "6AB6",
                "6AB7",
                "6AB8",
                "6AB9",
                "6AB10",
                "6AB11",
                "6AB12"
            )
        ),
        Pair(
            arrayListOf("7CEIT-A", "7CEIT-B", "7CEIT-C", "7CEIT-D"),
            arrayListOf(
                "7AB1",
                "7AB2",
                "7AB3",
                "7AB4",
                "7AB5",
                "7AB6",
                "7AB7",
                "7AB8",
                "7AB9",
                "7AB10",
                "7AB11",
                "7AB12"
            )
        ),
        Pair(
            arrayListOf("8CEIT-A", "8CEIT-B", "8CEIT-C", "8CEIT-D"),
            arrayListOf(
                "8AB1",
                "8AB2",
                "8AB3",
                "8AB4",
                "8AB5",
                "8AB6",
                "8AB7",
                "8AB8",
                "8AB9",
                "8AB10",
                "8AB11",
                "8AB12"
            )
        )
    )