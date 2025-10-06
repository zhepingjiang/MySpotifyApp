package com.laioffer.spotify

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.laioffer.spotify.ui.theme.SpotifyTheme

// customized extend AppCompatActivity
class MainActivity : AppCompatActivity() {

    private val TAG = "lifecycle"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(TAG, "We are at onCreate()")
        setContent {
            SpotifyTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
//                    Greeting("Android")
//                    var name : String by remember {
//                        mutableStateOf("")
//                    }
                    HelloContent()
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()

        Log.d(TAG, "We are at onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "We are at onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "We are at onPause")
    }

    override fun onStop() {
        Log.d(TAG, "We are at onStop")
        super.onStop()
    }

    override fun onDestroy() {
        Log.d(TAG, "We are at onDestroy")
        super.onDestroy()
    }
}

@Composable
fun ArtistColumn() {
    Column {
        Text(text = "Alfred Sisley")
        Text(text = "3 minutes ago")
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
        Text(text = "Alfred Sisley")
        Text(text = "3 minutes ago")
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
        Text(text = "Alfred Sisley")
        Text(text = "3 minutes ago")
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
    Column (
        // The sequence matters
        // if padding goes first, only part of the element is yellow
        // if padding goes after bg, then element's bg are all yellow
        modifier =
        Modifier
            .padding(24.dp)
            .fillMaxWidth()
            .wrapContentHeight()
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
fun HelloContent() {
//    var name: String = ""
    var name by remember {
        mutableStateOf("")
    }

    Column {
        Text(text = "Hello",
            modifier = Modifier.padding(bottom = 16.dp),
            style = MaterialTheme.typography.body2
        )
        TextField(
            value = name,
            onValueChange = {
                name = it
            },
            label = {
                Text(text = "name")
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHelloContent() {
    SpotifyTheme {
        Surface {
            HelloContent()
        }
    }
}