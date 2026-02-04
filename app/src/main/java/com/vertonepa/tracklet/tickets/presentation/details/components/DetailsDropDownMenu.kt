package com.vertonepa.tracklet.tickets.presentation.details.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.vertonepa.tracklet.core.ui.TrackletIcons

@Composable
fun DetailsMenu(onClickDelete: () -> Unit, onClickEdit: () -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    Box {
        IconButton(onClick = { expanded = !expanded }) {
            Icon(painter = painterResource(TrackletIcons.KebabMenu), contentDescription = null)
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = !expanded }) {
            DropdownMenuItem(
                text = { Text("Borrar") },
                onClick = {
                    expanded = !expanded
                    onClickDelete()
                }
            )
            DropdownMenuItem(
                text = { Text("Editar") },
                onClick = {
                    expanded = !expanded
                    onClickEdit()
                }
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    DetailsMenu(
        onClickDelete = {},
        onClickEdit = {}
    )
}