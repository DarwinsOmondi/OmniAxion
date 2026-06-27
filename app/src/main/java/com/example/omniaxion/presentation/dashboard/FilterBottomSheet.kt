package com.example.omniaxion.presentation.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.omniaxion.ui.theme.OmniAxisColors
import androidx.compose.ui.tooling.preview.Preview
import com.example.omniaxion.ui.theme.OmniAxionTheme


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
                .padding(24.dp)
        ) {
            Text(
                "ADVANCED FILTERS",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Gray,
                letterSpacing = 1.sp
            )
            
            Spacer(modifier = Modifier.height(24.dp))

            // Search by Title
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Search by Title") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = OmniAxisColors.PrimaryBlue,
                    focusedLabelColor = OmniAxisColors.PrimaryBlue
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Language Dropdown
            FilterDropdown(
                label = "Language",
                options = listOf(
                    "en" to "English",
                    "fr" to "French",
                    "es" to "Spanish",
                    "de" to "German"
                ),
                selectedCode = selectedLanguage,
                onSelect = { selectedLanguage = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Country Dropdown
            FilterDropdown(
                label = "Source Country",
                options = listOf(
                    null to "Global (All)",
                    "us" to "United States",
                    "gb" to "United Kingdom",
                    "ca" to "Canada",
                    "au" to "Australia"
                ),
                selectedCode = selectedCountry,
                onSelect = { selectedCountry = it }
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = { 
                    onApplyFilters(selectedLanguage, selectedCountry, searchQuery.ifBlank { null })
                    onDismiss()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = OmniAxisColors.PrimaryBlue),
                shape = MaterialTheme.shapes.extraSmall
            ) {
                Text("APPLY INTELLIGENCE FILTERS", fontWeight = FontWeight.Bold)
            }
            
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterDropdown(
    label: String,
    options: List<Pair<String?, String>>,
    selectedCode: String?,
    onSelect: (String?) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val selectedName = options.find { it.first == selectedCode }?.second ?: "Select $label"

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
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryEditable, enabled = true).fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = OmniAxisColors.PrimaryBlue,
                focusedLabelColor = OmniAxisColors.PrimaryBlue
            )
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.background(Color.White)
        ) {
            options.forEach { (code, name) ->
                DropdownMenuItem(
                    text = { Text(name) },
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
