package com.example.gradia.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.gradia.data.Subject
import com.example.gradia.databinding.ItemSubjectBinding

class SubjectAdapter :
    ListAdapter<Subject, SubjectAdapter.SubjectViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubjectViewHolder {
        val binding = ItemSubjectBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SubjectViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SubjectViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class SubjectViewHolder(private val binding: ItemSubjectBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(subject: Subject) {
            binding.txtSubjectName.text = subject.name
            binding.txtCode.text = subject.code
            binding.txtGp.text = "GP: ${subject.gp}"
            binding.txtCredit.text = "Credits: ${subject.credit}"
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Subject>() {
        override fun areItemsTheSame(old: Subject, new: Subject) = old.code == new.code
        override fun areContentsTheSame(old: Subject, new: Subject) = old == new
    }
}
