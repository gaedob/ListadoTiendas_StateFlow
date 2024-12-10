package com.desafiolatam.listadodetiendas.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.desafiolatam.listadodetiendas.data.StoreRepository
import com.desafiolatam.listadodetiendas.model.Store
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class MainViewModel : ViewModel() {

    private val repository : StoreRepository = StoreRepository()

    suspend fun getStoreList(): StateFlow<List<Store>> = repository.getStoreList().stateIn(viewModelScope)

    suspend fun getStoreById(id: Int) : StateFlow<Store?> = repository.getStoreById(id).stateIn(viewModelScope)

}