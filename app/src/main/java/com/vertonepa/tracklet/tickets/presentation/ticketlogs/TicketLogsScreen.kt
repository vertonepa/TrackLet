package com.vertonepa.tracklet.tickets.presentation.ticketlogs

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vertonepa.tracklet.core.ui.BottomSheetOptions
import com.vertonepa.tracklet.core.ui.TrackletBottomSheet
import com.vertonepa.tracklet.tickets.domain.model.TicketLog
import com.vertonepa.tracklet.tickets.domain.model.enums.PaymentState
import com.vertonepa.tracklet.tickets.presentation.ticket_list.LoadingScreen
import com.vertonepa.tracklet.tickets.presentation.ticketlogs.components.DeleteDialog
import com.vertonepa.tracklet.tickets.presentation.ticketlogs.components.MultiSelectionTopBar
import com.vertonepa.tracklet.tickets.presentation.ticketlogs.components.ProductTotalsCard
import com.vertonepa.tracklet.tickets.presentation.ticketlogs.components.TicketLogDialog
import com.vertonepa.tracklet.tickets.presentation.ticketlogs.components.TicketLogItem
import java.time.LocalDate
import kotlin.random.Random


@Composable
fun TicketLogsRoute(
    viewModel: TicketLogsViewModel = hiltViewModel(),
    navigateUp: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val totalsState by viewModel.totalsState.collectAsStateWithLifecycle()
    val selectedItems = viewModel.selectedItems
    val isMultiSelectionEnabled = viewModel.isMultiSelectionEnabled


    TicketLogsScreen(
        uiState = uiState,
        totalsState = totalsState,
        selectedItems = selectedItems,
        isMultiSelectionEnabled = isMultiSelectionEnabled,
        generateLog = { paymentState, date, quantity, color ->
            viewModel.onGenerateLog(
                paymentState,
                date,
                quantity,
                color
            )
        },
        navigateToBack = navigateUp,
        onItemClick = { viewModel.onItemClick(it) },
        onItemLongPress = { viewModel.onItemLongPress(it) },
        onUndoLongPress = { viewModel.onUndoLongPress() },
        onDeleteLog = { viewModel.onDeleteLog(it) },
        onMarkLog = { viewModel.onMarkLog(it) },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TicketLogsScreen(
    uiState: TicketLogsUIState,
    totalsState: UiTotals,
    selectedItems: Set<Int>,
    isMultiSelectionEnabled: Boolean,
    generateLog: (PaymentState, LocalDate, Int, String) -> Unit,
    navigateToBack: () -> Unit,
    onItemClick: (Int) -> Unit,
    onItemLongPress: (Int) -> Unit,
    onUndoLongPress: () -> Unit,
    onDeleteLog: (Set<Int>) -> Unit,
    onMarkLog: (Int) -> Unit
) {
    var showCreateDialog by remember { mutableStateOf(false) }
    var isSheetOpen by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            if (isMultiSelectionEnabled) {
                if (selectedItems.isEmpty()) {
                    onUndoLongPress()
                }

                MultiSelectionTopBar(
                    itemsSelected = selectedItems.size,
                    onDelete = { showDeleteDialog = true },
                    onCancelActions = onUndoLongPress
                )
            } else {
                TopAppBar(
                    title = { Text("Registros") },
                    navigationIcon = {
                        IconButton(onClick = navigateToBack) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = null
                            )
                        }
                    }
                )
            }
        },
        floatingActionButton = {
            if (uiState is TicketLogsUIState.Success) {
                FloatingActionButton(
                    onClick = {
                        showCreateDialog = true
                        onUndoLongPress()
                    }
                ) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = null,
                    )
                }
            }
        }
    ) { paddingValues ->

        when (uiState) {
            TicketLogsUIState.Empty -> {
                EmptyScreenState(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    addLog = { showCreateDialog = true },
                )
            }

            TicketLogsUIState.Loading -> LoadingScreen()
            is TicketLogsUIState.Success -> {
                val haptic = LocalHapticFeedback.current

                LazyColumn(modifier = Modifier.padding(paddingValues)) {
                    item {
                        ProductTotalsCard(
                            totals = totalsState,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        )
                    }

                    items(items = uiState.ticketLogs, key = { it.logId }) {
                        TicketLogItem(
                            date = it.date,
                            isSelected = it.logId in selectedItems,
                            paymentState = it.paymentState,
                            productColor = it.color,
                            productQuantity = it.quantity,
                            hasMark = it.hasMark,
                            haptic = { haptic.performHapticFeedback(HapticFeedbackType.LongPress) },
                            shouldShowBottomSheet = {
                                isSheetOpen = true
                                onMarkLog(it.logId)
                            },
                            onClickCard = { onItemClick(it.logId) },
                            onLongClickCard = { onItemLongPress(it.logId) }
                        )
                    }
                }

                if (isSheetOpen) {
                    val options: List<BottomSheetOptions> = listOf(
                        BottomSheetOptions(
                            imageVector = Icons.Default.Delete,
                            title = "Eliminar",
                            action = {
                                isSheetOpen = false
                                onUndoLongPress()
                                onDeleteLog(selectedItems)
                            }
                        )
                    )

                    TrackletBottomSheet(
                        onDismiss = { isSheetOpen = false },
                        options = options
                    )
                }

                if (showDeleteDialog) {
                    DeleteDialog(
                        onDismiss = { showDeleteDialog = false },
                        onDelete = {
                            showDeleteDialog = false
                            onDeleteLog(selectedItems)
                            onUndoLongPress()
                        }
                    )
                }
            }
        }
    }

    if (showCreateDialog) {
        var quantityState by remember { mutableStateOf("") }
        var colorState by remember { mutableStateOf("") }

        TicketLogDialog(
            onDismiss = { showCreateDialog = false },
            quantity = quantityState,
            color = colorState,
            onConfirm = {
                showCreateDialog = false
                //TODO("LocalDate.now() debe ser cambiado por un formateador común a todas las screens ")
                generateLog(
                    PaymentState.OWES,
                    LocalDate.now(),
                    quantityState.toIntOrNull() ?: 0,
                    colorState.ifEmpty { "-" }
                )
            },
            onQuantityChange = { quantityState = it },
            onColorChange = { colorState = it }
        )
    }


}


@Preview
@Composable
private fun Preview() {
    val id = Random.nextInt()
    TicketLogsScreen(
        uiState =
            TicketLogsUIState.Success(
                listOf(
                    TicketLog(
                        logId = 1,
                        ticketId = id,
                        date = LocalDate.now(),
                        quantity = 14,
                        color = "Rojo"
                    ),
                    TicketLog(
                        logId = 2,
                        ticketId = id,
                        date = LocalDate.now(),
                        quantity = 30,
                        color = "Verde claro"
                    ),
                    TicketLog(
                        logId = 3,
                        ticketId = id,
                        date = LocalDate.now(),
                        quantity = 10,
                        color = "Azul oscuro"
                    ),
                )
            ),
        totalsState = UiTotals(),
        selectedItems = setOf(),
        isMultiSelectionEnabled = false,
        generateLog = { _, _, _, _ -> },
        navigateToBack = {},
        onItemClick = {},
        onItemLongPress = { },
        onUndoLongPress = {},
        onDeleteLog = {},
        onMarkLog = {},
    )
}