package sk.figlar.apodjc.ui.apod_gallery

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import coil.compose.AsyncImage
import sk.figlar.apodjc.R
import sk.figlar.apodjc.model.ApodApiModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@Composable
fun ApodGalleryScreen(onDetailClick: (ApodApiModel) -> Unit = {}) {
    val viewModel = viewModel<ApodGalleryViewModel>()
    val apods by viewModel.apods.collectAsStateWithLifecycle()

    // calculating image size (width and height)
    val configuration = LocalConfiguration.current
    val imageSize = (configuration.screenWidthDp / 3).dp

    val apodsWithImages = apods.filter { it.mediaType == "image" }
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        verticalArrangement = Arrangement.spacedBy(1.dp),
        horizontalArrangement = Arrangement.spacedBy(1.dp),
    ) {
        items(apodsWithImages.reversed()) { apod ->
            Column(
                Modifier.clickable {
                    onDetailClick(apod)
                }
            ) {
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

                Column(
                    modifier = Modifier
                        .padding(4.dp)
                ) {
                    Text(text = apod.title)
                    Text(text = formattedLocalDate)
                }
            }
        }
    }
}