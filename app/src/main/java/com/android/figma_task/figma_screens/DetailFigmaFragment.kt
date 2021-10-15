package com.android.figma_task.figma_screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.figma_task.R
import com.android.figma_task.databinding.FragmentDetailFigmaBinding

class DetailFigmaFragment : Fragment() {


    private lateinit var adapter: Adapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<FragmentDetailFigmaBinding>(
            inflater, R.layout.fragment_detail_figma, container, false
        )

        arrayListOf(
            Famous(R.drawable.famous1, "James Mangold", "Director"),
            Famous(R.drawable.famous2, "Matt Damon", "Carroll"),
            Famous(R.drawable.famous3, "Christian Bale", "Ken Miles"),
            Famous(R.drawable.famous4, "Caitriona Balfe", "Mollie")
        ).apply {
            adapter = Adapter(this)
        }

        binding.apply {
            recylerView.setHasFixedSize(true)
            recylerView.layoutManager =
                LinearLayoutManager(layoutInflater.context, LinearLayoutManager.HORIZONTAL, false)
            recylerView.adapter = adapter
        }
        return binding.root
    }

}