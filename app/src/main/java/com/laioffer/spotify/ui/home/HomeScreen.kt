package com.laioffer.spotify.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.laioffer.spotify.R
import com.laioffer.spotify.datamodel.Album
import com.laioffer.spotify.datamodel.Section

@Composable
fun HomeScreen(viewModel: HomeViewModel, onTap: (Album) -> Unit) {

    // by is delegate, so we don't need to call uiState.value
    val uiState by viewModel.uiState.collectAsState()
    HomeScreenContent(uiState = uiState, onTap)
}

@Composable
private fun HomeScreenContent(uiState: HomeUiState, onTap: (Album) -> Unit) {
    LazyColumn(modifier = Modifier.padding(16.dp)) {
        item {
            HomeScreenHeader()
        }

        when {
            uiState.isLoading -> {
                item {
                    LoadingSection(text = stringResource(id = R.string.screen_loading))
                }
            }
            else -> {
                items(uiState.feed) { item ->
                    AlbumSection(section = item ,onTap)
                }

            }
        }
    }
}

@Composable
private fun AlbumSection(section: Section, onTap: (Album) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = section.sectionTitle,
            style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.Bold),
            color = Color.White
        )

        LazyRow(
            modifier = Modifier.padding(top = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(section.albums) { item ->
                AlbumCover(album = item, onTap)
            }
        }
    }
}

@Composable
private fun AlbumCover(album: Album, onTap: (Album) -> Unit) {
    Column(modifier = Modifier.clickable {
        onTap(album)
    }) {
        Box(modifier = Modifier.size(160.dp)) {

            // need add agent to avoid 403 error
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(album.cover)
                    .addHeader("User-Agent", "Mozilla/5.0 (Android Emulator) Chrome/120.0").build(),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )
            Text(
                text = album.name,
                color = Color.White,
                modifier = Modifier
                    .padding(bottom = 4.dp, start = 2.dp)
                    .align(Alignment.BottomStart),
            )
        }

        Text(
            text = album.artists,
            modifier = Modifier.padding(top = 4.dp),
            style = MaterialTheme.typography.body2.copy(fontWeight = FontWeight.Bold),
            color = Color.LightGray,
        )
    }
}

@Composable
private fun LoadingSection(text: String) {
    Row(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(
            text = text,
            style = MaterialTheme.typography.body2,
            color = Color.White
        )
    }
}

@Composable
fun HomeScreenHeader() {
    Column {
        Text(
//            modifier = Modifier.padding(bottom = 16.dp),
            text = stringResource(id = R.string.menu_home),
            style = MaterialTheme.typography.h4,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
}

