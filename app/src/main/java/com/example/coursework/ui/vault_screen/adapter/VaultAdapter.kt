package com.example.coursework.ui.vault_screen.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.coursework.R
import com.example.coursework.data.data_storage.Data
import com.example.coursework.databinding.VaultItemBinding
import com.example.coursework.ui.vault_screen.vaultWindowManager
import com.example.testmvvm.data.model.Vault

class VaultAdapter(private val vaultWindowManager: vaultWindowManager) : RecyclerView.Adapter<VaultAdapter.VaultViewHolder>() {

    private var vaultList: MutableList<Vault> = Data.getVaultList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VaultViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = VaultItemBinding.inflate(inflater, parent, false)

        return VaultViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VaultViewHolder, position: Int) {

        val item = vaultList[position]

        // LONG-SELECT VAULT
        holder.itemView.setOnLongClickListener {
            if(Data.getLongVault() != null){
                Data.pushLongVault(item)
            }
            else{
                Data.setLongVault(item)
            }
            Data.deleteFromVault(item)
            vaultWindowManager.loadLongClick()
            vaultWindowManager.changeLongVault()
            notifyDataSetChanged()
            true
        }

        // SHORT-SELECT VAULT
        holder.itemView.setOnClickListener {
            if(Data.getLongVault() != item){
                Data.setVault(item)
                vaultWindowManager.openTransactionWindow()
            }
        }

        with(holder.binding){

            // IS IN FAVOURITES
            if (Data.isInFavouritesList(item.base)) {
                starIcon.setImageResource(R.drawable.ic_star)
            }else{
                starIcon.setImageResource(R.drawable.ic_star_outline)
            }

            // END SECTION

            vaultName.text = item.base

            // ADD TO FAVOURITES
            starIcon.setOnClickListener {
                if (Data.isInFavouritesList(item.base)){
                    starIcon.setImageResource(R.drawable.ic_star_outline)
                    Data.removeFavouriesList(item.base)
                }
                else{
                    starIcon.setImageResource(R.drawable.ic_star)
                    Data.addFavouritesList(item.base)

                    Data.pushToStartVault(item)
                    notifyDataSetChanged()
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return vaultList.size
    }

    class VaultViewHolder (var binding: VaultItemBinding): RecyclerView.ViewHolder(binding.root)

}