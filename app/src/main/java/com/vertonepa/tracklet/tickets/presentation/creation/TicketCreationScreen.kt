package com.vertonepa.tracklet.tickets.presentation.creation

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.vertonepa.tracklet.core.ui.PicturesPicker

@Composable
fun TicketCreationRoute(
    viewModel: TicketCreationViewModel = hiltViewModel(),
    backToMain: () -> Unit
) {

    TicketCreationScreen(
        backToMain = backToMain,
        headingState = viewModel.heading,
        descriptionState = viewModel.description,
        pictures = viewModel.pictures,
        onAddImages = viewModel::onAddImage,
        onRemoveImages = viewModel::onRemoveImage,
        onHeadingChanged = viewModel::onHeadingChanged,
        onDescriptionChanged = viewModel::onDescriptionChanged,
        onCreateTicket = viewModel::onCreateTicket
    )
}

@Composable
fun TicketCreationScreen(
    headingState: String,
    descriptionState: String,
    pictures: List<Uri>,
    backToMain: () -> Unit,
    onAddImages: (List<Uri>) -> Unit,
    onRemoveImages: (Uri) -> Unit,
    onHeadingChanged: (String) -> Unit,
    onDescriptionChanged: (String) -> Unit,
    onCreateTicket: () -> Unit,
) {
    val contentResolver = LocalContext.current.contentResolver
    val scrollState = rememberScrollState()
    var showDismissDialog by remember { mutableStateOf(false) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(),
        onResult = { uris ->
            uris.forEach { uri ->
                runCatching {
                    contentResolver.takePersistableUriPermission(
                        uri, Intent.FLAG_GRANT_READ_URI_PERMISSION
                    )
                }
            }
            onAddImages(uris)
        }
    )

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
                    backToMain()
                }
            },
            onCreateTicket = onCreateTicket,
            headingState = headingState,
            descriptionState = descriptionState,
            navigateToTicketListScreen = { backToMain() }
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
            )
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
        )
        Spacer(modifier = Modifier.padding(4.dp))

        Text(text = "Adjuntar imagenes")
        Spacer(modifier = Modifier.padding(4.dp))
        PicturesPicker(
            pictures = pictures, onAddElement = {
                launcher.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                )
            },
            onRemoveElement = {
                onRemoveImages(it)
            }
        )
    }


    if (showDismissDialog) {
        AlertDialog(
            onDismissRequest = { showDismissDialog = false },
            confirmButton = {
                TextButton(onClick = {
                    backToMain()
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
    headingState: String,
    descriptionState: String,
    navigateToTicketListScreen: () -> Unit,
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = onClickClose) {
            Icon(imageVector = Icons.Default.Close, contentDescription = null)
        }
        Text(
            modifier = Modifier.weight(1f),
            text = "Crear Ticket",
            textAlign = TextAlign.Center
        )

        Button(
            onClick = {
                onCreateTicket()
                navigateToTicketListScreen()
            },
            enabled = headingState.isNotBlank() && descriptionState.isNotBlank(),
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
        pictures = emptyList(),
        onAddImages = {},
        onRemoveImages = {},
        backToMain = {},
        onHeadingChanged = {},
        onDescriptionChanged = {},
        onCreateTicket = {}
    )
}