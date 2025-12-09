package com.vertonepa.tracklet.tickets.presentation.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.vertonepa.tracklet.navigation.graphs.details_graph.Details
import com.vertonepa.tracklet.tickets.domain.model.TicketDetailsModel
import com.vertonepa.tracklet.tickets.domain.usecases.DeleteTicketByIdUseCase
import com.vertonepa.tracklet.tickets.domain.usecases.GetTicketDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getTicketDetailsUseCase: GetTicketDetailsUseCase,
    private val deleteTicketByIdUseCase: DeleteTicketByIdUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _uiState = MutableStateFlow<DetailsUIState>(DetailsUIState.Loading)
    val uiState = _uiState.asStateFlow()

    private var detailsJob: Job? = null

    private val detailsRoute: Details = savedStateHandle.toRoute()
    private val ticketDetailsId = savedStateHandle.getStateFlow(
        key = "ticketIdKey",
        initialValue = detailsRoute.id
    ).value

    init {
        loadDetails()
    }

    fun loadDetails() {
        detailsJob = viewModelScope.launch {
            getTicketDetailsUseCase(id = ticketDetailsId).collectLatest {
                _uiState.value = DetailsUIState.Success(it)
            }
        }
    }


    fun onClickDelete(id: Int) {
        viewModelScope.launch {
            deleteTicketByIdUseCase(ticketId = id)
            onDelete()
        }
    }

    private fun onDelete() {
        detailsJob?.cancel()
        _uiState.value = DetailsUIState.Error
    }

}

sealed class DetailsUIState() {
    data object Loading : DetailsUIState()
    data class Success(val ticketDetails: TicketDetailsModel) : DetailsUIState()
    data object Error : DetailsUIState()
}