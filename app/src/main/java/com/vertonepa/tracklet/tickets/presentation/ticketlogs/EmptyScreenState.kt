package com.vertonepa.tracklet.tickets.presentation.ticketlogs

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun EmptyScreenState(
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Center,
    addLog: () -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = textAlign,
            text = "No hay actividad registrada",
            fontWeight = FontWeight.SemiBold,
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { addLog() },
            textAlign = textAlign,
            color = Color(0xFF3949AB),
            text = "Agrega el primer registro",
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    EmptyScreenState(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        addLog = {},
    )
}