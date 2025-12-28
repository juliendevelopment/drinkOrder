package com.drinkorder.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

data class DrinkIconItem(
    val id: String,
    val icon: ImageVector,
    val displayName: String,
    val searchKeywords: List<String>,
    val category: String
)

object DrinkIconDatabase {
    val icons = listOf(
        // Hot Drinks
        DrinkIconItem(
            id = "local_cafe",
            icon = Icons.Default.LocalCafe,
            displayName = "Coffee",
            searchKeywords = listOf("coffee", "cafe", "espresso", "latte", "cappuccino", "hot", "caffeine", "americano", "mocha"),
            category = "Hot Drinks"
        ),
        DrinkIconItem(
            id = "emoji_food_beverage",
            icon = Icons.Default.LocalCafe,
            displayName = "Tea",
            searchKeywords = listOf("tea", "hot", "green", "black", "herbal", "chai", "matcha", "oolong"),
            category = "Hot Drinks"
        ),
        
        // Cold Drinks
        DrinkIconItem(
            id = "local_drink",
            icon = Icons.Default.LocalCafe,
            displayName = "Cold Drink",
            searchKeywords = listOf("drink", "cold", "beverage", "glass", "juice", "soda", "refreshing"),
            category = "Cold Drinks"
        ),
        DrinkIconItem(
            id = "water_drop",
            icon = Icons.Default.WaterDrop,
            displayName = "Water",
            searchKeywords = listOf("water", "h2o", "hydration", "clear", "pure", "still", "sparkling"),
            category = "Cold Drinks"
        ),
        DrinkIconItem(
            id = "icecream",
            icon = Icons.Default.Icecream,
            displayName = "Milkshake",
            searchKeywords = listOf("milkshake", "shake", "ice cream", "cold", "creamy", "vanilla", "chocolate", "strawberry"),
            category = "Cold Drinks"
        ),
        
        // Alcoholic Drinks
        DrinkIconItem(
            id = "local_bar",
            icon = Icons.Default.LocalBar,
            displayName = "Cocktail",
            searchKeywords = listOf("cocktail", "bar", "mixed", "alcohol", "martini", "drink", "spirit", "rum", "vodka"),
            category = "Alcohol"
        ),
        DrinkIconItem(
            id = "wine_bar",
            icon = Icons.Default.LocalBar,
            displayName = "Wine",
            searchKeywords = listOf("wine", "glass", "red", "white", "alcohol", "grape", "vintage", "bottle"),
            category = "Alcohol"
        ),
        DrinkIconItem(
            id = "sports_bar",
            icon = Icons.Default.LocalBar,
            displayName = "Beer",
            searchKeywords = listOf("beer", "ale", "lager", "alcohol", "bottle", "draft", "pint", "brew"),
            category = "Alcohol"
        ),
        
        // Fruit Juices
        DrinkIconItem(
            id = "eco",
            icon = Icons.Default.Eco,
            displayName = "Green Juice",
            searchKeywords = listOf("green", "juice", "healthy", "vegetable", "spinach", "kale", "celery", "organic"),
            category = "Juices"
        ),
        DrinkIconItem(
            id = "local_florist",
            icon = Icons.Default.LocalFlorist,
            displayName = "Fruit Juice",
            searchKeywords = listOf("fruit", "juice", "orange", "apple", "grape", "fresh", "vitamin", "citrus"),
            category = "Juices"
        ),
        
        // Energy & Sports Drinks
        DrinkIconItem(
            id = "fitness_center",
            icon = Icons.Default.FitnessCenter,
            displayName = "Energy Drink",
            searchKeywords = listOf("energy", "sports", "fitness", "caffeine", "boost", "power", "workout"),
            category = "Energy Drinks"
        ),
        DrinkIconItem(
            id = "directions_run",
            icon = Icons.Default.DirectionsRun,
            displayName = "Sports Drink",
            searchKeywords = listOf("sports", "electrolyte", "hydration", "athletic", "recovery", "isotonic"),
            category = "Energy Drinks"
        ),
        
        // Soft Drinks
        DrinkIconItem(
            id = "bubble_chart",
            icon = Icons.Default.BubbleChart,
            displayName = "Soda",
            searchKeywords = listOf("soda", "cola", "pepsi", "coke", "fizzy", "carbonated", "bubble", "soft drink"),
            category = "Soft Drinks"
        ),
        
        // Specialty Drinks
        DrinkIconItem(
            id = "cake",
            icon = Icons.Default.Cake,
            displayName = "Dessert Drink",
            searchKeywords = listOf("dessert", "sweet", "chocolate", "caramel", "vanilla", "milkshake", "frappe"),
            category = "Specialty"
        ),
        DrinkIconItem(
            id = "self_improvement",
            icon = Icons.Default.Favorite,
            displayName = "Wellness Drink",
            searchKeywords = listOf("wellness", "health", "detox", "kombucha", "probiotic", "herbal", "medicinal"),
            category = "Specialty"
        ),
        DrinkIconItem(
            id = "outdoor_grill",
            icon = Icons.Default.LocalCafe,
            displayName = "BBQ Drink",
            searchKeywords = listOf("bbq", "grill", "summer", "outdoor", "lemonade", "iced tea", "refreshing"),
            category = "Specialty"
        ),
        
        // Breakfast Drinks
        DrinkIconItem(
            id = "free_breakfast",
            icon = Icons.Default.FreeBreakfast,
            displayName = "Breakfast Drink",
            searchKeywords = listOf("breakfast", "morning", "orange juice", "milk", "smoothie", "fresh"),
            category = "Breakfast"
        ),
        
        // Hot Chocolate & Warm Drinks
        DrinkIconItem(
            id = "local_fire_department",
            icon = Icons.Default.Whatshot,
            displayName = "Hot Chocolate",
            searchKeywords = listOf("hot chocolate", "cocoa", "warm", "winter", "marshmallow", "chocolate"),
            category = "Hot Drinks"
        ),
        
        // Smoothies & Shakes
        DrinkIconItem(
            id = "blender",
            icon = Icons.Default.Kitchen,
            displayName = "Smoothie",
            searchKeywords = listOf("smoothie", "blend", "fruit", "healthy", "banana", "berry", "protein"),
            category = "Smoothies"
        ),
        
        // Default/Generic
        DrinkIconItem(
            id = "restaurant",
            icon = Icons.Default.LocalCafe,
            displayName = "Generic Beverage",
            searchKeywords = listOf("beverage", "drink", "restaurant", "dining", "liquid", "refreshment"),
            category = "General"
        )
    )
    
    fun searchIcons(query: String): List<DrinkIconItem> {
        if (query.isBlank()) return icons
        
        val queryLower = query.lowercase()
        return icons.filter { icon ->
            icon.displayName.lowercase().contains(queryLower) ||
            icon.searchKeywords.any { keyword -> 
                keyword.lowercase().contains(queryLower) 
            } ||
            icon.category.lowercase().contains(queryLower)
        }.sortedBy { icon ->
            // Prioritize exact matches in display name, then keywords, then category
            when {
                icon.displayName.lowercase() == queryLower -> 0
                icon.displayName.lowercase().startsWith(queryLower) -> 1
                icon.searchKeywords.any { it.lowercase() == queryLower } -> 2
                icon.searchKeywords.any { it.lowercase().startsWith(queryLower) } -> 3
                else -> 4
            }
        }
    }
    
    fun getIconsByCategory(): Map<String, List<DrinkIconItem>> {
        return icons.groupBy { it.category }.toSortedMap()
    }
    
    fun getIconById(iconId: String): DrinkIconItem? {
        return icons.find { it.id == iconId }
    }
}