package com.example.testmvvm.data.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.testmvvm.data.repository.VaultRepository

class VaultViewModelFactory(private val repository: VaultRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return VaultViewModel(repository) as T
    }

}