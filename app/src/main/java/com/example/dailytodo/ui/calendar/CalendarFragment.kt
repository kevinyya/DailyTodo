package com.example.dailytodo.ui.calendar

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.dailytodo.databinding.FragmentCalendarBinding
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import java.util.Calendar


class CalendarFragment : Fragment() {

    private var _binding: FragmentCalendarBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val calendarViewModel =
            ViewModelProvider(this).get(CalendarViewModel::class.java)

        _binding = FragmentCalendarBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // val textView: TextView = binding.textCalendar
        // calendarViewModel.text.observe(viewLifecycleOwner) {
        //     textView.text = it
        // }

        // Highlight Current Date
        val currentDate: Calendar = Calendar.getInstance()
        binding.calendarView.setSelectedDate(currentDate.getTime())


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}