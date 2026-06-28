package com.example.omniaxion.presentation.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.omniaxion.ui.theme.OmniAxionTheme
import com.example.omniaxion.ui.theme.OmniAxisColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterBottomSheet(
    currentFilters: NewsFilters,
    onApplyFilters: (language: String?, country: String?, query: String?) -> Unit,
    onDismiss: () -> Unit
) {
    var selectedLanguage by remember { mutableStateOf(currentFilters.languageCode) }
    var selectedCountry by remember { mutableStateOf(currentFilters.countryCode) }
    var searchQuery by remember { mutableStateOf(currentFilters.searchQuery ?: "") }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = Color.White,
        dragHandle = { BottomSheetDefaults.DragHandle(color = OmniAxisColors.PrimaryBlue) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "ADVANCED FILTERS",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.Black,
                    letterSpacing = 1.sp
                )
                TextButton(
                    onClick = {
                        selectedLanguage = "en"
                        selectedCountry = null
                        searchQuery = ""
                    }
                ) {
                    Text("RESET", color = Color.Gray, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))

            // Search by Title
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Search intelligence database") },
                placeholder = { Text("Enter keywords...") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = OmniAxisColors.PrimaryBlue) },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { searchQuery = "" }) {
                            Icon(Icons.Default.Close, contentDescription = "Clear", tint = Color.Gray)
                        }
                    }
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = OmniAxisColors.PrimaryBlue,
                    focusedLabelColor = OmniAxisColors.PrimaryBlue,
                    unfocusedBorderColor = OmniAxisColors.DividerGray
                ),
                shape = MaterialTheme.shapes.small
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Language Dropdown
            FilterDropdown(
                label = "Output Language",
                icon = Icons.Default.Language,
                options = listOf(
                    "en" to "English (US/UK)",
                    "fr" to "French (Standard)",
                    "es" to "Spanish (Castilian)",
                    "de" to "German (Standard)"
                ),
                selectedCode = selectedLanguage,
                onSelect = { selectedLanguage = it }
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Country Dropdown
            FilterDropdown(
                label = "Source Geography",
                icon = Icons.Default.Public,
                options = listOf(
                    null to "Global Coverage",
                    "us" to "United States",
                    "gb" to "United Kingdom",
                    "ca" to "Canada",
                    "au" to "Australia"
                ),
                selectedCode = selectedCountry,
                onSelect = { selectedCountry = it }
            )

            Spacer(modifier = Modifier.height(40.dp))

            Button(
                onClick = { 
                    onApplyFilters(selectedLanguage, selectedCountry, searchQuery.ifBlank { null })
                    onDismiss()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                colors = ButtonDefaults.buttonColors(containerColor = OmniAxisColors.PrimaryBlue),
                shape = MaterialTheme.shapes.extraSmall
            ) {
                Text(
                    "APPLY INTELLIGENCE FILTERS", 
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
            }
            
            Spacer(modifier = Modifier.height(48.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterDropdown(
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    options: List<Pair<String?, String>>,
    selectedCode: String?,
    onSelect: (String?) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val selectedName = options.find { it.first == selectedCode }?.second ?: "Global Coverage"

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = Modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            value = selectedName,
            onValueChange = {},
            readOnly = true,
            label = { Text(label) },
            leadingIcon = { Icon(icon, contentDescription = null, tint = OmniAxisColors.PrimaryBlue) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryEditable, enabled = true).fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = OmniAxisColors.PrimaryBlue,
                focusedLabelColor = OmniAxisColors.PrimaryBlue,
                unfocusedBorderColor = OmniAxisColors.DividerGray
            ),
            shape = MaterialTheme.shapes.small
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.background(Color.White)
        ) {
            options.forEach { (code, name) ->
                DropdownMenuItem(
                    text = { 
                        Text(
                            name, 
                            fontWeight = if (code == selectedCode) FontWeight.Bold else FontWeight.Normal,
                            color = if (code == selectedCode) OmniAxisColors.PrimaryBlue else Color.Black
                        ) 
                    },
                    onClick = {
                        onSelect(code)
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FilterBottomSheetPreview() {
    OmniAxionTheme {
        FilterBottomSheet(
            currentFilters = NewsFilters(
                languageCode = "en",
                countryCode = "us",
                searchQuery = "Quantum Computing"
            ),
            onApplyFilters = { _, _, _ -> },
            onDismiss = {}
        )
    }
}
