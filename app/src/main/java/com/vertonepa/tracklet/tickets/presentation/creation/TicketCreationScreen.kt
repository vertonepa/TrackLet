package com.vertonepa.tracklet.tickets.presentation.creation

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun TicketCreationRoute(
    viewModel: TicketCreationViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
    navigateToTicketListScreen: () -> Unit,
) {
    val heading by viewModel.heading.collectAsStateWithLifecycle()
    val description by viewModel.description.collectAsState()
    val isButtonEnabled by viewModel.isButtonEnabled.collectAsState()

    TicketCreationScreen(
        navigateBack = navigateBack,
        navigateToTicketListScreen = navigateToTicketListScreen,
        headingState = heading,
        descriptionState = description,
        isButtonEnabled = isButtonEnabled,
        onHeadingChanged = viewModel::onHeadingChanged,
        onDescriptionChanged = viewModel::onDescriptionChanged,
        onCreateTicket = viewModel::onCreateTicket,
        cleanData = viewModel::cleanData,
    )
}

@Composable
fun TicketCreationScreen(
    navigateBack: () -> Unit,
    navigateToTicketListScreen: () -> Unit,
    headingState: String,
    descriptionState: String,
    isButtonEnabled: Boolean,
    onHeadingChanged: (String) -> Unit,
    onDescriptionChanged: (String) -> Unit,
    onCreateTicket: () -> Unit,
    cleanData: () -> Unit,
) {
    val scrollState = rememberScrollState()
    var showDismissDialog by remember { mutableStateOf(false) }


    Column(
        Modifier
            .fillMaxSize()
            .background(Color.Companion.White)
            .padding(16.dp)
            .scrollable(state = scrollState, orientation = Orientation.Vertical),
    ) {
        TopBar(
            modifier = Modifier.padding(bottom = 8.dp),
            onClickClose = {
                if (headingState.isNotEmpty() || descriptionState.isNotEmpty())
                    showDismissDialog = true
                else {
                    navigateBack()
                    cleanData()
                }
            },
            onCreateTicket = { onCreateTicket() },
            isButtonEnabled = { isButtonEnabled },
            cleanData = { cleanData() },
            navigateToTicketListScreen = { navigateToTicketListScreen() }
        )

        OutlinedTextField(
            value = headingState,
            onValueChange = { onHeadingChanged(it) },
            modifier = Modifier
                .padding(bottom = 20.dp)
                .fillMaxWidth(),
            placeholder = { Text("Título") },
            maxLines = 2,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
            shape = RoundedCornerShape(size = 4.dp)
        )
        OutlinedTextField(
            value = descriptionState,
            onValueChange = { onDescriptionChanged(it) },
            modifier = Modifier
                .padding(bottom = 20.dp)
                .fillMaxWidth()
                .heightIn(min = 150.dp, max = 250.dp),
            placeholder = { Text("Descripción") },
            maxLines = 100,
            shape = RoundedCornerShape(size = 4.dp)
        )
        Spacer(modifier = Modifier.padding(4.dp))

        Text(text = "Adjuntar imagenes")
    }


    if (showDismissDialog) {
        AlertDialog(
            onDismissRequest = { showDismissDialog = false },
            confirmButton = {
                TextButton(onClick = {
                    navigateBack()
                    cleanData()
                }) {
                    Text(text = "Confirmar")
                }
            },
            title = { Text(text = "Descartar cambios") },
            text = { Text(text = "Estás a punto de descartar el progreso, si querés seguir adelante presioná \"Confirmar\".") }
        )
    }

}


@Composable
private fun TopBar(
    modifier: Modifier = Modifier,
    onClickClose: () -> Unit,
    onCreateTicket: () -> Unit,
    isButtonEnabled: () -> Boolean,
    cleanData: () -> Unit,
    navigateToTicketListScreen: () -> Unit,
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = { onClickClose() }) {
            Icon(imageVector = Icons.Default.Close, contentDescription = null)
        }
        Text(
            modifier = Modifier.weight(1f),
            text = "Creación de Ticket",
            textAlign = TextAlign.Center
        )

        Button(
            onClick = {
                onCreateTicket()
                navigateToTicketListScreen()
                cleanData()
            },
            enabled = isButtonEnabled(),
        ) {
            Text(text = "Crear")
        }
    }
}


@Preview
@Composable
private fun Preview() {
    TicketCreationScreen(
        headingState = "",
        descriptionState = "",
        navigateBack = {},
        navigateToTicketListScreen = {},
        isButtonEnabled = false,
        onHeadingChanged = {},
        onDescriptionChanged = {},
        onCreateTicket = {},
        cleanData = {}
    )
}