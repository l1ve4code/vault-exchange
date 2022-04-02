package com.example.coursework.ui.change_screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import com.example.coursework.ui.HideBottomNavigation
import com.example.coursework.ui.MainWindowManager
import com.example.coursework.R
import com.example.coursework.data.data_storage.Data
import com.example.coursework.data.data_storage.History
import com.example.coursework.databinding.FragmentChangeScreenBinding
import com.example.testmvvm.data.model.Vault
import java.lang.Integer.parseInt
import java.util.*

class ChangeScreen : Fragment() {

    private lateinit var binding: FragmentChangeScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChangeScreenBinding.inflate(inflater, container, false)
        if(Data.getLongVault() == null) {
            // Vault logic - SINGLE CLICK
            var vault: Vault? = Data.getVaultList().getOrNull(0)

            if (vault?.base == Data.getVault()?.base) {
                vault = Data.getVaultList().getOrNull(1)
            }

            if (vault?.base?.let { Data.isInFavouritesList(it) } == true) {
                binding.secondStarIcon.setImageResource(R.drawable.ic_star)
            }else{
                binding.secondStarIcon.setImageResource(R.drawable.ic_star_outline)
            }
            if (Data.getVault()?.let { Data.isInFavouritesList(it.base) } == true) {
                binding.firstStarIcon.setImageResource(R.drawable.ic_star)
            }else{
                binding.firstStarIcon.setImageResource(R.drawable.ic_star_outline)
            }

            binding.firstVaultName.text = Data.getVault()?.base
            binding.secondVaultName.text = vault?.base

            binding.secondVaultExchange.text = Data.getVault()?.rates?.find { it.name == vault?.base}?.cost.toString()
            binding.firstEditText.setText("1")

            binding.firstEditText.addTextChangedListener {
                if(binding.firstEditText.text.toString().toIntOrNull() != null)
                binding.secondVaultExchange.text = (Data.getVault()?.rates?.find { it.name == vault?.base}?.cost?.times(
                    parseInt(binding.firstEditText.text.toString())
                )).toString()
            }

            binding.exchangeButton.setOnClickListener {
                Data.getVault()?.let { it1 -> History(it1, vault, binding.firstEditText.text.toString(), binding.secondVaultExchange.text.toString(), Date()) }
                    ?.let { it2 -> Data.pushToHistoryList(it2) }
                val mainWindowManager = requireActivity() as MainWindowManager
                mainWindowManager.openMainWindow()
            }
        }
        else{
            // Vault logic - LONG CLICK
            var vault: Vault? = Data.getLongVault()

            if (vault?.let { Data.isInFavouritesList(it.base) } == true) {
                binding.firstStarIcon.setImageResource(R.drawable.ic_star)
            }else{
                binding.firstStarIcon.setImageResource(R.drawable.ic_star_outline)
            }
            if (Data.getVault()?.let { Data.isInFavouritesList(it.base) } == true) {
                binding.secondStarIcon.setImageResource(R.drawable.ic_star)
            }else{
                binding.secondStarIcon.setImageResource(R.drawable.ic_star_outline)
            }

            binding.firstVaultName.text = vault?.base
            binding.secondVaultName.text = Data.getVault()?.base

            binding.secondVaultExchange.text = vault?.rates?.find{ it.name == Data.getVault()?.base}?.cost.toString()
            binding.firstEditText.setText("1")

            binding.firstEditText.addTextChangedListener {
                if(binding.firstEditText.text.toString().toIntOrNull() != null)
                    binding.secondVaultExchange.text = (vault?.rates?.find{ it.name == Data.getVault()?.base}?.cost?.times(
                        parseInt(binding.firstEditText.text.toString())
                    )).toString()
            }

            binding.exchangeButton.setOnClickListener {
                vault?.let { it1 -> Data.getVault()?.let { it2 ->
                    History(it1,
                        it2, binding.firstEditText.text.toString(), binding.secondVaultExchange.text.toString(), Date())
                } }
                    ?.let { it2 -> Data.pushToHistoryList(it2) }
                val mainWindowManager = requireActivity() as MainWindowManager
                mainWindowManager.openMainWindow()
            }
        }

        val hideBottomNavigation = requireActivity() as HideBottomNavigation
        hideBottomNavigation.hideButtomNav()

        binding.backButton.setOnClickListener {
            val mainWindowManager = requireActivity() as MainWindowManager
            mainWindowManager.openMainWindow()
         }

        Data.setLongVault(null)

        return binding.root
    }

}