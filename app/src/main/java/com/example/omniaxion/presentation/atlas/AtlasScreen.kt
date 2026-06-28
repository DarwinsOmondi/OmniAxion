package com.example.omniaxion.presentation.atlas

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.omniaxion.domain.model.NewsArticle
import com.example.omniaxion.ui.theme.OmniAxionTheme
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.Style
import com.mapbox.maps.plugin.animation.flyTo
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AtlasScreen(
    article: NewsArticle?,
    onBackClick: () -> Unit,
    onListClick: () -> Unit
) {
    val context = LocalContext.current
    val isPreview = LocalInspectionMode.current
    
    // Create MapView and keep it in state - skip in preview to avoid render issues
    val mapView = if (isPreview) null else remember {
        MapView(context).apply {
            mapboxMap.loadStyle(Style.OUTDOORS) {
                // Set initial camera for globe view
                mapboxMap.setCamera(
                    CameraOptions.Builder()
                        .center(Point.fromLngLat(0.0, 0.0))
                        .zoom(0.0) // Low zoom for globe effect
                        .build()
                )
            }
        }
    }

    // Update point on map when article changes
    LaunchedEffect(article) {
        if (isPreview || mapView == null) return@LaunchedEffect
        
        article?.locations?.firstOrNull()?.let { geo ->
            val point = Point.fromLngLat(geo.longitude, geo.latitude)
            
            // Wait for style to load before adding annotation
            mapView.mapboxMap.getStyle {
                val annotationApi = mapView.annotations
                val pointAnnotationManager = annotationApi.createPointAnnotationManager()
                pointAnnotationManager.deleteAll()
                
                val pointAnnotationOptions = PointAnnotationOptions()
                    .withPoint(point)
                
                pointAnnotationManager.create(pointAnnotationOptions)
                
                // Fly to the point
                mapView.mapboxMap.flyTo(
                    CameraOptions.Builder()
                        .center(point)
                        .zoom(3.5)
                        .build()
                )
            }
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
                    TextButton(onClick = onListClick) {
                        Text("LIST", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White.copy(alpha = 0.9f))
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            if (isPreview || mapView == null) {
                // Placeholder for MapView in Preview to avoid UninitializedPropertyAccessException
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.DarkGray
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text("Map View (Not available in Preview)", color = Color.White)
                    }
                }
            } else {
                // Map fills the entire Box
                AndroidView(
                    factory = { mapView },
                    modifier = Modifier.fillMaxSize(),
                    update = {}
                )
            }

            // Intelligence Information Card floating at the bottom
            Card(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.95f)),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = article?.title ?: "Select an article to view location",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = article?.description ?: "Location intelligence data will appear here.",
                        fontSize = 13.sp,
                        lineHeight = 20.sp,
                        color = Color.DarkGray
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
        AtlasScreen(
            article = null, 
            onBackClick = {},
            onListClick = {}
        )
    }
}
