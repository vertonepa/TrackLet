package com.vertonepa.tracklet.core.ui

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil.compose.AsyncImage
import com.vertonepa.tracklet.R

@Composable
fun PicturesPicker(
    modifier: Modifier = Modifier,
    pictures: List<Uri> = emptyList(),
    onAddElement: () -> Unit,
    onRemoveElement: (Uri) -> Unit
) {

    LazyVerticalGrid(
        columns = GridCells.Fixed(4),
        horizontalArrangement = Arrangement.spacedBy(1.dp),
        verticalArrangement = Arrangement.spacedBy(1.dp),
        modifier = modifier
    ) {
        item {
            AddButton {
                onAddElement()
            }
        }

        items(items = pictures) {
            Thumbnail(it) {
                onRemoveElement(it)
            }
        }
    }
}

@Composable
private fun AddButton(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .background(
                color = Color(0xF080DFFF),
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(TrackletIcons.Add),
            contentDescription = "Add pictures button",
            modifier = Modifier.fillMaxSize(),
            colorFilter = ColorFilter.tint(Color.White)
        )
    }
}

@Composable
private fun Thumbnail(uri: Uri, onClick: () -> Unit) {
    Box(contentAlignment = Alignment.TopEnd) {
        AsyncImage(
            model = uri,
            contentDescription = "image",
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f),
            contentScale = ContentScale.Crop,
            error = painterResource(R.drawable.img_placeholder)
        )
        Icon(
            modifier = Modifier.clickable { onClick() },
            painter = painterResource(TrackletIcons.Close),
            contentDescription = "remove image button"
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    val mockImages = remember {
        (1..15).map { id ->
            "https://picsum.photos/id/${id + 10}/200/200".toUri()
        }
    }

    PicturesPicker(
        Modifier.fillMaxSize(),
        mockImages,
        onAddElement = {},
        onRemoveElement = {}
    )
}