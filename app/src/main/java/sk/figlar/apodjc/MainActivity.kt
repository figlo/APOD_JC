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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import kotlinx.serialization.Serializable
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
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = GalleryDestination,
                    modifier = Modifier.fillMaxSize()
                ) {
                    composable<GalleryDestination> {
                        ApodGallery()
                    }
                    composable<DetailDestination> {
                        ApodDetail()
                    }
                }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ApodGallery(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Serializable
object GalleryDestination

@Serializable
data class DetailDestination(
    val apodId: String,
)

@Composable
fun ApodGallery(modifier: Modifier = Modifier) {
    val viewModel = viewModel<ApodGalleryViewModel>()
    val apods by viewModel.apods.collectAsStateWithLifecycle()

    // calculating image size (width and height)
    val configuration = LocalConfiguration.current
    val imageSize = (configuration.screenWidthDp / 3).dp

    val apodsWithImages = apods.filter { it.mediaType == "image" }
    LazyVerticalGrid(columns = GridCells.Fixed(3)) {
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
fun ApodDetail(modifier: Modifier = Modifier) {
    Text("ApodDetail")
}