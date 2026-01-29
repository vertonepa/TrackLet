package com.vertonepa.tracklet.tickets.presentation.edit

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.vertonepa.tracklet.navigation.graphs.EditTicketDestination
import com.vertonepa.tracklet.tickets.domain.model.TicketDetailsModel
import com.vertonepa.tracklet.tickets.domain.usecases.GetTicketDetailsUseCase
import com.vertonepa.tracklet.tickets.domain.usecases.UpdateTicketInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditTicketViewModel @Inject constructor(
    private val updateTicketInfoUseCase: UpdateTicketInfoUseCase,
    private val getTicketDetailsUseCase: GetTicketDetailsUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _uiState = MutableStateFlow<EditingUIState>(EditingUIState.Loading)
    val uiState = _uiState.asStateFlow()

    var heading by mutableStateOf("")
        private set

    var description by mutableStateOf("")
        private set

    private val editTicketRoute: EditTicketDestination = savedStateHandle.toRoute()
    private val ticketId = savedStateHandle.getStateFlow(
        key = "ticketIdKey",
        initialValue = editTicketRoute.id
    ).value

    init {
        loadDetails()
    }

    fun loadDetails() {
        viewModelScope.launch {
            getTicketDetailsUseCase(id = ticketId).collectLatest { ticket ->
                _uiState.value = EditingUIState.Success(ticket)
                heading = ticket.ticketHeading
                description = ticket.ticketDescription
            }
        }
    }

    fun onHeadingChanged(input: String) {
        heading = input
    }

    fun onDescriptionChanged(input: String) {
        description = input
    }


    fun onUpdateTicketState(updateHeading: String? = null, updateDescription: String? = null) {
        viewModelScope.launch {
            updateTicketInfoUseCase(
                id = ticketId,
                heading = updateHeading,
                description = updateDescription
            )
        }
    }

}

sealed class EditingUIState() {
    data object Loading : EditingUIState()
    data class Success(val ticketDetails: TicketDetailsModel) : EditingUIState()
}