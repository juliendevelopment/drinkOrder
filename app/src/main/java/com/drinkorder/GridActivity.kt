package com.drinkorder

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.drinkorder.data.ListItem
import com.drinkorder.data.DrinkColorDatabase
import com.drinkorder.repository.ListRepository
import com.drinkorder.ui.theme.DrinkOrderTheme
import com.drinkorder.ui.components.IconPreview
import com.drinkorder.ui.components.readableContentColor
import com.drinkorder.viewmodel.GridViewModel
import com.drinkorder.viewmodel.GridViewModelFactory

class GridActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val repository = ListRepository(this)
        
        setContent {
            DrinkOrderTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GridScreen(repository = repository)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GridScreen(repository: ListRepository) {
    val context = LocalContext.current
    val viewModel: GridViewModel = viewModel(
        factory = GridViewModelFactory(repository)
    )
    
    val items by viewModel.items.collectAsState()
    val itemCounts by viewModel.itemCounts.collectAsState()
    val totalCount by viewModel.totalCount.collectAsState()
    val orderSummary by viewModel.orderSummary.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Order Drinks",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = { viewModel.resetCounts() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Text("Reset", color = MaterialTheme.colorScheme.onError)
                }
                
                FloatingActionButton(
                    onClick = {
                        val intent = Intent(context, ListActivity::class.java)
                        context.startActivity(intent)
                    },
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(Icons.Default.List, contentDescription = "List View")
                }
            }
        }
        
        Text(
            text = "Total Items: $totalCount",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        
        if (orderSummary.isNotEmpty()) {
            OrderSummaryCard(summary = orderSummary)
        }
        
        if (items.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "No drinks available",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Add drinks in the list view first",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )
                }
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(items) { item ->
                    GridItemCard(
                        item = item,
                        count = itemCounts[item.id] ?: 0,
                        onIncrement = { viewModel.incrementItem(item.id) },
                        onDecrement = { viewModel.decrementItem(item.id) }
                    )
                }
            }
        }
    }
}

@Composable
fun OrderSummaryCard(summary: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    ) {
        // Larger text, split over two columns so the grid buttons stay reachable
        val lines = summary.lines()
        val columns = if (lines.size > 3) lines.chunked((lines.size + 1) / 2) else listOf(lines)
        Row(modifier = Modifier.padding(12.dp)) {
            columns.forEach { columnLines ->
                Column(modifier = Modifier.weight(1f)) {
                    columnLines.forEach { line ->
                        // Only the name is truncated, so the count always stays visible
                        Row(modifier = Modifier.padding(end = 8.dp)) {
                            Text(
                                text = line.substringBeforeLast(":"),
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Medium,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.weight(1f, fill = false),
                                color = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                            if (":" in line) {
                                Text(
                                    text = ":" + line.substringAfterLast(":"),
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontWeight = FontWeight.Bold,
                                    maxLines = 1,
                                    color = MaterialTheme.colorScheme.onSecondaryContainer
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun GridItemCard(
    item: ListItem,
    count: Int,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    modifier: Modifier = Modifier
) {
    val haptic = LocalHapticFeedback.current

    // Get the color for this item
    val drinkColor = DrinkColorDatabase.getColorById(item.colorId)?.color
        ?: DrinkColorDatabase.getColorById("blue")!!.color // Fallback to blue if color not found
    
    // Use white text for dark colors, black text for light colors
    val textColor = readableContentColor(drinkColor)
    
    Card(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1f),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = drinkColor, // Use the drink's assigned color as background
            contentColor = textColor
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (count > 0) 8.dp else 4.dp
        )
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Top 3/4: tapping anywhere adds one
            Box(
                modifier = Modifier
                    .weight(3f)
                    .fillMaxWidth()
                    .clickable(onClickLabel = "Add one ${item.text}") {
                        haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                        onIncrement()
                    }
            ) {
                Text(
                    text = "+",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = textColor.copy(alpha = 0.6f),
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 8.dp, vertical = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    IconPreview(
                        iconId = item.iconId,
                        size = 48.dp,
                        tint = textColor
                    )
                    
                    Text(
                        text = item.text,
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Medium,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        color = textColor
                    )
                    
                    Text(
                        text = count.toString(),
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = if (count > 0) textColor else textColor.copy(alpha = 0.5f)
                    )
                }
            }

            // Bottom 1/4: full-width button to remove one
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .background(Color.Black.copy(alpha = if (count > 0) 0.25f else 0.1f))
                    .clickable(enabled = count > 0, onClickLabel = "Remove one ${item.text}") {
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        onDecrement()
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "−",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = if (count > 0) textColor else textColor.copy(alpha = 0.35f)
                )
            }
        }
    }
}
