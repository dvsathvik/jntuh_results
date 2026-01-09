package com.example.gradia.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gradia.data.Semester
import com.example.gradia.databinding.SemesterCardBinding

class SemAdapter(
    private val semesters: List<Semester>,
    private val onItemClick: (Semester) -> Unit
) : RecyclerView.Adapter<SemAdapter.UserViewHolder>() {

    inner class UserViewHolder(val binding: SemesterCardBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = SemesterCardBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val item = semesters[position]

        holder.binding.txtSemName.text = "Semester ${position + 1}"

        holder.binding.txtSgpa.text = item.sgpa?.toString() ?: "--"

        holder.binding.txtCredit.text = "Credits: ${item.credits ?: 0}"

        if (item.status == "PASS") {
            holder.binding.backlogBadge.visibility = View.GONE
        } else {
            holder.binding.backlogBadge.visibility = View.VISIBLE
        }

        val progress = ((item.sgpa ?: 0.0) / 10.0 * 100).toInt()
        holder.binding.semProgress.progress = progress

        holder.binding.root.setOnClickListener {
            onItemClick(item)
        }
    }

    override fun getItemCount() = semesters.size
}
