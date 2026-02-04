package com.vertonepa.tracklet.timecounter.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vertonepa.tracklet.core.datatypes.LogEntry
import com.vertonepa.tracklet.core.ui.TrackletDialog
import com.vertonepa.tracklet.core.ui.TrackletIcons
import com.vertonepa.tracklet.tickets.presentation.ticket_list.LoadingScreen
import com.vertonepa.tracklet.timecounter.presentation.model.TimecounterInfo
import com.vertonepa.tracklet.timecounter.presentation.service.TCServiceHelper
import com.vertonepa.tracklet.timecounter.presentation.utils.Time
import com.vertonepa.tracklet.timecounter.presentation.utils.TimecounterState
import com.vertonepa.tracklet.timecounter.presentation.utils.TimecounterValues
import com.vertonepa.tracklet.timecounter.presentation.utils.formatToString

@Composable
fun TimecounterRoute(
    viewmodel: TimecounterViewmodel = hiltViewModel(),
    navigateUp: () -> Unit
) {
    val timeState = viewmodel.timeState.collectAsStateWithLifecycle()
    val timecounterState = viewmodel.timecounterState.collectAsStateWithLifecycle()
    val uiState = viewmodel.uiState.collectAsStateWithLifecycle()

    TimecounterScreen(
        time = timeState.value,
        timecounter = timecounterState.value,
        uiState = uiState.value,
        navigateUp = navigateUp
    )
}

@Composable
fun TimecounterScreen(
    time: Time,
    timecounter: TimecounterState,
    uiState: TimecounterUiState,
    navigateUp: () -> Unit
) {
    val context = LocalContext.current
    var showStopDialog by rememberSaveable { mutableStateOf(false) }
    var showCancelDialog by rememberSaveable { mutableStateOf(false) }

    when (uiState) {
        is TimecounterUiState.Loading -> LoadingScreen()
        is TimecounterUiState.Success -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .background(Color.White),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    fontSize = 48.sp, text = time.formatToString()
                )
                Spacer(Modifier.padding(48.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Absolute.SpaceAround
                ) {
                    IconButton(modifier = Modifier.size(48.dp), onClick = {
                        showStopDialog = true
                        TCServiceHelper.triggerService(context, TimecounterValues.PAUSE)
                    }) {
                        Icon(
                            modifier = Modifier.size(48.dp),
                            painter = painterResource(TrackletIcons.Stop),
                            contentDescription = "stop button"
                        )
                    }
                    IconButton(modifier = Modifier.size(60.dp), onClick = {
                        if (timecounter == TimecounterState.NOT_INITIALIZED) {
                            TCServiceHelper.triggerService(context, TimecounterValues.START)
                        }
                        if (timecounter == TimecounterState.RESUMED) {
                            TCServiceHelper.triggerService(context, TimecounterValues.PAUSE)
                        }
                        if (timecounter == TimecounterState.PAUSED) {
                            TCServiceHelper.triggerService(context, TimecounterValues.RESUME)
                        }
                    }) {
                        Icon(
                            modifier = Modifier.size(60.dp),
                            tint = if (timecounter == TimecounterState.NOT_INITIALIZED) Color.Red else LocalContentColor.current,
                            painter = when (timecounter) {
                                TimecounterState.NOT_INITIALIZED -> painterResource(
                                    TrackletIcons.Start
                                )

                                TimecounterState.PAUSED -> painterResource(
                                    TrackletIcons.Resume
                                )

                                TimecounterState.RESUMED -> painterResource(
                                    TrackletIcons.Pause
                                )

                                else -> {
                                    painterResource(TrackletIcons.Resume)
                                }
                            },
                            contentDescription = "button change dynamically between Start, Resume and Pause"
                        )
                    }
                    IconButton(
                        modifier = Modifier.size(60.dp),
                        onClick = {
                            TCServiceHelper.triggerService(context, TimecounterValues.PAUSE)
                            showCancelDialog = true
                        }
                    ) {
                        Icon(
                            modifier = Modifier.size(48.dp),
                            painter = painterResource(TrackletIcons.Cancel),
                            contentDescription = "cancel button"
                        )
                    }
                }
            }

            AnimatedVisibility(
                visible = showStopDialog,
                enter = scaleIn(),
                exit = scaleOut()
            ) {
                TrackletDialog(
                    onDismissRequest = { showStopDialog = false },
                    confirmButton = {
                        showStopDialog = false
                        TCServiceHelper.triggerService(
                            context, TimecounterValues.STOP
                        )
                        navigateUp()
                    },
                    dismissButton = { showStopDialog = false },
                    text = "Presione confirmar si quiere registrar el tiempo trabajado en este ticket"
                )
            }

            AnimatedVisibility(
                visible = showCancelDialog,
                enter = scaleIn(),
                exit = scaleOut()
            ) {
                TrackletDialog(
                    onDismissRequest = { showCancelDialog = false },
                    confirmButton = {
                        showCancelDialog = false
                        TCServiceHelper.triggerService(
                            context, TimecounterValues.CANCEL
                        )
                        navigateUp()
                    },
                    dismissButton = { showCancelDialog = false },
                    text = "Al confirmar se eliminará todo el progreso obtenido hasta ahora y no se recuperará. Confirme para seguir adelante la operación"
                )
            }


        }


    }
}

@Preview
@Composable
private fun Preview() {
    TimecounterScreen(
        time = Time(),
        timecounter = TimecounterState.NOT_INITIALIZED,
        uiState = TimecounterUiState.Success(TimecounterInfo(1, 0, LogEntry.TIMER, 0)),
        navigateUp = {}
    )
}