package com.drinkorder.repository

import android.content.Context
import android.content.SharedPreferences
import com.drinkorder.data.ListItem
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.UUID

class ListRepository(context: Context) {
    private val sharedPreferences: SharedPreferences = 
        context.getSharedPreferences("list_items", Context.MODE_PRIVATE)
    
    private val json = Json { 
        prettyPrint = true
        isLenient = true
    }
    
    companion object {
        private const val ITEMS_KEY = "items"
    }
    
    fun getItems(): List<ListItem> {
        val itemsJson = sharedPreferences.getString(ITEMS_KEY, "[]")
        return try {
            json.decodeFromString<List<ListItem>>(itemsJson ?: "[]")
        } catch (e: Exception) {
            emptyList()
        }
    }
    
    fun saveItems(items: List<ListItem>) {
        val itemsJson = json.encodeToString(items)
        sharedPreferences.edit()
            .putString(ITEMS_KEY, itemsJson)
            .apply()
    }
    
    fun addItem(text: String): List<ListItem> {
        val currentItems = getItems().toMutableList()
        val newItem = ListItem(
            id = UUID.randomUUID().toString(),
            text = text,
            order = currentItems.size
        )
        currentItems.add(newItem)
        saveItems(currentItems)
        return currentItems
    }
    
    fun removeItem(itemId: String): List<ListItem> {
        val currentItems = getItems().toMutableList()
        currentItems.removeAll { it.id == itemId }
        // Update order indices
        val reorderedItems = currentItems.mapIndexed { index, item ->
            item.copy(order = index)
        }
        saveItems(reorderedItems)
        return reorderedItems
    }
    
    fun reorderItems(items: List<ListItem>): List<ListItem> {
        val reorderedItems = items.mapIndexed { index, item ->
            item.copy(order = index)
        }
        saveItems(reorderedItems)
        return reorderedItems
    }
}