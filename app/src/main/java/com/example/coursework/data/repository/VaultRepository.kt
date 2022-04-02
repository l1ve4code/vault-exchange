package com.example.testmvvm.data.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.testmvvm.data.dao.VaultService
import com.example.testmvvm.data.model.Vault
import com.example.testmvvm.data.datasource.LocalDataSource
import com.example.testmvvm.utils.NetworkUtils

class VaultRepository(
    private val vaultService: VaultService,
    private val localDataSource: LocalDataSource,
    private val applicationContext: Context
) {

    private val vaultsLiveData = MutableLiveData<List<Vault>>()

    val vaults: LiveData<List<Vault>>
        get() = vaultsLiveData

    suspend fun getVaults() {

        if(NetworkUtils.isInternetAvailable(applicationContext)){
            val result = vaultService.getLatestVault()
            if(!result.isNullOrEmpty()){

                localDataSource.vaultDAO().insertVaults(result)
                vaultsLiveData.postValue(result)

            }
        }
        else{
            val vaults = localDataSource.vaultDAO().getVaults()
            vaultsLiveData.postValue(vaults)
//            localDataSource.vaultDAO().nukeTable()
        }

    }

}