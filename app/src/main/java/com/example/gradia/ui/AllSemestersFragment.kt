package com.example.gradia.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gradia.R
import com.example.gradia.SecondPage
import com.example.gradia.databinding.FragmentAllSemestersBinding
import com.example.gradia.viewmodel.StudentViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class AllSemestersFragment : Fragment() {

    private var _binding: FragmentAllSemestersBinding? = null
    private val binding get() = _binding!!

    private val viewModel: StudentViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAllSemestersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        lifecycleScope.launch {
            viewModel.student.collectLatest { student ->
                student?.let { s ->

                    // s.results is a Map<String, Semester>
                    val semList = s.results.values.toList()

                    val adapter = SemAdapter(semList) { _, position ->
                        (requireActivity() as SecondPage).myButtonDetails()

                        viewModel.selectSemester(position)

                        openSemesterDetails()
                    }

                    binding.recyclerView.adapter = adapter
                }
            }
        }
    }

    private fun openSemesterDetails() {
        val fragment = SemesterDetailsFragment()

        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.resultFragmentContainer, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
