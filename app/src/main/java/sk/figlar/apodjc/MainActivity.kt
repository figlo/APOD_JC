package sk.figlar.apodjc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import sk.figlar.apodjc.api.ApodApiModel
import sk.figlar.apodjc.ui.theme.APODJCTheme
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

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
    var apods by remember { mutableStateOf<List<ApodApiModel>>(emptyList()) }

    // calculating image size (width and height)
    val configuration = LocalConfiguration.current
    val imageSize = (configuration.screenWidthDp / 3).dp

    LaunchedEffect(true) {
        apods = ApodRepository().getApodApiModels()
    }

    val apodsWithImages = apods.filter { it.mediaType == "image" }
    LazyVerticalGrid(columns = GridCells.Fixed(3), modifier = modifier) {
        items(apodsWithImages.reversed()) { apod ->
            Column {
                AsyncImage(
                    model = apod.url,
                    placeholder = painterResource(R.drawable.placeholder),
                    contentScale = ContentScale.Crop,
                    contentDescription = "",
                    modifier = Modifier
                        .height(imageSize)
                )

                val localDate = LocalDate.parse(apod.date)
                val formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)
                val formattedLocalDate = localDate.format(formatter)

                Text(text = formattedLocalDate)
                Text(text = apod.title)
            }
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