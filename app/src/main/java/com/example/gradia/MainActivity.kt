package com.example.gradia

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.gradia.databinding.ActivityMainBinding
import com.example.gradia.viewmodel.StudentViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: StudentViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = Color.TRANSPARENT
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupSemesterDropdown()
        setupClickListeners()
        observeLoadingState()
        observeNavigationEvent()
    }

    private fun setupSemesterDropdown() {
        val semesters = (1..8).map { "Semester $it" }
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            semesters
        )
    }

    private fun observeLoadingState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loading.collectLatest { isLoading ->
                    if (isLoading) {
                        binding.iconArrow.visibility = View.GONE
                        binding.textGetResults.visibility = View.GONE
                        binding.progressLoading.visibility = View.VISIBLE
                    } else {
                        binding.progressLoading.visibility = View.GONE
                        binding.iconArrow.visibility = View.VISIBLE
                        binding.textGetResults.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    private fun observeNavigationEvent() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.navigateEvent.collect {
                    startActivity(Intent(this@MainActivity, SecondPage::class.java))
                }
            }
        }
    }

    private fun setupClickListeners() {
        binding.btnGetSemesters.setOnClickListener {
            val rollNo = binding.editTextRoll.text.toString().trim()

            if (rollNo.isEmpty()) return@setOnClickListener

            viewModel.fetchStudentByRollNo(rollNo)

        }
    }
}
