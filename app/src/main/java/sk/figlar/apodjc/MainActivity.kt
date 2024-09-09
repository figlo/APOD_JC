package sk.figlar.apodjc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.launch
import sk.figlar.apodjc.api.ApodApiModel
import sk.figlar.apodjc.ui.theme.APODJCTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            APODJCTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ApodGallery(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun ApodGallery(modifier: Modifier = Modifier) {
    val scope = rememberCoroutineScope()
    var apods by remember { mutableStateOf<List<ApodApiModel>>(emptyList()) }

    LaunchedEffect(true) {
        scope.launch {
            apods = ApodRepository().getApodApiModels()
        }
    }

    LazyVerticalGrid(columns = GridCells.Fixed(3), modifier = modifier) {
        items(apods) { apod ->
            Text(text = apod.title)
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    APODJCTheme {
        Greeting("Android")
    }
}