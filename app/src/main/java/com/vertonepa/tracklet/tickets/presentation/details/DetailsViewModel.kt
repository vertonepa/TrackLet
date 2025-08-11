package com.vertonepa.tracklet.tickets.presentation.details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.vertonepa.tracklet.navigation.Details
import com.vertonepa.tracklet.tickets.domain.model.TicketDetailsModel
import com.vertonepa.tracklet.tickets.domain.usecases.DeleteTicketByIdUseCase
import com.vertonepa.tracklet.tickets.domain.usecases.GetTicketDetailsUseCase
import com.vertonepa.tracklet.tickets.domain.usecases.UpdateTicketInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getTicketDetailsUseCase: GetTicketDetailsUseCase,
    private val updateTicketInfoUseCase: UpdateTicketInfoUseCase,
    private val deleteTicketByIdUseCase: DeleteTicketByIdUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _uiState = MutableStateFlow<DetailsUIState>(DetailsUIState.Loading)
    val uiState = _uiState.asStateFlow()

    var isEditingModeEnabled by mutableStateOf(false)
        private set

    private val detailsRoute: Details = savedStateHandle.toRoute()
    private val ticketDetailsId = savedStateHandle.getStateFlow(
        key = "ticketIdKey",
        initialValue = detailsRoute.id
    ).value

    init {
        loadDetails()
    }

    fun loadDetails() {
        viewModelScope.launch {
            val ticket = getTicketDetailsUseCase(id = ticketDetailsId)
            _uiState.value = DetailsUIState.Success(ticket)
        }
    }

    fun onUpdateTicketState(updateHeading: String? = null, updateDescription: String? = null) {
        viewModelScope.launch {
            updateTicketInfoUseCase(
                id = ticketDetailsId,
                heading = updateHeading,
                description = updateDescription
            )
        }
    }

    fun onDoubleClickToUpdate() {
        isEditingModeEnabled = !isEditingModeEnabled
    }

    fun onHeadingChanged() {

    }

    fun onClickDelete() {
        viewModelScope.launch {
            deleteTicketByIdUseCase(ticketId = ticketDetailsId)
        }
    }

}

sealed class DetailsUIState() {
    data object Loading : DetailsUIState()
    data class Success(val ticketDetails: TicketDetailsModel) : DetailsUIState()
}