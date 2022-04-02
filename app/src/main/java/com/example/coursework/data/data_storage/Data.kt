package com.example.coursework.data.data_storage

import java.text.SimpleDateFormat
import java.util.*
import com.example.testmvvm.data.model.Vault

object Data {

    private var vaultList: MutableList<Vault> = mutableListOf<Vault>()

    private var favouritesList: MutableList<String> = mutableListOf<String>()
    private var historyList: MutableList<History> = mutableListOf<History>()
    private var graphList: MutableList<Vault> = mutableListOf<Vault>()
    private var setVaultList: MutableList<Vault> = mutableListOf<Vault>()

    private var vault: Vault? = null
    private var vaultLong: Vault? = null
    private var filter: String? = "all"

    private var startDate: String? = null
    private var endDate: String? = null

    fun setVaultList(list: MutableList<Vault>){
        vaultList = list
    }

    fun getStartDate(): String? {
        return startDate
    }

    fun getEndDate(): String? {
        return endDate
    }

    fun setStartDate(date: String){
        startDate = date
    }

    fun setEndDate(date: String){
        endDate = date
    }

    fun clearDates(){
        startDate = null
        endDate = null
    }

    fun getFilter(): String? {
        return filter
    }

    fun setFilter(name: String){
        filter = name
    }

    fun getFavouritesList(): MutableList<String>{
        return favouritesList
    }

    fun addFavouritesList(base: String){
        favouritesList.add(base)
    }

    fun isInFavouritesList(base: String): Boolean {
        if (favouritesList.contains(base)) return true
        return false
    }

    fun removeFavouriesList(base: String){
        favouritesList.remove(base)
    }

    fun getVaultList(): MutableList<Vault>{
        for(i in vaultList){
            if (setVaultList.find { it.base == i.base } == null) setVaultList.add(i)
        }
        return setVaultList
    }

    fun getVaultByName(name: String): Vault? {
        return vaultList.find { it.base == name }
    }

    fun deleteFromVault(item: Vault){
        setVaultList.remove(item)
    }

    fun addFirstVault(item: Vault){
        setVaultList.add(0, item)
    }

    fun pushToStartVault(item: Vault){
        setVaultList.remove(item)
        setVaultList.add(0, item)
    }

    fun pushLongVault(item: Vault){
        vaultLong?.let { setVaultList.add(it) }
        vaultLong = item
    }

    fun removeLongVault(){
        vaultLong?.let { setVaultList.add(it) }
        vaultLong = null
    }

    fun removeLongVault(item: Vault){
        vaultLong = null
    }

    fun getHistoryList(): List<History> {
        if(getFilter() == "month"){
            return historyList.filter { s -> s.date.month == Date().month }
        }
        else if(getFilter() == "week"){

            val history = mutableListOf<History>()

            for (i in historyList) {

                val calendar = Calendar.getInstance()
                calendar.time = Date()
                calendar.add(Calendar.DAY_OF_YEAR, -7)
                val newDate = calendar.time

                val sec_cal = Calendar.getInstance()
                sec_cal.time = i.date
                val secDate = sec_cal.time

                val thr_cal = Calendar.getInstance()
                thr_cal.time = i.date
                val thrDate = sec_cal.time

                if (secDate >= newDate && secDate <= thrDate) history.add(i)

            }

            return history
        }
        else if(getFilter() == "select"){
            val history = mutableListOf<History>()

            for (i in historyList) {

                val startDateSimple = SimpleDateFormat("dd/MM/yyyy")
                val startDateDate = startDateSimple.parse(getStartDate())

                val endDateSimple = SimpleDateFormat("dd/MM/yyyy")
                val endDateDate = endDateSimple.parse(getEndDate())


                if (i.date.after(startDateDate) && endDateDate.after(i.date)) {
                    history.add(i)
                }
            }
            return history
        }
        return historyList
    }

    fun pushToHistoryList(item: History){
        historyList.add(item)
    }

    fun getGraphList(name: String): MutableList<Vault>{
        val graphAssignList = mutableListOf<Vault>()
        for(i in vaultList){
            if (i.base == name) graphAssignList.add(i)
        }
        graphList = graphAssignList
        return graphAssignList
    }

    fun setVault(item: Vault){
        vault = item
    }

    fun getVault(): Vault? {
        return vault
    }

    fun setLongVault(item: Vault?){
        vaultLong = item
    }

    fun getLongVault(): Vault? {
        return vaultLong
    }


}