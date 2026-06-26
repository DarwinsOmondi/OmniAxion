package com.example.omniaxion.presentation.payment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.omniaxion.ui.theme.OmniAxionTheme
import com.example.omniaxion.ui.theme.OmniAxisColors

@Composable
fun PaymentScreen(
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp)
    ) {
        IconButton(onClick = onBackClick, modifier = Modifier.size(24.dp)) {
            Text("\u2190", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        Text(
            "UNLOCK DEEP\nINTELLIGENCE.",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 34.sp,
            color = OmniAxisColors.PrimaryBlue
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        val features = listOf(
            "Full access to the Context Lexicon",
            "Uncapped historical timelines",
            "Geographic bias maps"
        )
        
        features.forEach { feature ->
            Row(
                modifier = Modifier.padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("\u2713", color = OmniAxisColors.PrimaryBlue, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.width(12.dp))
                Text(feature, fontSize = 14.sp, color = Color.Gray)
            }
        }
        
        Spacer(modifier = Modifier.height(48.dp))
        
        Text(
            text = "$4.99 / monthly",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        
        Spacer(modifier = Modifier.weight(1f))
        
        Button(
            onClick = {},
            modifier = Modifier.fillMaxWidth().height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = OmniAxisColors.PrimaryBlue),
            shape = MaterialTheme.shapes.extraSmall
        ) {
            Text("PAY WITH APPLE / GOOGLE PAY", fontWeight = FontWeight.Bold)
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        OutlinedButton(
            onClick = {},
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape = MaterialTheme.shapes.extraSmall
        ) {
            Text("PROCESS PAYMENT", color = OmniAxisColors.PrimaryBlue, fontWeight = FontWeight.Bold)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PaymentScreenPreview() {
    OmniAxionTheme {
        PaymentScreen(onBackClick = {})
    }
}
