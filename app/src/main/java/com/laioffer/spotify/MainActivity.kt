package com.laioffer.spotify

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.laioffer.spotify.datamodel.Section
import com.laioffer.spotify.network.NetworkApi
import com.laioffer.spotify.network.NetworkModule
import com.laioffer.spotify.repository.HomeRepository
import com.laioffer.spotify.ui.theme.SpotifyTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject

// customized extend AppCompatActivity
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val TAG = "lifecycle"

//    @Inject
//    lateinit var retrofit: Retrofit

    @Inject
    lateinit var api: NetworkApi

    @Inject
    lateinit var homeRepository: HomeRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(TAG, "We are at onCreate")

        setContentView(R.layout.activity_main)

        // findViewById
        val navView = findViewById<BottomNavigationView>(R.id.nav_view)

        // navHost, navController
        // using navController to change the fragment in navHost

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        val navController = navHostFragment.navController
        navController.setGraph(R.navigation.nav_graph)

        NavigationUI.setupWithNavController(navView, navController)

        // https://stackoverflow.com/questions/70703505/navigationui-not-working-correctly-with-bottom-navigation-view-implementation
        navView.setOnItemSelectedListener {
            NavigationUI.onNavDestinationSelected(it, navController)
            navController.popBackStack(it.itemId, inclusive = false)
            true
        }

        // val retrofit: Retrofit = NetworkModule.provideRetrofit()
        // // Retrofit helps us to implement the interface
        // val api: NetworkApi = retrofit.create(NetworkApi::class.java)

        // val call: Call<List<Section>> = api.getHomeFeed() // HTTP calls very expensive
        GlobalScope.launch(Dispatchers.IO) {
//            val response: Response<List<Section>> = call.execute()
//            response.code()
//            response.headers()
//            response.body()

//            val sections: List<Section>? = api.getHomeFeed().execute().body()
            val sections: List<Section> = homeRepository.getHomeSections()
            Log.d("Network", sections.toString())
        }

        // main thread (UI thread)
        // default is main thread
        // expensive operations: put it in background thread
    }
}

@Composable
fun ArtistColumn() {
    Column {
        Text("Alfred Sisley")
        Text("3 minutes ago")
    }
}

@Preview
@Composable
fun PreviewArtistColumn() {
    SpotifyTheme {
        Surface {
            ArtistColumn()
        }
    }
}

@Composable
fun ArtistRow() {
    Row {
        Text("Alfred Sisley")
        Text("3 minutes ago")
    }
}

@Preview
@Composable
fun PreviewArtistRow() {
    SpotifyTheme {
        Surface {
            ArtistRow()
        }
    }
}

@Composable
fun ArtistBox() {
    Box {
        Text("Alfred Sisley")
        Text("3 minutes ago")
    }
}

@Preview
@Composable
fun PreviewArtistBox() {
    SpotifyTheme {
        Surface {
            ArtistBox()
        }
    }
}

@Composable
fun Greeting(name: String) {
    Column(
        modifier =
        Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(24.dp)
            .background(Color.Yellow)
    ) {
        Text(text = "Hello, ")
        Text(text = "$name!")
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SpotifyTheme {
        Surface {
            Greeting("Android")
        }
    }
}

@Composable
fun HelloContent(name: String, onNameChange: (String) -> Unit) {
    Column {
        if (name.isNotEmpty()) {
            Text(
                text = "Hello",
                modifier = Modifier.padding(bottom = 16.dp),
                style = MaterialTheme.typography.body2
            )
        }
        TextField(
            value = name,
            onValueChange = onNameChange,
            label = {
                Text("Name")
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHelloContent() {
    SpotifyTheme {
        Surface {
            var name by remember { mutableStateOf("") }
            HelloContent(name) {
                name = it
            }
        }
    }
}

@Composable
fun AlbumCover() {
    Column {
        Box(modifier = Modifier.size(160.dp)) {
            // https://upload.wikimedia.org/wikipedia/en/d/d1/Stillfantasy.jpg
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https://upload.wikimedia.org/wikipedia/en/d/d1/Stillfantasy.jpg")
                    .addHeader("User-Agent", "Mozilla/5.0 (Android Emulator) Chrome/120.0").build(),
                contentDescription = null,
                contentScale = ContentScale.FillBounds
            )
            Text(
                text = "Still Fantacy",
                color = Color.White,
                modifier = Modifier
                    .padding(start = 2.dp, bottom = 4.dp)
                    .align(Alignment.BottomStart)
            )
        }
        Text(
            text = "Jay Chou",
            color = Color.LightGray,
            modifier = Modifier.padding(top = 4.dp, start = 2.dp),
            // MaterialTheme: material design
            style = MaterialTheme.typography.body2.copy(fontWeight = FontWeight.Bold)
        )
    }
}
