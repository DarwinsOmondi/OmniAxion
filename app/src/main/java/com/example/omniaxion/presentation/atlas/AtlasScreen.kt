package com.example.omniaxion.presentation.atlas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.omniaxion.BuildConfig
import com.example.omniaxion.ui.theme.OmniAxionTheme
import com.mapbox.maps.MapView
import com.mapbox.maps.Style

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AtlasScreen(
    onBackClick: () -> Unit
) {
    val context = LocalContext.current
    val mapView = remember {
        MapView(context).apply {
            mapboxMap.loadStyle(Style.MAPBOX_STREETS)
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            "OMNIAXIS",
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            letterSpacing = 2.sp
                        )
                        Text(
                            "Global Intelligence",
                            fontSize = 10.sp,
                            color = Color.Gray
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Text("\u2190", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                    }
                },
                actions = {
                    TextButton(onClick = {}) {
                        Text("LIST", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            AndroidView(
                factory = { mapView },
                modifier = Modifier.fillMaxSize()
            )

            // Floating Bottom Control
            Card(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(24.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        "Local reporting at epicenter is focused on regional payrolls.",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        lineHeight = 18.sp
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AtlasScreenPreview() {
    OmniAxionTheme {
        AtlasScreen(onBackClick = {})
    }
}
