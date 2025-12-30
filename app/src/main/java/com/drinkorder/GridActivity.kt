package com.drinkorder

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.drinkorder.data.ListItem
import com.drinkorder.data.DrinkColorDatabase
import com.drinkorder.repository.ListRepository
import com.drinkorder.ui.theme.DrinkOrderTheme
import com.drinkorder.ui.components.IconPreview
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
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                )
            ) {
                Text(
                    text = orderSummary,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(12.dp),
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
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
fun GridItemCard(
    item: ListItem,
    count: Int,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Get the color for this item
    val drinkColor = DrinkColorDatabase.getColorById(item.colorId)?.color
        ?: DrinkColorDatabase.getColorById("blue")!!.color // Fallback to blue if color not found
    
    // Calculate text color for proper contrast against the drink color
    // Use white text for dark colors, black text for light colors
    val isLightColor = (drinkColor.red + drinkColor.green + drinkColor.blue) / 3 > 0.5f
    val textColor = if (isLightColor) Color.Black else Color.White
    val iconTint = textColor
    
    Card(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1f),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = drinkColor // Use the drink's assigned color as background
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (count > 0) 8.dp else 4.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Icon at the top
            IconPreview(
                iconId = item.iconId,
                size = 40.dp,
                tint = iconTint
            )
            
            // Drink name
            Text(
                text = item.text,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.weight(1f),
                maxLines = 2,
                color = textColor
            )
            
            // Count display
            Text(
                text = count.toString(),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = textColor
            )
            
            // Increment/Decrement buttons
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = onDecrement,
                    enabled = count > 0,
                    modifier = Modifier.size(36.dp),
                    contentPadding = PaddingValues(0.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (count > 0)
                            Color.Black.copy(alpha = 0.2f)
                        else
                            Color.Gray.copy(alpha = 0.3f),
                        disabledContainerColor = Color.Gray.copy(alpha = 0.2f)
                    )
                ) {
                    Text(
                        "-", 
                        style = MaterialTheme.typography.headlineSmall,
                        color = textColor
                    )
                }
                
                Button(
                    onClick = onIncrement,
                    modifier = Modifier.size(36.dp),
                    contentPadding = PaddingValues(0.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black.copy(alpha = 0.3f)
                    )
                ) {
                    Text(
                        "+", 
                        style = MaterialTheme.typography.headlineSmall,
                        color = textColor
                    )
                }
            }
        }
    }
}