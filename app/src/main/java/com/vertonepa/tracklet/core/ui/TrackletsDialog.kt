package com.vertonepa.tracklet.core.ui

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


/**
 * Cuando el usuario necesita decidir si realmente quiere realizar una acción
 * o tener la posibilidad de rechazarla, debe presentarse este diálogo
 */
@Composable
fun TrackletDialog(
    onDismissRequest: () -> Unit,
    confirmButton: () -> Unit,
    dismissButton: () -> Unit,
    text: String
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = { Button(onClick = confirmButton) { Text("Confirm") } },
        dismissButton = { TextButton(onClick = dismissButton) { Text("Dismiss") } },
        text = { Text(text = text) },
        shape = RoundedCornerShape(12.dp)
    )
}

/**
 * Dialogo para acciones que no se pueden realizar
 */
@Composable
fun BlockActionDialog(
    onDismissRequest: () -> Unit,
    confirmButton: () -> Unit,
    text: String
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = { TextButton(onClick = confirmButton) { Text("Entendido") } },
        text = { Text(text = text) },
        shape = RoundedCornerShape(12.dp)
    )
}

@Preview
@Composable
private fun Preview() {
    BlockActionDialog(
        onDismissRequest = {},
        confirmButton = {},
        text = "Hay un contador activo, para iniciar uno nuevo debe finalizar con el anterior"
    )
}