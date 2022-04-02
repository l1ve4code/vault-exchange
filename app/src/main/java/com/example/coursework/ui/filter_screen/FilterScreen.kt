package com.example.coursework.ui.filter_screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.coursework.data.data_storage.Data
import com.example.coursework.databinding.FragmentFilterScreenBinding
import com.example.coursework.ui.ShowBottomNavigation

class FilterScreen : Fragment() {

    private lateinit var binding: FragmentFilterScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFilterScreenBinding.inflate(inflater, container, false)

        binding.allBlock.setOnClickListener {
            Data.setFilter("all")
        }
        binding.monthBlock.setOnClickListener {
            Data.setFilter("month")
        }
        binding.weekBlock.setOnClickListener {
            Data.setFilter("week")
        }
        binding.selectDate.setOnClickListener {
            if(binding.fromDate.text.toString() != "" && binding.toDate.text.toString() != ""){
                Data.setFilter("select")
                Data.setStartDate(binding.fromDate.text.toString())
                Data.setEndDate(binding.toDate.text.toString())
            }
        }

        val showBottomNavigation = requireActivity() as ShowBottomNavigation
        showBottomNavigation.showBottomNav()

        return binding.root
    }

}