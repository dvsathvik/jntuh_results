package com.example.gradia.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.gradia.databinding.FragmentSemesterResultBinding
import com.example.gradia.viewmodel.StudentViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SemesterResultFragment : Fragment() {

    private var _binding: FragmentSemesterResultBinding? = null
    private val binding get() = _binding!!

    private val viewModel: StudentViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSemesterResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        lifecycleScope.launch {
            viewModel.semesterResult.collectLatest { result ->
                result?.let {
                    binding.tvHeader.text =
                        "Roll No: ${it.rollNo} | Semester: ${it.sem}"

                    binding.tvSgpa.text = "SGPA: ${it.data.sgpa}"

                    binding.tvSubjects.text = it.data.subjects.joinToString("\n\n") { subject ->
                        """
                        ${subject.name}
                        Code: ${subject.code}
                        Credits: ${subject.credit}
                        GP: ${subject.gp}
                        """.trimIndent()
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
