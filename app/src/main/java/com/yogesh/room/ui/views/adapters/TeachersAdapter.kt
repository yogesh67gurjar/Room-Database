package com.yogesh.room.ui.views.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yogesh.room.R
import com.yogesh.room.data.models.TeacherEntity
import com.yogesh.room.utils.Constants
import com.yogesh.room.utils.RecyclerViewClicklistenerWithType
import de.hdodenhof.circleimageview.CircleImageView


class TeachersAdapter(
    private val teachersList: MutableList<TeacherEntity>,
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

        holder.itemView.setOnClickListener {
            if (!holder.adapterPosition.equals(RecyclerView.NO_POSITION)) {
                recyclerViewClicklistenerWithType.onClick(holder.adapterPosition, Constants.TEACHER)
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
    }
}