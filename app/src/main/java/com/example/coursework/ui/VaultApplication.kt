package com.example.coursework.ui

import android.app.Application
import com.example.testmvvm.data.datasource.RemoteDataSource
import com.example.testmvvm.data.dao.VaultService
import com.example.testmvvm.data.datasource.LocalDataSource
import com.example.testmvvm.data.repository.VaultRepository

class VaultApplication : Application() {

    lateinit var vaultRepository: VaultRepository

    override fun onCreate() {
        super.onCreate()
        initialize()
    }

    private fun initialize() {
        val quoteService = RemoteDataSource.getInstance().create(VaultService::class.java)
        val database = LocalDataSource.getDatabase(applicationContext)
        vaultRepository = VaultRepository(quoteService, database, applicationContext)
    }

}