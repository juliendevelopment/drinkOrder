package com.drinkorder.data

import androidx.compose.ui.graphics.vector.ImageVector
import com.drinkorder.ui.icons.DrinkIcons

data class DrinkIconItem(
    val id: String,
    val icon: ImageVector,
    val displayName: String,
    val defaultColorId: String
)

object DrinkIconDatabase {
    const val DEFAULT_ICON_ID = "local_drink"

    val icons = listOf(
        DrinkIconItem("beer", DrinkIcons.Beer, "Beer", "amber"),
        DrinkIconItem("beer_na", DrinkIcons.BeerNonAlcoholic, "Alcohol-free Beer", "light_green"),
        DrinkIconItem("beer_special", DrinkIcons.SpecialBeer, "Special Beer", "copper"),
        DrinkIconItem("water", DrinkIcons.Water, "Water", "light_blue"),
        DrinkIconItem("sparkling_water", DrinkIcons.SparklingWater, "Sparkling Water", "cyan"),
        DrinkIconItem("cola", DrinkIcons.Cola, "Cola", "red"),
        DrinkIconItem("coffee", DrinkIcons.Coffee, "Coffee", "brown"),
        DrinkIconItem("red_bull", DrinkIcons.RedBull, "Red Bull", "indigo"),
        DrinkIconItem("juice", DrinkIcons.Juice, "Juice", "orange"),
        DrinkIconItem(DEFAULT_ICON_ID, DrinkIcons.Glass, "Other", "grey")
    )

    // Icon ids from the previous Material icon set, mapped to the closest custom icon
    private val legacyIds = mapOf(
        "sports_bar" to "beer",
        "local_bar" to "beer_special",
        "wine_bar" to "beer_special",
        "water_drop" to "water",
        "bubble_chart" to "cola",
        "local_cafe" to "coffee",
        "emoji_food_beverage" to "coffee",
        "free_breakfast" to "coffee",
        "local_fire_department" to "coffee",
        "fitness_center" to "red_bull",
        "directions_run" to "red_bull",
        "eco" to "juice",
        "local_florist" to "juice",
        "blender" to "juice"
    )

    fun resolveId(iconId: String): String {
        val id = legacyIds[iconId] ?: iconId
        return if (icons.any { it.id == id }) id else DEFAULT_ICON_ID
    }

    fun getIconById(iconId: String): DrinkIconItem {
        val id = resolveId(iconId)
        return icons.first { it.id == id }
    }
}
