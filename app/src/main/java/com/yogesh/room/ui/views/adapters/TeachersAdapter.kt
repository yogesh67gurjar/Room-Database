package com.yogesh.room.ui.views.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yogesh.room.R

import com.yogesh.room.data.models.TeacherEntity
import com.yogesh.room.utils.Constants
import com.yogesh.room.utils.RecyclerViewClicklistenerWithType
import de.hdodenhof.circleimageview.CircleImageView


class TeachersAdapter(
    private val teachersList: MutableList<TeacherEntity>,
    private val context: Context,
    private val recyclerViewClicklistenerWithType: RecyclerViewClicklistenerWithType
) :
    RecyclerView.Adapter<TeachersAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.rv_teacher, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val singleUnit = teachersList[position]
        holder.name.text = singleUnit.name
        holder.subject.text = singleUnit.subject

        holder.editBtn.setOnClickListener {
            if (holder.adapterPosition != RecyclerView.NO_POSITION) {
                recyclerViewClicklistenerWithType.onClick(
                    holder.adapterPosition,
                    Constants.EDIT
                )
            }
        }


    }


override fun getItemCount(): Int {
    return teachersList.size
}

class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
    val name: TextView = itemView.findViewById(R.id.name)
    val subject: TextView = itemView.findViewById(R.id.subject)
    val img: CircleImageView = itemView.findViewById(R.id.img)
    val editBtn: ImageView = itemView.findViewById(R.id.editBtn)
}
}