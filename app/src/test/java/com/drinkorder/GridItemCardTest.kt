package com.drinkorder

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.unit.dp
import com.drinkorder.data.ListItem
import com.drinkorder.ui.theme.DrinkOrderTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class GridItemCardTest {

    @get:Rule
    val composeRule = createComposeRule()

    private fun setCard() {
        composeRule.setContent {
            DrinkOrderTheme {
                var count by remember { mutableIntStateOf(0) }
                GridItemCard(
                    item = ListItem(id = "1", text = "Beer", order = 0, iconId = "beer", colorId = "amber"),
                    count = count,
                    onIncrement = { count++ },
                    onDecrement = { count-- },
                    modifier = Modifier.size(180.dp)
                )
            }
        }
    }

    private val addArea get() = composeRule.onNode(hasText("Beer") and hasClickAction())
    private val removeButton get() = composeRule.onNode(hasText("−") and hasClickAction())

    @Test
    fun tappingTheCardAddsOne() {
        setCard()

        addArea.performClick()
        addArea.performClick()

        composeRule.onNodeWithText("2").assertExists()
    }

    @Test
    fun removeButtonRemovesOneAndIsDisabledAtZero() {
        setCard()
        removeButton.assertIsNotEnabled()

        addArea.performClick()
        removeButton.assertIsEnabled()
        removeButton.performClick()

        composeRule.onNodeWithText("0").assertExists()
        removeButton.assertIsNotEnabled()
    }
}
