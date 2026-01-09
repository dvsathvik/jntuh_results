package com.example.gradia.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.gradia.R
import com.example.gradia.data.Semester

class SemesterDetailsFragment : Fragment() {

    private lateinit var data: Semester

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        data = requireArguments().getParcelable("semester")!!
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_semester_details, container, false)
    }

    companion object {
        fun newInstance(semester: Semester): SemesterDetailsFragment {
            val fragment = SemesterDetailsFragment()
            val bundle = Bundle()
            bundle.putParcelable("semester", semester)
            fragment.arguments = bundle
            return fragment
        }
    }
}
