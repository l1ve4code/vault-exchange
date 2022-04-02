package com.example.coursework.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.coursework.R
import com.example.coursework.data.data_storage.Data
import com.example.coursework.ui.change_screen.ChangeScreen
import com.example.coursework.databinding.ActivityMainBinding
import com.example.coursework.ui.filter_screen.FilterScreen
import com.example.coursework.ui.grow_screen.GrowScreen
import com.example.coursework.ui.transaction_screen.TransactionScreen
import com.example.coursework.ui.vault_screen.VaultScreen
import com.example.testmvvm.data.model.Vault
import com.example.testmvvm.data.viewmodel.VaultViewModel
import com.example.testmvvm.data.viewmodel.VaultViewModelFactory

interface VaultWindowManager{
    fun openTransactionWindow()
}
interface FilterWindowManager{
    fun openFilterWindow()
}
interface MainWindowManager{
    fun openMainWindow()
}
interface HideBottomNavigation{
    fun hideButtomNav()
}
interface ShowBottomNavigation{
    fun showBottomNav()
}
class MainActivity : AppCompatActivity(), VaultWindowManager, FilterWindowManager, MainWindowManager, HideBottomNavigation, ShowBottomNavigation {

    private lateinit var binding: ActivityMainBinding
    lateinit var mainViewModel: VaultViewModel

    private val vaultScreen = VaultScreen()
    private val transactionScreen = TransactionScreen()
    private val growScreen = GrowScreen()
    private val changeScreen = ChangeScreen()
    private val filterScreen = FilterScreen()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.bottomNavigation.setOnNavigationItemSelectedListener{
            when (it.itemId) {
                R.id.ic_home -> replaceFragment(vaultScreen)
                R.id.ic_history -> replaceFragment(transactionScreen)
                R.id.ic_analytics -> replaceFragment(growScreen)
            }
            true
        }

        val repository = (application as VaultApplication).vaultRepository

        mainViewModel = ViewModelProvider(this, VaultViewModelFactory(repository)).get(VaultViewModel::class.java)

        mainViewModel.vaults.observe(this, Observer {
            if (it.isEmpty()) Toast.makeText(applicationContext, "No data form api/ local db! [500]", Toast.LENGTH_SHORT).show()
            Data.setVaultList(it as MutableList<Vault>)
        })

    }

    private fun replaceFragment(fragment: Fragment) {
        if (fragment != null) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(binding.fragmentContainer.id, fragment)
            transaction.commit()
        }
    }

    override fun openTransactionWindow() {
        replaceFragment(changeScreen)
    }

    override fun openFilterWindow() {
        replaceFragment(filterScreen)
    }

    override fun openMainWindow() {
        replaceFragment(vaultScreen)
    }

    override fun hideButtomNav() {
       binding.bottomNavigation.visibility = View.GONE
    }

    override fun showBottomNav() {
        binding.bottomNavigation.visibility = View.VISIBLE
    }
}