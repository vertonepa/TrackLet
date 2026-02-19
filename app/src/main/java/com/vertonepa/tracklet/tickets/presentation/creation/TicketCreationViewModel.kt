package com.vertonepa.tracklet.tickets.presentation.creation

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vertonepa.tracklet.tickets.domain.model.TicketCreationModel
import com.vertonepa.tracklet.tickets.domain.repository.TicketsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TicketCreationViewModel @Inject constructor(
    private val repository: TicketsRepository,
) : ViewModel() {
    var heading by mutableStateOf("")
        private set

    var description by mutableStateOf("")
        private set

    var pictures by mutableStateOf(emptyList<Uri>())
        private set

    fun onAddImage(uris: List<Uri>) {
        val filteredPictures = uris.filter { uri ->
            !pictures.contains(uri)
        }
        pictures += filteredPictures
    }

    fun onRemoveImage(uri: Uri) {
        pictures -= uri
    }

    fun onHeadingChanged(input: String) {
        heading = input
    }

    fun onDescriptionChanged(input: String) {
        description = input
    }

    fun onCreateTicket() {
        val pics = pictures.map { it.toString() }

        viewModelScope.launch {
            val newTicket = TicketCreationModel(
                heading = heading,
                description = description,
                pictures = pics
            )
            repository.createNewTicket(newTicket)
        }
    }
}