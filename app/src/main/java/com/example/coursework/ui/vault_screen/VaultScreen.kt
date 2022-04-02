package com.example.coursework.ui.vault_screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coursework.R
import com.example.coursework.ui.ShowBottomNavigation
import com.example.coursework.ui.VaultWindowManager
import com.example.coursework.data.data_storage.Data
import com.example.coursework.databinding.FragmentVaultScreenBinding
import com.example.coursework.ui.VaultApplication
import com.example.coursework.ui.vault_screen.adapter.VaultAdapter
import com.example.testmvvm.data.model.Vault
import com.example.testmvvm.data.viewmodel.VaultViewModel
import com.example.testmvvm.data.viewmodel.VaultViewModelFactory

interface vaultWindowManager{
    fun openTransactionWindow()
    fun changeLongVault()
    fun loadLongClick()
}

class VaultScreen : Fragment(), vaultWindowManager {

    private lateinit var binding: FragmentVaultScreenBinding
    private lateinit var adapter: VaultAdapter
    lateinit var mainViewModel: VaultViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVaultScreenBinding.inflate(inflater, container, false)

        val repository = (activity?.application as VaultApplication).vaultRepository

        mainViewModel = ViewModelProvider(this, VaultViewModelFactory(repository)).get(VaultViewModel::class.java)

        mainViewModel.vaults.observe(viewLifecycleOwner, Observer {
            Data.setVaultList(it as MutableList<Vault>)
        })

        val vaultWindowManagerMain = requireActivity() as VaultWindowManager

        Data.setLongVault(null)

        adapter = VaultAdapter(object : vaultWindowManager {
            override fun openTransactionWindow(){
                vaultWindowManagerMain.openTransactionWindow()
            }

            override fun changeLongVault() {
                binding.vaultName.text = Data.getLongVault()?.base
                binding.vaultScreenWithButton.visibility = View.VISIBLE
                binding.selectedValueText.visibility = View.VISIBLE
            }

            override fun loadLongClick() {
                if (Data.isInFavouritesList(Data.getLongVault()!!.base)) {
                    binding.starIcon.setImageResource(R.drawable.ic_star)
                }else{
                    binding.starIcon.setImageResource(R.drawable.ic_star_outline)
                }
            }
        })

        adapter.notifyDataSetChanged()

        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context)

        binding.vaultScreenWithButton.setOnLongClickListener {
            if (Data.isInFavouritesList(Data.getLongVault()!!.base)) {
                Data.getLongVault()?.let { it1 -> Data.addFirstVault(it1) }
                Data.getLongVault()?.let { it1 -> Data.removeLongVault(it1) }
            }else{
                Data.removeLongVault()
            }
            adapter.notifyDataSetChanged()
            binding.vaultScreenWithButton.visibility = View.GONE
            binding.selectedValueText.visibility = View.GONE
            true
        }

        binding.selectedValueText.visibility = View.GONE
        binding.vaultScreenWithButton.visibility = View.GONE

        val showBottomNavigation = requireActivity() as ShowBottomNavigation
        showBottomNavigation.showBottomNav()

        binding.recyclerVaultScreen.layoutManager = layoutManager
        binding.recyclerVaultScreen.adapter = adapter

        if(Data.getLongVault() != null){
            if (Data.isInFavouritesList(Data.getLongVault()!!.base)) {
                binding.starIcon.setImageResource(R.drawable.ic_star)
            }else{
                binding.starIcon.setImageResource(R.drawable.ic_star_outline)
            }

            binding.starIcon.setOnClickListener {
                if (Data.isInFavouritesList(Data.getLongVault()!!.base)){
                    binding.starIcon.setImageResource(R.drawable.ic_star_outline)
                    Data.removeFavouriesList(Data.getLongVault()!!.base)
                }
                else{
                    binding.starIcon.setImageResource(R.drawable.ic_star)
                    Data.addFavouritesList(Data.getLongVault()!!.base)
                }
            }
        }

        binding.starIcon.setOnClickListener {
            if (Data.isInFavouritesList(Data.getLongVault()!!.base)){
                binding.starIcon.setImageResource(R.drawable.ic_star_outline)
                Data.removeFavouriesList(Data.getLongVault()!!.base)
            }
            else{
                binding.starIcon.setImageResource(R.drawable.ic_star)
                Data.addFavouritesList(Data.getLongVault()!!.base)
            }
        }

        return binding.root
    }

    override fun openTransactionWindow() {
        TODO("Not yet implemented")
    }

    override fun changeLongVault() {
        TODO("Not yet implemented")
    }

    override fun loadLongClick() {
        TODO("Not yet implemented")
    }

}