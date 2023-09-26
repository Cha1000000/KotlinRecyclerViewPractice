package com.kotlin.recyclerview.practice.screens.fragments.task2

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kotlin.recyclerview.practice.data.UuidItem
import com.kotlin.recyclerview.practice.data.createUuidsList
import java.util.UUID

class DataSource {

    private val initialUuidList = createUuidsList()
    private val uuidsLiveData = MutableLiveData(initialUuidList)

    /* Adds uuidItem to liveData and posts value. */
    fun addUuid() {
        val uuidItem = UuidItem(UUID.randomUUID().toString())
        val currentList = uuidsLiveData.value
        if (currentList == null) {
            uuidsLiveData.postValue(listOf(uuidItem))
        } else {
            val updatedList = currentList.toMutableList()
            updatedList.add(currentList.size, uuidItem)
            uuidsLiveData.postValue(updatedList)
        }
    }

    /* Removes uuidItem from liveData and posts value. */
    fun removeUuid(uuidItem: UuidItem) {
        val currentList = uuidsLiveData.value
        if (currentList != null) {
            val updatedList = currentList.toMutableList()
            updatedList.remove(uuidItem)
            uuidsLiveData.postValue(updatedList)
        }
    }

    fun refreshUuid(uuidItem: UuidItem) {
        val newList = uuidsLiveData.value!!.map {
            if (it.uuid == uuidItem.uuid) {
                UuidItem(UUID.randomUUID().toString())
            } else {
                it
            }
        }
        uuidsLiveData.postValue(newList)
    }

    fun getUuidList() : LiveData<List<UuidItem>> = uuidsLiveData

    companion object {
        private var INSTANCE: DataSource? = null

        fun getDataSource(): DataSource {
            return synchronized(DataSource::class) {
                val newInstance = INSTANCE ?: DataSource()
                INSTANCE = newInstance
                newInstance
            }
        }
    }
}