package com.kotlin.recyclerview.practice.screens.fragments.task2

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kotlin.recyclerview.practice.data.UuidItem

class UuidItemsViewModel(val dataSource: DataSource) : ViewModel() {

    val uuidLiveData = dataSource.getUuidList()

    fun insertUuid() = dataSource.addUuid()

    fun deleteUuid(uuidItem: UuidItem) = dataSource.removeUuid(uuidItem)

    fun refreshUuid(uuidItem: UuidItem) = dataSource.refreshUuid(uuidItem)

    fun refreshAll() = dataSource.createNewSource()
}

class UuidsListViewModelFactory() : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UuidItemsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UuidItemsViewModel(
                dataSource = DataSource.getDataSource()
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}