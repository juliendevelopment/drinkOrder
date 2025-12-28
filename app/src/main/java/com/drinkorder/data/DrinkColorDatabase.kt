package com.drinkorder.data

import androidx.compose.ui.graphics.Color

data class DrinkColorItem(
    val id: String,
    val color: Color,
    val displayName: String,
    val searchKeywords: List<String>,
    val category: String
)

object DrinkColorDatabase {
    val colors = listOf(
        // Primary Colors
        DrinkColorItem(
            id = "blue",
            color = Color(0xFF2196F3),
            displayName = "Blue",
            searchKeywords = listOf("blue", "water", "ocean", "sky", "cool", "fresh", "hydrating"),
            category = "Primary"
        ),
        DrinkColorItem(
            id = "red",
            color = Color(0xFFF44336),
            displayName = "Red",
            searchKeywords = listOf("red", "strawberry", "cherry", "cranberry", "wine", "berry", "fruit punch"),
            category = "Primary"
        ),
        DrinkColorItem(
            id = "green",
            color = Color(0xFF4CAF50),
            displayName = "Green",
            searchKeywords = listOf("green", "lime", "mint", "tea", "matcha", "healthy", "vegetable", "spinach"),
            category = "Primary"
        ),
        DrinkColorItem(
            id = "yellow",
            color = Color(0xFFFFEB3B),
            displayName = "Yellow",
            searchKeywords = listOf("yellow", "lemon", "banana", "pineapple", "citrus", "sunny", "bright"),
            category = "Primary"
        ),
        
        // Warm Colors
        DrinkColorItem(
            id = "orange",
            color = Color(0xFFFF9800),
            displayName = "Orange",
            searchKeywords = listOf("orange", "tangerine", "peach", "mango", "carrot", "pumpkin", "sunset"),
            category = "Warm"
        ),
        DrinkColorItem(
            id = "coral",
            color = Color(0xFFFF7043),
            displayName = "Coral",
            searchKeywords = listOf("coral", "salmon", "pink", "tropical", "sunset", "warm", "peachy"),
            category = "Warm"
        ),
        DrinkColorItem(
            id = "amber",
            color = Color(0xFFFFC107),
            displayName = "Amber",
            searchKeywords = listOf("amber", "honey", "beer", "whiskey", "golden", "caramel", "warm"),
            category = "Warm"
        ),
        
        // Cool Colors
        DrinkColorItem(
            id = "teal",
            color = Color(0xFF009688),
            displayName = "Teal",
            searchKeywords = listOf("teal", "aqua", "mint", "fresh", "ocean", "cool", "refreshing"),
            category = "Cool"
        ),
        DrinkColorItem(
            id = "cyan",
            color = Color(0xFF00BCD4),
            displayName = "Cyan",
            searchKeywords = listOf("cyan", "light blue", "ice", "cold", "refreshing", "arctic", "glacier"),
            category = "Cool"
        ),
        DrinkColorItem(
            id = "indigo",
            color = Color(0xFF3F51B5),
            displayName = "Indigo",
            searchKeywords = listOf("indigo", "deep blue", "navy", "night", "blueberry", "grape"),
            category = "Cool"
        ),
        
        // Purple Spectrum
        DrinkColorItem(
            id = "purple",
            color = Color(0xFF9C27B0),
            displayName = "Purple",
            searchKeywords = listOf("purple", "grape", "blackberry", "lavender", "plum", "berry"),
            category = "Purple"
        ),
        DrinkColorItem(
            id = "deep_purple",
            color = Color(0xFF673AB7),
            displayName = "Deep Purple",
            searchKeywords = listOf("deep purple", "royal", "eggplant", "dark grape", "blackcurrant"),
            category = "Purple"
        ),
        DrinkColorItem(
            id = "pink",
            color = Color(0xFFE91E63),
            displayName = "Pink",
            searchKeywords = listOf("pink", "rose", "strawberry", "watermelon", "cherry blossom", "bubblegum"),
            category = "Purple"
        ),
        
        // Neutral & Earth Tones
        DrinkColorItem(
            id = "brown",
            color = Color(0xFF795548),
            displayName = "Brown",
            searchKeywords = listOf("brown", "coffee", "chocolate", "cocoa", "mocha", "espresso", "cola"),
            category = "Earth"
        ),
        DrinkColorItem(
            id = "grey",
            color = Color(0xFF9E9E9E),
            displayName = "Grey",
            searchKeywords = listOf("grey", "gray", "neutral", "classic", "simple", "elegant", "balanced"),
            category = "Neutral"
        ),
        DrinkColorItem(
            id = "black",
            color = Color(0xFF424242),
            displayName = "Black",
            searchKeywords = listOf("black", "dark", "coffee", "cola", "elegant", "strong", "bold"),
            category = "Neutral"
        ),
        
        // Light & Pastel
        DrinkColorItem(
            id = "light_blue",
            color = Color(0xFF81D4FA),
            displayName = "Light Blue",
            searchKeywords = listOf("light blue", "pastel", "sky", "baby blue", "soft", "gentle", "calm"),
            category = "Pastel"
        ),
        DrinkColorItem(
            id = "light_green",
            color = Color(0xFF81C784),
            displayName = "Light Green",
            searchKeywords = listOf("light green", "pastel", "mint", "lime", "cucumber", "fresh", "spring"),
            category = "Pastel"
        ),
        DrinkColorItem(
            id = "light_pink",
            color = Color(0xFFF8BBD9),
            displayName = "Light Pink",
            searchKeywords = listOf("light pink", "pastel", "rose", "blush", "soft", "gentle", "romantic"),
            category = "Pastel"
        ),
        
        // Vibrant & Electric
        DrinkColorItem(
            id = "lime",
            color = Color(0xFFCDDC39),
            displayName = "Lime",
            searchKeywords = listOf("lime", "electric green", "bright", "vibrant", "citrus", "energy", "neon"),
            category = "Vibrant"
        ),
        DrinkColorItem(
            id = "electric_blue",
            color = Color(0xFF00E5FF),
            displayName = "Electric Blue",
            searchKeywords = listOf("electric blue", "neon", "bright", "energy drink", "vibrant", "glow"),
            category = "Vibrant"
        )
    )
    
    fun searchColors(query: String): List<DrinkColorItem> {
        if (query.isBlank()) return colors
        
        val queryLower = query.lowercase()
        return colors.filter { color ->
            color.displayName.lowercase().contains(queryLower) ||
            color.searchKeywords.any { keyword -> 
                keyword.lowercase().contains(queryLower) 
            } ||
            color.category.lowercase().contains(queryLower)
        }.sortedBy { color ->
            // Prioritize exact matches in display name, then keywords, then category
            when {
                color.displayName.lowercase() == queryLower -> 0
                color.displayName.lowercase().startsWith(queryLower) -> 1
                color.searchKeywords.any { it.lowercase() == queryLower } -> 2
                color.searchKeywords.any { it.lowercase().startsWith(queryLower) } -> 3
                else -> 4
            }
        }
    }
    
    fun getColorsByCategory(): Map<String, List<DrinkColorItem>> {
        return colors.groupBy { it.category }.toSortedMap()
    }
    
    fun getColorById(colorId: String): DrinkColorItem? {
        return colors.find { it.id == colorId }
    }
}