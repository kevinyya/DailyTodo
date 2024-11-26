package com.example.dailytodo.ui.task

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.dailytodo.AddActivity
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
        taskSwipe(taskRV)

        // Observe LiveData
        taskViewModel.getAllTasks.observe(viewLifecycleOwner, Observer { data ->
            // Log.d("Debug", "Data Changed");
            taskAdapter.setData(data)
        })

        binding.addBtn.setOnClickListener{
            // Log.d("Debug", "Add Button Trigger");
            val intent = Intent(requireContext(), AddActivity::class.java)
            startActivity(intent)
        }

        return root
    }

    private fun taskSwipe(recyclerView: RecyclerView) {
        val swipeFinishCallback = object : TaskSwipe() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val deleteTask = taskAdapter.taskList[viewHolder.adapterPosition]
                // Delete Task from Database
                taskViewModel.deleteTask(deleteTask)
                taskAdapter.notifyItemRemoved(viewHolder.adapterPosition)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeFinishCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}