package com.example.coursework.ui.transaction_screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coursework.ui.FilterWindowManager
import com.example.coursework.ui.ShowBottomNavigation
import com.example.coursework.data.data_storage.Data
import com.example.coursework.databinding.FragmentTransactionScreenBinding
import com.example.coursework.ui.transaction_screen.adapter.TransactionAdapter

class TransactionScreen : Fragment() {

    private lateinit var binding: FragmentTransactionScreenBinding
    private lateinit var adapter: TransactionAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTransactionScreenBinding.inflate(inflater, container, false)

        binding.filterButton.setOnClickListener {
            val filterWindowManager = requireActivity() as FilterWindowManager
            filterWindowManager.openFilterWindow()
        }

        adapter = TransactionAdapter()

        if(Data.getFilter() == "all"){
            binding.selectedFilter.text = "Filter: all"
        }
        else if(Data.getFilter() == "month"){
            binding.selectedFilter.text = "Filter: month"

        }
        else if(Data.getFilter() == "week"){
            binding.selectedFilter.text = "Filter: week"
        }

        else if(Data.getFilter() == "select"){
            binding.selectedFilter.text = "Filter: dates"
        }

        val showBottomNavigation = requireActivity() as ShowBottomNavigation
        showBottomNavigation.showBottomNav()

        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context)

        binding.fragmentHistoryContainer.layoutManager = layoutManager
        binding.fragmentHistoryContainer.adapter = adapter

        return binding.root
    }

}