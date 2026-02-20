package com.vertonepa.tracklet.tickets.presentation.ticketlogs.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.vertonepa.tracklet.core.ui.TrackletIcons

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MultiSelectionTopBar(
    itemsSelected: Int,
    onDelete: () -> Unit,
    onCancelActions: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text =
                    if (itemsSelected > 1) "$itemsSelected seleccionados"
                    else "1 seleccionado",
                style = MaterialTheme.typography.titleMedium
            )
        },
        actions = {
            IconButton(onClick = onDelete) {
                Icon(
                    painter = painterResource(TrackletIcons.Delete), contentDescription = "Eliminar"
                )
            }
            IconButton(onClick = onCancelActions) {
                Icon(
                    painter = painterResource(TrackletIcons.Close), contentDescription = "Cancelar"
                )
            }
        })
}


@Composable
fun DeleteDialog(
    onDismiss: () -> Unit,
    onDelete: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.95f)
        ) {
            Column(
                modifier = Modifier.padding(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Eliminación de registros",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleMedium
                )
                HorizontalDivider()
                Text(
                    text = "Se borrarán todos los registros seleccionados. Una vez eliminados no se podrán recuperar, ¿Continuar con la acción?",
                    style = MaterialTheme.typography.bodySmall
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(onClick = onDismiss) {
                        Text(
                            modifier = Modifier,
                            text = "Cancelar",
                            textAlign = TextAlign.Center
                        )
                    }
                    Button(
                        onClick = onDelete,
                        modifier = Modifier,
                        colors = ButtonColors(
                            containerColor = Color.Red,
                            contentColor = Color.White,
                            disabledContainerColor = Color.Red,
                            disabledContentColor = Color.White
                        ),
                    ) {
                        Text(
                            text = "Eliminar",
                            textAlign = TextAlign.Center
                        )
                    }
                }

            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    MultiSelectionTopBar(
        itemsSelected = 10,
        onDelete = {},
        onCancelActions = {}
    )
}

@Preview
@Composable
private fun Dialog_Preview() {
    DeleteDialog(
        onDismiss = {},
        onDelete = {}
    )
}