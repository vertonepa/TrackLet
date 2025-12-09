package com.vertonepa.tracklet.tickets.presentation.details

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vertonepa.tracklet.tickets.domain.model.TicketDetailsModel
import com.vertonepa.tracklet.tickets.presentation.details.components.DetailsMenu
import java.time.LocalDate

@Composable
fun TicketDetailsRoute(
    viewModel: DetailsViewModel = hiltViewModel(),
    navigateUp: () -> Unit,
    navigateToEditing: (String) -> Unit,
    navigateToTicketLogs: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    TicketDetailsScreen(
        uiState = uiState,
        onClickDelete = { viewModel.onClickDelete(it) },
        navigateToBack = { navigateUp() },
        navigateToEditing = { navigateToEditing(it) },
        navigateToTicketLogs = { navigateToTicketLogs(it) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TicketDetailsScreen(
    uiState: DetailsUIState,
    onClickDelete: (String) -> Unit,
    navigateToBack: () -> Unit,
    navigateToEditing: (String) -> Unit,
    navigateToTicketLogs: (String) -> Unit
) {

    when (uiState) {
        DetailsUIState.Loading -> {
            LoadingDetailsScreen()
        }

        is DetailsUIState.Success -> {
            val scrollState = rememberScrollState()
            val ticket = uiState.ticketDetails

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
                            DetailsMenu(
                                onClickDelete = {
                                    onClickDelete(ticket.ticketId)
                                },
                                onClickEdit = {
                                    navigateToEditing(ticket.ticketId)
                                })
                        }
                    )
                }) { paddingValues ->
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
                    Text(
                        modifier = Modifier
                            .padding(8.dp)
                            .clickable {
                                navigateToTicketLogs(ticket.ticketId)
                            }
                            .fillMaxWidth(),
                        text = "Consultar registros",
                        textAlign = TextAlign.End
                    )
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
                            label = { Text(text = ticket.paymentStatus) })
                        Spacer(modifier = Modifier.weight(2f))
                        Text(text = ticket.ticketPublishDate.toString())
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

        DetailsUIState.Error -> {
            LoadingDetailsScreen()
            navigateToBack()
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
        ticketTaskProgress = "Creado",
        ticketPublishDate = LocalDate.now()
    )

    TicketDetailsScreen(
        uiState = DetailsUIState.Success(ticket),
        onClickDelete = {},
        navigateToBack = {},
        navigateToEditing = {},
        navigateToTicketLogs = {},
    )
}