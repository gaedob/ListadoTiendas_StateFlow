package com.desafiolatam.listadodetiendas.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.desafiolatam.listadodetiendas.adapter.StoreAdapter
import com.desafiolatam.listadodetiendas.databinding.ActivityMainBinding
import com.desafiolatam.listadodetiendas.model.Store
import com.desafiolatam.listadodetiendas.viewmodel.MainViewModel
import kotlinx.coroutines.flow.collectLatest

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    private lateinit var adapter: StoreAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launchWhenCreated {
            viewModel.getStoreList().collect {
                initRecyclerView(it)
            }
        }

    }

    private fun initRecyclerView(list: List<Store>){
        adapter = StoreAdapter(list)
        binding.recyclerViewStores.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        binding.recyclerViewStores.adapter = adapter
        onItemSelected()
    }

    private fun onItemSelected(){
        adapter.onClick = {
            id ->
            lifecycleScope.launchWhenCreated {
                viewModel.getStoreById(id).collectLatest {
                    store -> store?.let {
                        startActivity(Intent(applicationContext, StoreDetailActivity::class.java).apply {
                            putExtra("BUNDLE", Bundle().apply {
                                putParcelable("STORE",store)
                            })
                        })
                    }
                }
            }
        }

    }

}