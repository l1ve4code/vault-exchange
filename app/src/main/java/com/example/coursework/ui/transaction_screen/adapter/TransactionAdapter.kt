package com.example.coursework.ui.transaction_screen.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.coursework.data.data_storage.Data
import com.example.coursework.data.data_storage.History
import com.example.coursework.databinding.HistoryItemBinding

class TransactionAdapter: RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {

    private val testList: MutableList<History> = Data.getHistoryList() as MutableList<History>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = HistoryItemBinding.inflate(inflater, parent, false)
        return TransactionAdapter.TransactionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val item = testList[position]

        with(holder.binding){
            vaultName.text = item.fVault?.base
            secVaultName.text = item.sVault?.base

            vaultSumm.text = item.fPrice
            secVaultSumm.text = item.sPrice
        }
    }

    override fun getItemCount(): Int {
        return testList.size
    }

    class TransactionViewHolder (var binding: HistoryItemBinding): RecyclerView.ViewHolder(binding.root)
}