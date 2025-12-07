package com.vertonepa.tracklet.tickets.presentation.ticketlogs.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

private val widthModifier = Modifier.fillMaxWidth()


@Composable
fun TicketLogDialog(
    quantity: String,
    color: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    onQuantityChange: (String) -> Unit,
    onColorChange: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val colorFocus = remember { FocusRequester() }
    val quantityFocus = remember { FocusRequester() }


    if (!LocalInspectionMode.current) {
        LaunchedEffect(Unit) {
            colorFocus.requestFocus()
        }
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false,
            usePlatformDefaultWidth = false
        )
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(0.95f)
        ) {
            Column(
                modifier = widthModifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Agregar un nuevo registro",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleMedium
                )
                Column(
                    modifier = widthModifier,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = color,
                        onValueChange = onColorChange,
                        label = { Text(text = "Color") },
                        modifier = widthModifier
                            .focusRequester(colorFocus),
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = { quantityFocus.requestFocus() }
                        )
                    )
                    OutlinedTextField(
                        value = quantity,
                        onValueChange = onQuantityChange,
                        label = { Text(text = "Cantidad") },
                        modifier = widthModifier
                            .focusRequester(quantityFocus),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                focusManager.clearFocus()
                                onConfirm()
                            }
                        )
                    )
                    Row(
                        modifier = widthModifier.focusRequester(colorFocus),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Button(onClick = onDismiss) {
                            Text(text = "Cancelar")
                        }
                        Button(
                            onClick = {
                                focusManager.clearFocus()
                                onConfirm()
                            },
                            enabled = quantity.isNotEmpty() && color.isNotEmpty()
                        ) {
                            Text(text = "Registrar")
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    TicketLogDialog(
        onDismiss = {},
        onConfirm = {},
        quantity = "0",
        color = "",
        onQuantityChange = {},
        onColorChange = {}
    )
}