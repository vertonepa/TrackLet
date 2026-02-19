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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vertonepa.tracklet.core.ui.BlockActionDialog
import com.vertonepa.tracklet.core.ui.TrackletDialog
import com.vertonepa.tracklet.core.ui.TrackletIcons
import com.vertonepa.tracklet.tickets.domain.model.TicketDetailsModel
import com.vertonepa.tracklet.tickets.presentation.details.components.DetailsMenu
import java.time.LocalDate

@Composable
fun TicketDetailsRoute(
    viewModel: DetailsViewModel = hiltViewModel(),
    navigateUp: () -> Unit,
    navigateToEditing: (Int) -> Unit,
    navigateToTicketLogs: (Int) -> Unit,
    navigateToTimecounter: (Int) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val isTimecounterActive by viewModel.isTimecounterActive.collectAsStateWithLifecycle()
    val timecounterId by viewModel.timecounterId.collectAsStateWithLifecycle()

    TicketDetailsScreen(
        uiState = uiState,
        timecounterId = timecounterId,
        isTimecounterActive = isTimecounterActive,
        onClickDelete = { viewModel.onClickDelete(it) },
        onInitNewTimecounter = { viewModel.onClickInitNewTimecounter(it) },
        navigateToBack = { navigateUp() },
        navigateToEditing = { navigateToEditing(it) },
        navigateToTicketLogs = { navigateToTicketLogs(it) },
        navigateToTimecounter = { navigateToTimecounter(it) })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TicketDetailsScreen(
    uiState: DetailsUIState,
    timecounterId: Int,
    isTimecounterActive: Boolean,
    onClickDelete: (Int) -> Unit,
    onInitNewTimecounter: (Int) -> Unit,
    navigateToBack: () -> Unit,
    navigateToEditing: (Int) -> Unit,
    navigateToTicketLogs: (Int) -> Unit,
    navigateToTimecounter: (Int) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }

    when (uiState) {
        DetailsUIState.Loading -> {
            LoadingDetailsScreen()
        }

        is DetailsUIState.Success -> {
            val scrollState = rememberScrollState()
            val ticket = uiState.ticketDetails

            Scaffold(
                topBar = {
                    TopAppBar(title = { }, navigationIcon = {
                        IconButton(onClick = { navigateToBack() }) {
                            Icon(
                                painter = painterResource(TrackletIcons.Back),
                                contentDescription = null
                            )
                        }
                    }, actions = {
                        when {
                            isTimecounterActive -> {
                                IconButton(onClick = {
                                    navigateToTimecounter(timecounterId)
                                }) {
                                    Icon(
                                        painter = painterResource(TrackletIcons.Timecounter),
                                        tint = Color.Cyan,
                                        contentDescription = null
                                    )
                                }
                            }

                            !isTimecounterActive -> {
                                IconButton(onClick = { showDialog = true }) {
                                    Icon(
                                        painter = painterResource(TrackletIcons.Timecounter),
                                        contentDescription = null
                                    )
                                }
                            }
                        }
                        DetailsMenu(onClickDelete = {
                            onClickDelete(ticket.ticketId)
                        }, onClickEdit = {
                            navigateToEditing(ticket.ticketId)
                        })
                    })
                }) { paddingValues ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Companion.White)
                        .padding(paddingValues)
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
                        textAlign = TextAlign.End)
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
                        SuggestionChip(onClick = {}, label = { Text(text = ticket.paymentState) })
                        Spacer(modifier = Modifier.weight(2f))
                        Text(text = ticket.ticketPublishDate.toString())
                    }

                    Text(
                        modifier = Modifier.fillMaxWidth(), text = ticket.ticketHeading
                    )

                    Text(
                        modifier = Modifier.fillMaxWidth(), text = ticket.ticketDescription
                    )
                }
            }

            if (!isTimecounterActive && showDialog) {
                TrackletDialog(
                    onDismissRequest = { showDialog = false },
                    confirmButton = {
                        onInitNewTimecounter(ticket.ticketId)
                        showDialog = false


                        //mostrar snackbar de creación de Timecounter
                    },
                    dismissButton = { showDialog = false },
                    text = "No hay contadores activos actualmente, presiona confirmar para iniciar uno nuevo"
                )
            }

            if (isTimecounterActive && showDialog) {
                BlockActionDialog(
                    onDismissRequest = { showDialog = false },
                    confirmButton = { showDialog = false },
                    text = "Hay un contador activo, para iniciar uno nuevo debe finalizar con el anterior"
                )
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
        ticketId = 0,
        ticketHeading = "encabezado",
        ticketDescription = "descripcion",
        paymentState = "Pagado",
        ticketTaskProgress = "Creado",
        ticketPublishDate = LocalDate.now(),
        pictures = emptyList()
    )

    TicketDetailsScreen(
        uiState = DetailsUIState.Success(ticket),
        timecounterId = 0,
        isTimecounterActive = false,
        onClickDelete = {},
        onInitNewTimecounter = {},
        navigateToBack = {},
        navigateToEditing = {},
        navigateToTicketLogs = {},
        navigateToTimecounter = {}
    )
}