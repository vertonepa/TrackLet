package com.vertonepa.tracklet.tickets.presentation.creation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vertonepa.tracklet.tickets.domain.model.TicketCreationModel
import com.vertonepa.tracklet.tickets.domain.usecases.CreateNewTicketUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TicketCreationViewModel @Inject constructor(
    private val createNewTicketUseCase: CreateNewTicketUseCase,
) : ViewModel() {
    private val _heading = MutableStateFlow("")
    val heading: StateFlow<String> = _heading.asStateFlow()

    private val _description = MutableStateFlow("")
    val description: StateFlow<String> = _description.asStateFlow()

    val isButtonEnabled: StateFlow<Boolean> =
        combine(_heading, _description) { heading, description ->
            heading.isNotBlank() && description.isNotBlank()
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = false
        )

    fun onHeadingChanged(input: String) {
        _heading.value = input
    }

    fun onDescriptionChanged(input: String) {
        _description.value = input
    }

    fun onCreateTicket() {
        viewModelScope.launch {
            val newTicket = TicketCreationModel(
                ticketHeading = heading.value,
                ticketDescription = description.value
            )
            createNewTicketUseCase(newTicket)
        }
    }

    fun cleanData() {
        _heading.value = ""
        _description.value = ""
    }

}