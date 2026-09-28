package com.drinkorder.data

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

class DrinkIconDatabaseTest {

    @Test
    fun legacyMaterialIconIdsMapToCustomIcons() {
        assertEquals("beer", DrinkIconDatabase.resolveId("sports_bar"))
        assertEquals("coffee", DrinkIconDatabase.resolveId("local_cafe"))
        assertEquals("water", DrinkIconDatabase.resolveId("water_drop"))
        assertEquals("red_bull", DrinkIconDatabase.resolveId("fitness_center"))
    }

    @Test
    fun unknownIdFallsBackToDefault() {
        assertEquals(DrinkIconDatabase.DEFAULT_ICON_ID, DrinkIconDatabase.getIconById("does_not_exist").id)
    }

    @Test
    fun everyIconHasAnExistingDefaultColor() {
        DrinkIconDatabase.icons.forEach {
            assertNotNull("color for ${it.id}", DrinkColorDatabase.getColorById(it.defaultColorId))
        }
    }
}
