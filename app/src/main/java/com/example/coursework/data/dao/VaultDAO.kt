package com.example.coursework.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.testmvvm.data.model.Vault

@Dao
interface VaultDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVaults(vaults: List<Vault>)

    @Query("SELECT * FROM vaults")
    suspend fun getVaults() : List<Vault>

    @Query("DELETE FROM vaults")
    fun nukeTable()
}