package com.example.jobharbor.ui.login.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun WarningCircleIcon() {
    Icon(
        imageVector = Icons.Default.Warning,
        contentDescription = "Warning",
        modifier = Modifier
            .size(24.dp)
            .background(Color.Red, CircleShape)
            .padding(4.dp),
        tint = Color.White
    )
}