package com.example.artspace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

// Data Model (Now includes image resource)
data class Artwork(
    val title: String,
    val artist: String,
    val imageRes: Int
)

// Business Logic
fun getNextIndex(current: Int, size: Int): Int {
    return if (current < size - 1) current + 1 else 0
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    ArtSpaceScreen()
                }
            }
        }
    }
}

@Composable
fun ArtSpaceScreen() {

    val artworks = listOf(
        Artwork("Sunset", "Artist A", R.drawable.sunset),
        Artwork("Mountain", "Artist B", R.drawable.mountain),
        Artwork("Ocean", "Artist C", R.drawable.ocean)
    )

    var currentIndex by remember { mutableStateOf(0) }
    var favourites by remember { mutableStateOf(listOf<Artwork>()) }

    val currentArtwork = artworks[currentIndex]
    val isFavourite = favourites.contains(currentArtwork)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        // 🔹 Artwork Image (Centered)
        Image(
            painter = painterResource(id = currentArtwork.imageRes),
            contentDescription = currentArtwork.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(250.dp)
                .clip(RoundedCornerShape(16.dp))
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = currentArtwork.title,
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "by ${currentArtwork.artist}",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(32.dp))

        Row {

            Button(
                onClick = {
                    currentIndex = getNextIndex(currentIndex, artworks.size)
                }
            ) {
                Text("Next")
            }

            Spacer(modifier = Modifier.width(16.dp))

            Button(
                onClick = {
                    favourites = if (isFavourite) {
                        favourites - currentArtwork
                    } else {
                        favourites + currentArtwork
                    }
                }
            ) {
                Text(
                    if (isFavourite) "Unfavourite"
                    else "Favourite"
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Favourites Count: ${favourites.size}",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ArtSpacePreview() {
    MaterialTheme {
        ArtSpaceScreen()
    }
}
