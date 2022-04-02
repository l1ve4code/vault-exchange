package com.example.testmvvm.data.dao

import com.example.testmvvm.data.model.Vault
import retrofit2.http.GET

interface VaultService {

    @GET("/index.php")
    suspend fun getLatestVault(): List<Vault>
}