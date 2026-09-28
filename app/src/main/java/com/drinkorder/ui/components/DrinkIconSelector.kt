package com.drinkorder.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.drinkorder.data.DrinkColorDatabase
import com.drinkorder.data.DrinkIconDatabase
import com.drinkorder.data.DrinkIconItem

@Composable
fun DrinkIconSelector(
    selectedIconId: String?,
    onIconSelected: (DrinkIconItem) -> Unit,
    onDismiss: () -> Unit
) {
    val selectedId = selectedIconId?.let { DrinkIconDatabase.resolveId(it) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Select Icon",
                style = MaterialTheme.typography.headlineSmall
            )
        },
        text = {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(DrinkIconDatabase.icons) { iconItem ->
                    IconSelectionCard(
                        iconItem = iconItem,
                        isSelected = selectedId == iconItem.id,
                        onClick = { onIconSelected(iconItem) }
                    )
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Composable
fun IconSelectionCard(
    iconItem: DrinkIconItem,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    // Show each icon on its suggested color so the picker previews the grid card
    val background = DrinkColorDatabase.getColorById(iconItem.defaultColorId)?.color ?: Color.Gray
    val contentColor = readableContentColor(background)

    Card(
        modifier = Modifier
            .aspectRatio(1f)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = background),
        border = if (isSelected) {
            BorderStroke(3.dp, MaterialTheme.colorScheme.primary)
        } else {
            null
        },
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isSelected) 6.dp else 2.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(6.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = iconItem.icon,
                contentDescription = iconItem.displayName,
                modifier = Modifier.size(36.dp),
                tint = contentColor
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = iconItem.displayName,
                style = MaterialTheme.typography.labelSmall,
                textAlign = TextAlign.Center,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                color = contentColor
            )
        }
    }
}

@Composable
fun IconPreview(
    iconId: String,
    size: Dp = 24.dp,
    tint: Color = MaterialTheme.colorScheme.onSurface
) {
    val iconItem = DrinkIconDatabase.getIconById(iconId)
    Icon(
        imageVector = iconItem.icon,
        contentDescription = iconItem.displayName,
        modifier = Modifier.size(size),
        tint = tint
    )
}

/** Black or white, whichever reads best on top of [background]. */
fun readableContentColor(background: Color): Color {
    val isLightColor = (background.red + background.green + background.blue) / 3 > 0.5f
    return if (isLightColor) Color.Black else Color.White
}
