package com.example.coursework.data.data_storage

import java.util.*
import com.example.testmvvm.data.model.Vault

data class History(
    val fVault: Vault?,
    val sVault: Vault?,
    val fPrice: String,
    val sPrice: String,
    val date: Date
)
