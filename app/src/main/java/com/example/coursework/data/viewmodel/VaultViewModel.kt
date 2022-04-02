package com.example.testmvvm.data.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testmvvm.data.model.Vault
import com.example.testmvvm.data.repository.VaultRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class VaultViewModel(private val repository: VaultRepository) : ViewModel() {

    init {
        viewModelScope.launch(Dispatchers.IO){
            repository.getVaults()
        }
    }

    val vaults : LiveData<List<Vault>>
        get() = repository.vaults
}