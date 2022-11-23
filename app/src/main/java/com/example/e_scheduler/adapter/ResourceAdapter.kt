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
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*


class ResourceAdapter(var context: Context, var materialList: ArrayList<Material>) : BaseAdapter() {
    private val materials = FirebaseFirestore.getInstance().collection("materials")

    override fun getCount(): Int {
        return materialList.size
    }

    override fun getItem(position: Int): Any {
        return materialList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = LayoutInflater.from(context).inflate(R.layout.row_layout_material, parent, false)
        val topic = view.findViewById<TextView>(R.id.tv_topic)
        val url = view.findViewById<TextView>(R.id.tv_url)
        val description = view.findViewById<TextView>(R.id.tv_description_material)
        val parentLayout = view.findViewById<ConstraintLayout>(R.id.parent_layout_material)
        topic.text = materialList[position].topic
        url.text = materialList[position].documentUrl
        description.text = materialList[position].description

        url.movementMethod = LinkMovementMethod.getInstance()
        url.setOnClickListener {
            val browserIntent =
                Intent(Intent.ACTION_VIEW, Uri.parse(materialList[position].documentUrl))
            context.startActivity(browserIntent)
        }

        parentLayout.setOnClickListener {
            onResourceClickListener?.let {
                it(materialList[position])
            }
        }

        return view
    }

    private var onResourceClickListener: ((Material) -> Unit)? = null

    fun setOnResourceClickListener(listener: (Material) -> Unit) {
        onResourceClickListener = listener
    }
}