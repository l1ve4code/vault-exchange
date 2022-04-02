package com.example.coursework.ui.grow_screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.coursework.ui.ShowBottomNavigation
import com.example.coursework.data.data_storage.Data
import com.example.coursework.databinding.FragmentGrowScreenBinding
import com.example.coursework.ui.VaultApplication
import com.example.testmvvm.data.model.Vault
import com.example.testmvvm.data.viewmodel.VaultViewModel
import com.example.testmvvm.data.viewmodel.VaultViewModelFactory
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate


class GrowScreen : Fragment() {

    private lateinit var binding: FragmentGrowScreenBinding
    private lateinit var barChart: BarChart
    lateinit var mainViewModel: VaultViewModel

    private var graphList: MutableList<Vault> = Data.getGraphList("RUB")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGrowScreenBinding.inflate(inflater, container, false)

        val repository = (activity?.application as VaultApplication).vaultRepository

        mainViewModel = ViewModelProvider(this, VaultViewModelFactory(repository)).get(VaultViewModel::class.java)

        mainViewModel.vaults.observe(viewLifecycleOwner, Observer {
            Data.setVaultList(it as MutableList<Vault>)
        })

        graphList = Data.getGraphList("RUB")

        barChart = binding.growChart

        barChart.notifyDataSetChanged()
        barChart.invalidate()

        initBarChart()

        val entries = ArrayList<BarEntry>()

        for (i in graphList.indices) {
            val vault = graphList[i].rates.find { it.name == "EUR" }
            vault?.cost?.let {
                BarEntry((i + 1).toFloat(),
                    it.toFloat()
                )
            }?.let { entries.add(it) }
        }

        val barDataSet = BarDataSet(entries, "")
        barDataSet.setColors(*ColorTemplate.COLORFUL_COLORS)

        val data = BarData(barDataSet)
        barChart.data = data

        barChart.invalidate()

        val showBottomNavigation = requireActivity() as ShowBottomNavigation
        showBottomNavigation.showBottomNav()

        return binding.root
    }

    private fun initBarChart() {

        barChart.axisLeft.setDrawGridLines(false)
        val xAxis: XAxis = barChart.xAxis
        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(false)

        barChart.axisRight.isEnabled = false
        barChart.legend.isEnabled = false
        barChart.description.isEnabled = false
        barChart.animateY(1000)
        barChart.setVisibleXRange(10f, 10f)

        xAxis.position = XAxis.XAxisPosition.TOP

        val labels = mutableListOf<String>("")
        for (i in graphList){
            labels.add(i.date)
        }

        xAxis.valueFormatter = IndexAxisValueFormatter(labels)

        xAxis.setDrawLabels(true)
        xAxis.granularity = 1f

    }

}

class MyAxisFormatter : ValueFormatter() {
    override fun getAxisLabel(value: Float, axis: AxisBase?): String {
        val index = value.toInt()
        for (i in Data.getGraphList("RUB").indices) return Data.getGraphList("RUB")[i].date
        return ""
    }
}
