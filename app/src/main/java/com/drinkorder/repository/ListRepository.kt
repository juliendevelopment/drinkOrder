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
        private const val COUNTS_KEY = "item_counts"
    }
    
    fun getItems(): List<ListItem> {
        val itemsJson = sharedPreferences.getString(ITEMS_KEY, "[]")
        return try {
            val items = json.decodeFromString<List<ListItem>>(itemsJson ?: "[]")
            // Migration: Add default iconId and colorId to items that don't have them
            val migratedItems = items.map { item ->
                val needsIconMigration = item.iconId.isEmpty()
                val needsColorMigration = try {
                    // Check if colorId field exists by attempting to access it
                    item.colorId.isEmpty()
                } catch (e: Exception) {
                    // colorId field doesn't exist yet, needs migration
                    true
                }
                
                if (needsIconMigration || needsColorMigration) {
                    item.copy(
                        iconId = if (needsIconMigration) "local_drink" else item.iconId,
                        colorId = if (needsColorMigration) "blue" else item.colorId
                    )
                } else {
                    item
                }
            }
            // Save migrated items if any changes were made
            if (migratedItems != items) {
                saveItems(migratedItems)
            }
            migratedItems
        } catch (e: Exception) {
            // Handle legacy format or corrupted data
            emptyList()
        }
    }
    
    fun saveItems(items: List<ListItem>) {
        val itemsJson = json.encodeToString(items)
        sharedPreferences.edit()
            .putString(ITEMS_KEY, itemsJson)
            .apply()
    }
    
    fun addItem(text: String, iconId: String = "local_drink", colorId: String = "blue"): List<ListItem> {
        val currentItems = getItems().toMutableList()
        val newItem = ListItem(
            id = UUID.randomUUID().toString(),
            text = text,
            order = currentItems.size,
            iconId = iconId,
            colorId = colorId
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
    
    fun updateItem(itemId: String, newText: String? = null, newIconId: String? = null, newColorId: String? = null): List<ListItem> {
        val currentItems = getItems().toMutableList()
        val itemIndex = currentItems.indexOfFirst { it.id == itemId }
        
        if (itemIndex != -1) {
            val currentItem = currentItems[itemIndex]
            val updatedItem = currentItem.copy(
                text = newText ?: currentItem.text,
                iconId = newIconId ?: currentItem.iconId,
                colorId = newColorId ?: currentItem.colorId
            )
            currentItems[itemIndex] = updatedItem
            saveItems(currentItems)
        }
        
        return currentItems
    }
    
    fun getItemCounts(): Map<String, Int> {
        val countsJson = sharedPreferences.getString(COUNTS_KEY, "{}")
        return try {
            json.decodeFromString<Map<String, Int>>(countsJson ?: "{}")
        } catch (e: Exception) {
            emptyMap()
        }
    }
    
    fun saveItemCounts(counts: Map<String, Int>) {
        val countsJson = json.encodeToString(counts)
        sharedPreferences.edit()
            .putString(COUNTS_KEY, countsJson)
            .apply()
    }
}