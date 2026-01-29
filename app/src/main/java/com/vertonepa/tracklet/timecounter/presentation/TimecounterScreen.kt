package com.vertonepa.tracklet.timecounter.presentation

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.vertonepa.tracklet.core.ui.TrackletIcons
import com.vertonepa.tracklet.timecounter.presentation.service.TCServiceHelper
import com.vertonepa.tracklet.timecounter.presentation.utils.Time
import com.vertonepa.tracklet.timecounter.presentation.utils.TimecounterValues
import com.vertonepa.tracklet.timecounter.presentation.utils.formatToString

@Composable
fun TimecounterRoute(viewmodel: TimecounterViewmodel = hiltViewModel()) {
    val timeState = viewmodel.timeState.collectAsStateWithLifecycle()
    TimecounterScreen(
        time = timeState.value,
        onClickStart = {},
        onClickPause = {},
        onClickStop = {},
        onClickCancel = {}
    )
}

@Composable
fun TimecounterScreen(
    time: Time,
    onClickStart: () -> Unit = {},
    onClickPause: () -> Unit = {},
    onClickStop: () -> Unit = {},
    onClickCancel: () -> Unit = {}
    ) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            fontSize = 48.sp,
            text = time.formatToString()
        )
        Spacer(Modifier.padding(48.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Absolute.SpaceAround
        ) {
            IconButton(modifier = Modifier.size(48.dp), onClick = {}) {
                Icon(
                    modifier = Modifier.size(48.dp),
                    painter = painterResource(TrackletIcons.Stop),
                    contentDescription = "stop button"
                )
            }
            IconButton(modifier = Modifier.size(60.dp), onClick = {
                TCServiceHelper.triggerService(
                    context,
                    TimecounterValues.RESUME)
            }) {
                Icon(
                    modifier = Modifier.size(60.dp),
                    tint = Color.Red,
                    painter = painterResource(TrackletIcons.Start),
                    contentDescription = "start button"
                )
            }
            IconButton(modifier = Modifier.size(60.dp), onClick = {}) {
                Icon(
                    modifier = Modifier.size(48.dp),
                    painter = painterResource(TrackletIcons.Cancel),
                    contentDescription = "cancel button"
                )
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    TimecounterScreen(
        time = Time()
    )
}