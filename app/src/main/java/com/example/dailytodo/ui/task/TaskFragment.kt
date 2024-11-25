package com.example.dailytodo.ui.task

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.dailytodo.databinding.FragmentTaskBinding

class TaskFragment : Fragment() {

    private val taskAdapter: TaskAdapter by lazy { TaskAdapter() }

    private val taskViewModel: TaskViewModel by viewModels()

    private var _binding: FragmentTaskBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val taskViewModel =
            ViewModelProvider(this).get(TaskViewModel::class.java)

        _binding = FragmentTaskBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Configure RecyclerView
        val taskRV = binding.taskRV
        taskRV.adapter = taskAdapter
        taskRV.layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)

        // Observe LiveData
        taskViewModel.getAllTasks.observe(viewLifecycleOwner, Observer { data ->
            taskAdapter.setData(data)
        })

        binding.addBtn.setOnClickListener{
            Log.d("Debug", "Add Button Trigger");
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}