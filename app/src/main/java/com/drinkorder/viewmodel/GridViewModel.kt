package com.drinkorder.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.drinkorder.data.ListItem
import com.drinkorder.repository.ListRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map

class GridViewModel(private val repository: ListRepository) : ViewModel() {
    
    private val _items = MutableStateFlow<List<ListItem>>(emptyList())
    val items: StateFlow<List<ListItem>> = _items.asStateFlow()
    
    private val _itemCounts = MutableStateFlow<Map<String, Int>>(emptyMap())
    val itemCounts: StateFlow<Map<String, Int>> = _itemCounts.asStateFlow()
    
    private val _totalCount = MutableStateFlow(0)
    val totalCount: StateFlow<Int> = _totalCount.asStateFlow()
    
    private val _orderSummary = MutableStateFlow("")
    val orderSummary: StateFlow<String> = _orderSummary.asStateFlow()
    
    init {
        loadItems()
    }
    
    private fun loadItems() {
        _items.value = repository.getItems()
    }
    
    fun incrementItem(itemId: String) {
        val currentCounts = _itemCounts.value.toMutableMap()
        currentCounts[itemId] = (currentCounts[itemId] ?: 0) + 1
        _itemCounts.value = currentCounts
        updateTotalCount()
    }
    
    fun decrementItem(itemId: String) {
        val currentCounts = _itemCounts.value.toMutableMap()
        val currentCount = currentCounts[itemId] ?: 0
        if (currentCount > 0) {
            currentCounts[itemId] = currentCount - 1
            _itemCounts.value = currentCounts
            updateTotalCount()
        }
    }
    
    private fun updateTotalCount() {
        _totalCount.value = _itemCounts.value.values.sum()
        updateOrderSummary()
    }
    
    private fun updateOrderSummary() {
        val itemsWithCounts = _itemCounts.value.filter { it.value > 0 }
        val itemsMap = _items.value.associateBy { it.id }
        
        val summary = itemsWithCounts.map { (itemId, count) ->
            val itemName = itemsMap[itemId]?.text ?: "Unknown"
            "• $itemName: $count"
        }.joinToString("\n")
        
        _orderSummary.value = summary
    }
    
    fun resetCounts() {
        _itemCounts.value = emptyMap()
        updateTotalCount()
    }
    
    fun getItemCount(itemId: String): Int {
        return _itemCounts.value[itemId] ?: 0
    }
}

class GridViewModelFactory(private val repository: ListRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GridViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return GridViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}