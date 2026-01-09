package com.example.gradia.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gradia.R
import com.example.gradia.data.Subject
import com.example.gradia.databinding.SubjectCardBinding

class SubjectAdapter(
    private val subjects: List<Subject>
) : RecyclerView.Adapter<SubjectAdapter.SubjectViewHolder>() {

    inner class SubjectViewHolder(val binding: SubjectCardBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubjectViewHolder {
        val binding = SubjectCardBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return SubjectViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SubjectViewHolder, position: Int) {
        val item = subjects[position]

        holder.binding.txtCode.text = item.code
        holder.binding.txtCredit.text = "Credits: ${item.credit.toInt()}"
        holder.binding.txtSubjectName.text = item.name

        if (item.gp!! == 0){
            holder.binding.rightGradeBox.setBackgroundResource(R.drawable.grade_box_bg_red)
            holder.binding.txtGp.setTextColor(holder.binding.root.context.getColor(R.color.backlog))
            holder.binding.txtGpValue.setTextColor(holder.binding.root.context.getColor(R.color.backlog_number))
        }

        holder.binding.txtGp.text = item.grade

        holder.binding.txtGpValue.text = item.gp.toString()
    }

    override fun getItemCount() = subjects.size
}
