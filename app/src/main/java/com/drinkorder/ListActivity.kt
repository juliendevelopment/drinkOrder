package com.drinkorder

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.drinkorder.data.ListItem
import com.drinkorder.repository.ListRepository
import com.drinkorder.ui.theme.DrinkOrderTheme
import com.drinkorder.ui.components.SearchableIconSelector
import com.drinkorder.ui.components.SearchableColorSelector
import com.drinkorder.ui.components.IconPreview
import com.drinkorder.ui.components.ColorPreview
import com.drinkorder.viewmodel.ListViewModel
import com.drinkorder.viewmodel.ListViewModelFactory
import org.burnoutcrew.reorderable.*

class ListActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val repository = ListRepository(this)
        
        setContent {
            DrinkOrderTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ListScreen(repository = repository)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(repository: ListRepository) {
    val context = LocalContext.current
    val viewModel: ListViewModel = viewModel(
        factory = ListViewModelFactory(repository)
    )
    
    val items by viewModel.items.collectAsState()
    val newItemText by viewModel.newItemText.collectAsState()
    val selectedIconId by viewModel.selectedIconId.collectAsState()
    val selectedColorId by viewModel.selectedColorId.collectAsState()
    var showIconSelector by remember { mutableStateOf(false) }
    var showColorSelector by remember { mutableStateOf(false) }
    var editingItem by remember { mutableStateOf<ListItem?>(null) }
    var editItemText by remember { mutableStateOf("") }
    var editIconId by remember { mutableStateOf("local_drink") }
    var editColorId by remember { mutableStateOf("blue") }
    var isEditingIcon by remember { mutableStateOf(false) }
    var isEditingColor by remember { mutableStateOf(false) }
    
    val state = rememberReorderableLazyListState(
        onMove = { from, to ->
            val newList = items.toMutableList()
            val item = newList.removeAt(from.index)
            newList.add(to.index, item)
            viewModel.reorderItems(newList)
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "My Drinks",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            
            FloatingActionButton(
                onClick = {
                    val intent = Intent(context, GridActivity::class.java)
                    context.startActivity(intent)
                },
                modifier = Modifier.size(48.dp)
            ) {
                Icon(Icons.Default.Menu, contentDescription = "Grid View")
            }
        }
        
        // Add item section
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            // Icon and Color selection row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Icon selection
                Text(
                    text = "Icon:",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                
                Spacer(modifier = Modifier.width(8.dp))
                
                OutlinedButton(
                    onClick = { showIconSelector = true },
                    modifier = Modifier.height(40.dp)
                ) {
                    IconPreview(
                        iconId = selectedIconId,
                        size = 20.dp,
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Change")
                }
                
                Spacer(modifier = Modifier.width(16.dp))
                
                // Color selection
                Text(
                    text = "Color:",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                
                Spacer(modifier = Modifier.width(8.dp))
                
                OutlinedButton(
                    onClick = { showColorSelector = true },
                    modifier = Modifier.height(40.dp)
                ) {
                    ColorPreview(
                        colorId = selectedColorId,
                        size = 20.dp
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Change")
                }
            }
            
            // Text input and add button row
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = newItemText,
                    onValueChange = viewModel::updateNewItemText,
                    label = { Text("Add new drink") },
                    placeholder = { Text("e.g., Cappuccino, Orange Juice...") },
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
                
                Spacer(modifier = Modifier.width(8.dp))
                
                FloatingActionButton(
                    onClick = viewModel::addItem,
                    modifier = Modifier.size(56.dp)
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add drink")
                }
            }
        }
        
        // Items list
        LazyColumn(
            state = state.listState,
            modifier = Modifier
                .fillMaxSize()
                .reorderable(state),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(items, key = { it.id }) { item ->
                ReorderableItem(state, key = item.id) { isDragging ->
                    ListItemCard(
                        item = item,
                        isDragging = isDragging,
                        onRemove = { viewModel.removeItem(item.id) },
                        onEdit = { 
                            editingItem = item
                            editItemText = item.text
                            editIconId = item.iconId
                            editColorId = item.colorId
                        },
                        modifier = Modifier
                            .detectReorderAfterLongPress(state)
                            .fillMaxWidth()
                    )
                }
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
                        text = "No drinks added yet",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Add your first drink above",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
    
    // Icon Selector Dialog
    if (showIconSelector) {
        SearchableIconSelector(
            selectedIconId = if (isEditingIcon) editIconId else selectedIconId,
            onIconSelected = { iconId ->
                if (isEditingIcon) {
                    editIconId = iconId
                    isEditingIcon = false
                } else {
                    viewModel.updateSelectedIconId(iconId)
                }
                showIconSelector = false
            },
            onDismiss = { 
                showIconSelector = false
                if (isEditingIcon) {
                    isEditingIcon = false
                }
            }
        )
    }
    
    // Color Selector Dialog
    if (showColorSelector) {
        SearchableColorSelector(
            selectedColorId = if (isEditingColor) editColorId else selectedColorId,
            onColorSelected = { colorId ->
                if (isEditingColor) {
                    editColorId = colorId
                    isEditingColor = false
                } else {
                    viewModel.updateSelectedColorId(colorId)
                }
                showColorSelector = false
            },
            onDismiss = { 
                showColorSelector = false
                if (isEditingColor) {
                    isEditingColor = false
                }
            }
        )
    }
    
    // Edit Item Dialog
    editingItem?.let { item ->
        AlertDialog(
            onDismissRequest = { 
                editingItem = null
                editItemText = ""
                editIconId = "local_drink"
                editColorId = "blue"
            },
            title = { Text("Edit Drink") },
            text = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Icon and Color selection section
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        // Icon selection
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = "Icon:",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            
                            OutlinedButton(
                                onClick = { 
                                    isEditingIcon = true
                                    showIconSelector = true
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp)
                            ) {
                                IconPreview(
                                    iconId = editIconId,
                                    size = 20.dp,
                                    tint = MaterialTheme.colorScheme.onSurface
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Change")
                            }
                        }
                        
                        Spacer(modifier = Modifier.width(8.dp))
                        
                        // Color selection
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = "Color:",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            
                            OutlinedButton(
                                onClick = { 
                                    isEditingColor = true
                                    showColorSelector = true
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp)
                            ) {
                                ColorPreview(
                                    colorId = editColorId,
                                    size = 20.dp
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Change")
                            }
                        }
                    }
                    
                    // Text input section
                    OutlinedTextField(
                        value = editItemText,
                        onValueChange = { editItemText = it },
                        label = { Text("Drink name") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        if (editItemText.isNotBlank()) {
                            viewModel.updateItem(
                                itemId = item.id,
                                newText = editItemText.trim(),
                                newIconId = editIconId,
                                newColorId = editColorId
                            )
                            editingItem = null
                            editItemText = ""
                            editIconId = "local_drink"
                            editColorId = "blue"
                        }
                    }
                ) {
                    Text("Save")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        editingItem = null
                        editItemText = ""
                        editIconId = "local_drink"
                        editColorId = "blue"
                    }
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun ListItemCard(
    item: ListItem,
    isDragging: Boolean,
    onRemove: () -> Unit,
    onEdit: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .shadow(
                elevation = if (isDragging) 8.dp else 2.dp,
                shape = RoundedCornerShape(8.dp)
            ),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isDragging) 
                MaterialTheme.colorScheme.surfaceVariant 
            else 
                MaterialTheme.colorScheme.surface
        )
    ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Color indicator
                ColorPreview(
                    colorId = item.colorId,
                    size = 32.dp
                )
                
                Spacer(modifier = Modifier.width(12.dp))
                
                IconPreview(
                    iconId = item.iconId,
                    size = 24.dp,
                    tint = MaterialTheme.colorScheme.primary
                )
                
                Spacer(modifier = Modifier.width(12.dp))
                
                Text(
                    text = item.text,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.weight(1f)
                )
                
                Row {
                    IconButton(onClick = onEdit) {
                        Icon(
                            Icons.Default.Edit,
                            contentDescription = "Edit item",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    
                    IconButton(onClick = onRemove) {
                        Icon(
                            Icons.Default.Delete,
                            contentDescription = "Remove item",
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }
    }
}