package com.android.figma_task

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.android.figma_task.databinding.FragmentTasksBinding

class TasksFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentTasksBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_tasks, container, false
        )

        binding.livedataExample.setOnClickListener {
            findNavController().navigate(TasksFragmentDirections.actionTaskDestinationToTitleDestination())
        }
        binding.figmaLoginButton.setOnClickListener {
            findNavController().navigate(TasksFragmentDirections.actionTasksFragmentToFigmaLoginFragment())
        }
        binding.figmaDetailButton.setOnClickListener {
            findNavController().navigate(TasksFragmentDirections.actionTasksFragmentToDetailFigmaFragment())
        }

        return binding.root
    }

}