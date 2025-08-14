package com.vertonepa.tracklet.tickets.presentation.editing

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vertonepa.tracklet.tickets.domain.model.TicketDetailsModel
import java.time.LocalDate

@Composable
fun TicketEditingRoute(
    viewModel: EditingViewModel = hiltViewModel(),
    navigateToBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val headingState = viewModel.heading
    val descriptionState = viewModel.description

    TicketEditingScreen(
        uiState = uiState,
        headingState = headingState,
        descriptionState = descriptionState,
        navigateToBack = { navigateToBack() },
        onHeadingChanged = { viewModel.onHeadingChanged(it) },
        onDescriptionChanged = { viewModel.onDescriptionChanged(it) },
        updateTicket = { heading, description ->
            viewModel.onUpdateTicketState(heading, description)
        }

    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TicketEditingScreen(
    uiState: EditingUIState,
    headingState: String,
    descriptionState: String,
    navigateToBack: () -> Unit,
    onHeadingChanged: (String) -> Unit,
    onDescriptionChanged: (String) -> Unit,
    updateTicket: (String, String) -> Unit,
) {
    when (uiState) {
        EditingUIState.Loading -> {
            LoadingDetailsScreen()
        }

        is EditingUIState.Success -> {
            Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
                TopAppBar(
                    title = { Text("Editar") },
                    navigationIcon = {
                        IconButton(onClick = { navigateToBack() }) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = null
                            )
                        }
                    },
                    actions = {
                        Button(onClick = {
                            updateTicket(headingState, descriptionState)
                            navigateToBack()
                        }) {
                            Text("Aceptar")
                        }
                    }
                )
            }) { paddingValues ->
                Column(
                    modifier = Modifier
                        .background(Color.Companion.White)
                        .fillMaxSize()
                        .padding(top = paddingValues.calculateTopPadding())
                ) {
                    BasicTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = headingState,
                        onValueChange = { onHeadingChanged(it) })
                    BasicTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = descriptionState,
                        onValueChange = { onDescriptionChanged(it) }
                    )

                }
            }
        }
    }
}

@Composable
private fun LoadingDetailsScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Companion.Gray),
        contentAlignment = Alignment.Center
    ) {
        LinearProgressIndicator()
    }
}

@Preview
@Composable
private fun Preview() {
    TicketEditingScreen(
        uiState = EditingUIState.Success(
            ticketDetails = TicketDetailsModel(
                ticketId = "",
                ticketHeading = "Heading",
                ticketDescription = "Description",
                paymentStatus = "Payment",
                ticketTaskProgress = "En progreso",
                ticketPublishDate = LocalDate.now()
            )
        ),
        navigateToBack = {},
        onHeadingChanged = {},
        onDescriptionChanged = {},
        headingState = "",
        descriptionState = "",
        updateTicket = { str1, str2 -> }
    )
}