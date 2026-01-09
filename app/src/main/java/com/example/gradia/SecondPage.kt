package com.example.gradia

import android.os.Bundle
import androidx.activity.addCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.gradia.databinding.ActivitySecondPageBinding
import com.example.gradia.ui.AllSemestersFragment
import com.example.gradia.ui.SemesterDetailsFragment
import com.example.gradia.viewmodel.StudentViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SecondPage : AppCompatActivity() {

    private lateinit var binding: ActivitySecondPageBinding
    private val viewModel: StudentViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySecondPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            viewModel.student.collectLatest { student ->
                binding.studentFullName.text = student?.name
                binding.studentRollNo.text = student?.rollNo

                var s: Double = 0.0
                var c: Double = 0.0

                student?.results.orEmpty().forEach { (_, semester) ->
                    s += semester.sgpa!! * semester.credits!!
                    c += semester.credits!!
                }

                val cgpa = if (c == 0.0) 0.0 else s / c
                binding.overallCgpa.text = String.format("%.2f", cgpa)
                binding.backlogs.text = student?.currentBacklogs?.size.toString()
                val hasNoBacklogs = student?.currentBacklogs.isNullOrEmpty()

                if (hasNoBacklogs) {
                    binding.constraintLayout.setBackgroundResource(R.drawable.second_layout_no_backlog_card)
                    binding.textView4.setTextColor(ContextCompat.getColor(this@SecondPage, R.color.no_backlog))
                    binding.backlogs.setTextColor(ContextCompat.getColor(this@SecondPage, R.color.no_backlog_number))
                } else {
                    binding.constraintLayout.setBackgroundResource(R.drawable.second_layout_backlog_card)
                    binding.textView4.setTextColor(ContextCompat.getColor(this@SecondPage, R.color.backlog))
                    binding.backlogs.setTextColor(ContextCompat.getColor(this@SecondPage, R.color.backlog_number))
                }
            }
        }

        binding.myButtonAllSemesters.setOnClickListener {
            loadFragment(AllSemestersFragment())
            binding.myButtonAllSemesters.setBackgroundResource(R.drawable.blue_button_background)
            binding.myButtonDetails.setBackgroundResource(R.drawable.white_button_background)
            binding.myButtonAllSemesters.setTextColor(ContextCompat.getColor(this, R.color.white))
            binding.myButtonDetails.setTextColor(ContextCompat.getColor(this, R.color.black))
        }

        binding.myButtonDetails.setOnClickListener {
            viewModel.selectSemester(viewModel.student.value?.results?.size?.minus(1) ?: 0)
            loadFragment(SemesterDetailsFragment())
            myButtonDetails()
        }

        loadFragment(AllSemestersFragment())

        onBackPressedDispatcher.addCallback(this) {

            val currentFragment = supportFragmentManager.findFragmentById(R.id.resultFragmentContainer)

            when (currentFragment) {

                is com.example.gradia.ui.SemesterDetailsFragment -> {
                    loadFragment(com.example.gradia.ui.AllSemestersFragment())
                    binding.myButtonAllSemesters.performClick()
                }

                is com.example.gradia.ui.AllSemestersFragment -> {
                    finish()
                }

                else -> finish()
            }
        }

    }

    fun myButtonDetails() {
        binding.myButtonAllSemesters.setBackgroundResource(R.drawable.white_button_background)
        binding.myButtonDetails.setTextColor(ContextCompat.getColor(this, R.color.white))
        binding.myButtonDetails.setBackgroundResource(R.drawable.blue_button_background)
        binding.myButtonAllSemesters.setTextColor(ContextCompat.getColor(this, R.color.black))
    }

    private fun loadFragment(f: androidx.fragment.app.Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.resultFragmentContainer, f)
            .commit()
    }
}
