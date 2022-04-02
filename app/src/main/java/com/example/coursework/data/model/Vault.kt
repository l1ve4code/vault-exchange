package com.example.testmvvm.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.testmvvm.data.model.Rate

@Entity(tableName = "vaults")
data class Vault(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val base: String,
    val date: String,
    val rates: List<Rate>
)