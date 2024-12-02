package sk.figlar.apodjc.ui.apod_detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import sk.figlar.apodjc.R
import sk.figlar.apodjc.model.ApodApiModel
import sk.figlar.apodjc.ui.fakeApod
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@Composable
fun ApodDetailScreen(apod: ApodApiModel) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
    ) {
        AsyncImage(
            model = apod.url,
            placeholder = painterResource(R.drawable.placeholder),
            contentScale = ContentScale.FillWidth,
            contentDescription = "",
            modifier = Modifier
                .fillMaxWidth()
        )

        val localDate = LocalDate.parse(apod.date)
        val formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)
        val formattedLocalDate = localDate.format(formatter)

        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = apod.title.trim(),
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.size(8.dp))

            val copyright = apod.copyright?.trim()
            if (copyright?.isNotBlank() == true) {
                Text(
                    text = copyright,
                )
            }

            Text(text = formattedLocalDate)

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = apod.explanation)
        }
    }
}

@Preview(
    showSystemUi = true,
)
@Composable
fun ApodDetailScreenPreview() {
    ApodDetailScreen(apod = fakeApod)
}