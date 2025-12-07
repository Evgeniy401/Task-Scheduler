package com.example.app.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun GeneralButton(
    modifier: Modifier = Modifier,
    text: String = "",
    onClick: () -> Unit,
    content: @Composable () -> Unit = {
        Text(
            text = text,
            color = colorScheme.onTertiary
        )
    }
) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .height(45.dp)
            .padding(horizontal = 16.dp),
        onClick = onClick,
        shape = RoundedCornerShape(30.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorScheme.tertiary,
            contentColor = colorScheme.onTertiary
        )
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            content()
        }
    }
}

