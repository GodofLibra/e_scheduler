package com.example.e_scheduler.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat.startActivity
import com.example.e_scheduler.R
import com.example.e_scheduler.entity.Material
import com.example.e_scheduler.entity.User
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*


class UserAdapter(var context: Context, var userList: ArrayList<User>) : BaseAdapter() {
    private val users = FirebaseFirestore.getInstance().collection("users")

    override fun getCount(): Int {
        return userList.size
    }

    override fun getItem(position: Int): Any {
        return userList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = LayoutInflater.from(context).inflate(R.layout.row_layout_users, parent, false)
        val uname = view.findViewById<TextView>(R.id.tv_username)
        val sem = view.findViewById<TextView>(R.id.tv_sem)
        val enr = view.findViewById<TextView>(R.id.tv_enrnum)
        val mobile = view.findViewById<TextView>(R.id.tv_mobile)
        uname.text = userList[position].userName
        sem.text = userList[position].semester
        enr.text = userList[position].enrNo
        mobile.text = userList[position].mobileNumber

        return view
    }

}