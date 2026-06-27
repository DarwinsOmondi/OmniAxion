package com.example.omniaxion.presentation.lexicon

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.omniaxion.domain.model.JargonTerm
import com.example.omniaxion.ui.theme.OmniAxionTheme
import com.example.omniaxion.ui.theme.OmniAxisColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LexiconBottomSheet(
    jargonTerm: JargonTerm,
    onDismiss: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = Color.White,
        dragHandle = null
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "LEXICON ENTRY",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray
                )
                IconButton(onClick = onDismiss, modifier = Modifier.size(16.dp)) {
                    Text("\u2715", fontSize = 12.sp, color = Color.Gray)
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = jargonTerm.term,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = jargonTerm.description ?: "No description available.",
                fontSize = 16.sp,
                lineHeight = 24.sp,
                color = Color.DarkGray
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            TextButton(
                onClick = { /* Navigate to deep dive */ },
                contentPadding = PaddingValues(0.dp)
            ) {
                Text(
                    "KEEP READING \u2192",
                    color = OmniAxisColors.PrimaryBlue,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp
                )
            }
            
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LexiconBottomSheetPreview() {
    OmniAxionTheme {
        LexiconBottomSheet(
            jargonTerm = JargonTerm(
                id = 1,
                term = "repo rate",
                type = "FINANCE",
                description = "The rate at which commercial banks borrow money from the central bank using securities.",
                occurrences = emptyList()
            ),
            onDismiss = {}
        )
    }
}
