package com.vertonepa.tracklet.tickets.presentation.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vertonepa.tracklet.tickets.domain.model.TicketDetailsModel
import com.vertonepa.tracklet.tickets.presentation.details.components.DetailsMenu

@Composable
fun TicketDetailsRoute(viewModel: DetailsViewModel = hiltViewModel(), navigateToBack: () -> Unit) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val isEditingModeEnabled = viewModel.isEditingModeEnabled

    TicketDetailsScreen(
        uiState = uiState,
        isEditingModeEnabled = isEditingModeEnabled,
        onDoubleClickToUpdate = viewModel::onDoubleClickToUpdate,
        onClickDelete = viewModel::onClickDelete,
        onUpdateTicketState = { heading, desc ->
            viewModel.onUpdateTicketState(
                heading, desc
            )
        },
        navigateToBack = { navigateToBack() }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TicketDetailsScreen(
    uiState: DetailsUIState,
    onClickDelete: () -> Unit,
    onUpdateTicketState: (String?, String?) -> Unit,
    isEditingModeEnabled: Boolean,
    onDoubleClickToUpdate: () -> Unit,
    navigateToBack: () -> Unit
) {

    when (uiState) {
        DetailsUIState.Loading -> {
            LoadingDetailsScreen()
        }

        is DetailsUIState.Success -> {
            val scrollState = rememberScrollState()

            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text("Detalles") },
                        navigationIcon = {
                            IconButton(onClick = { navigateToBack() }) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = null
                                )
                            }
                        },
                        actions = {
                            //TODO Meter dropdown acá
                            DetailsMenu(
                                onClickDelete = {
                                    onClickDelete()
                                    navigateToBack()
                                },
                                onClickEdit = {
                                })
                        }
                    )
                }) { paddingValues ->

                val ticket = uiState.ticketDetails

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Companion.White)
                        .padding(top = paddingValues.calculateTopPadding())
                        .padding(start = 4.dp, bottom = 4.dp, end = 4.dp)
                        .verticalScroll(scrollState)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 300.dp)
                            .aspectRatio(1f)
                            .fillMaxHeight(0.5f), contentAlignment = Alignment.Center
                    ) {
                        Text("Imagenes de la db")
                    }
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        SuggestionChip(
                            onClick = {},
                            label = { Text(text = ticket.ticketTaskProgress) },
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        SuggestionChip(
                            onClick = {},
                            label = { Text(text = ticket.paymentStatus ?: "") })
                        Spacer(modifier = Modifier.weight(2f))
                        Text("27.4.25")
                    }

                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = ticket.ticketHeading
                    )

                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = ticket.ticketDescription
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
    val ticket = TicketDetailsModel(
        ticketId = "-",
        ticketHeading = "encabezado",
        ticketDescription = "descripcion",
        paymentStatus = "Pagado",
        ticketTaskProgress = "Creado"
    )

    TicketDetailsScreen(
        uiState = DetailsUIState.Success(
            ticketDetails = ticket
        ),
        onClickDelete = {},
        onUpdateTicketState = { string1, string2 -> null },
        isEditingModeEnabled = false,
        onDoubleClickToUpdate = {},
        navigateToBack = {},
    )
}