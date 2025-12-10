package com.vertonepa.tracklet.tickets.presentation.creation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vertonepa.tracklet.tickets.domain.model.TicketCreationModel
import com.vertonepa.tracklet.tickets.domain.usecases.CreateNewTicketUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TicketCreationViewModel @Inject constructor(
    private val createNewTicketUseCase: CreateNewTicketUseCase,
) : ViewModel() {
    var heading by mutableStateOf("")
        private set

    var description by mutableStateOf("")
        private set

    fun onHeadingChanged(input: String) {
        heading = input
    }

    fun onDescriptionChanged(input: String) {
        description = input
    }

    fun onCreateTicket() {
        viewModelScope.launch {
            val newTicket = TicketCreationModel(
                ticketHeading = heading,
                ticketDescription = description
            )
            createNewTicketUseCase(newTicket)
        }
    }

    fun onClearFields() {
        heading = ""
        description = ""
    }

}