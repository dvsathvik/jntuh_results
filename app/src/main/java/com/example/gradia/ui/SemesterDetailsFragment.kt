package com.example.gradia.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gradia.databinding.FragmentSemesterDetailsBinding
import com.example.gradia.viewmodel.StudentViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SemesterDetailsFragment : Fragment() {

    private var _binding: FragmentSemesterDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: StudentViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSemesterDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.selectedSemesterIndex.collectLatest { index ->
                if (index == -1) return@collectLatest

                val student = viewModel.student.value
                val semList = student?.results?.values?.toList() ?: emptyList()
                val semester = semList.getOrNull(index)
                semester?.let {
                    binding.txtCurrentSem.text = "Semester ${index + 1}"
                    binding.txtCurrentSemSGPA.text = it.sgpa.toString()
                    binding.txtCurrentSemSubjects.text = "${it.subject?.size ?: 0} Subjects"

                    val adapter = SubjectAdapter(it.subject ?: emptyList())
                    binding.recyclerView.adapter = adapter
                }

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
