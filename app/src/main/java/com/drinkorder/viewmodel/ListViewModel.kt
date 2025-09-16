package com.drinkorder.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.drinkorder.data.ListItem
import com.drinkorder.repository.ListRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ListViewModel(private val repository: ListRepository) : ViewModel() {
    
    private val _items = MutableStateFlow<List<ListItem>>(emptyList())
    val items: StateFlow<List<ListItem>> = _items.asStateFlow()
    
    private val _newItemText = MutableStateFlow("")
    val newItemText: StateFlow<String> = _newItemText.asStateFlow()
    
    init {
        loadItems()
    }
    
    private fun loadItems() {
        _items.value = repository.getItems()
    }
    
    fun updateNewItemText(text: String) {
        _newItemText.value = text
    }
    
    fun addItem() {
        if (_newItemText.value.isNotBlank()) {
            _items.value = repository.addItem(_newItemText.value.trim())
            _newItemText.value = ""
        }
    }
    
    fun removeItem(itemId: String) {
        _items.value = repository.removeItem(itemId)
    }
    
    fun reorderItems(newOrder: List<ListItem>) {
        _items.value = repository.reorderItems(newOrder)
    }
}

class ListViewModelFactory(private val repository: ListRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ListViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}