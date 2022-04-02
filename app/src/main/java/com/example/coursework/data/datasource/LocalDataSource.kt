package com.example.testmvvm.data.datasource

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.testmvvm.data.converter.RateConverter
import com.example.coursework.data.dao.VaultDAO
import com.example.testmvvm.data.model.Vault

@Database(entities = [Vault::class], version = 2)
@TypeConverters(RateConverter::class)
abstract class LocalDataSource : RoomDatabase() {

    abstract fun vaultDAO() : VaultDAO

    companion object{
        @Volatile
        private var INSTANCE: LocalDataSource? = null

        fun getDatabase(context: Context): LocalDataSource {
            if (INSTANCE == null) {
                synchronized(this){
                    INSTANCE = Room.databaseBuilder(context,
                        LocalDataSource::class.java,
                        "vaultDB")
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE!!
        }
    }
}