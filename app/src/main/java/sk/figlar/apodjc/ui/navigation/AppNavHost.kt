package sk.figlar.apodjc.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable
import sk.figlar.apodjc.model.ApodApiModel
import sk.figlar.apodjc.ui.apod_detail.ApodDetailScreen
import sk.figlar.apodjc.ui.apod_gallery.ApodGalleryScreen
import kotlin.reflect.typeOf

@Composable
fun AppNavHost(innerPadding: PaddingValues) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = ApodListRoute,
        modifier = Modifier.padding(innerPadding)
    ) {
        composable<ApodListRoute> {
            ApodGalleryScreen(
                onDetailClick = { apodApiModel ->
                    navController.navigate(ApodDetailRoute(apodApiModel))
                }
            )
        }
        composable<ApodDetailRoute>(
            typeMap = mapOf(
                typeOf<ApodApiModel>() to CustomNavType.Apod
            )
        ) { backStackEntry ->
            val apodDetailRoute: ApodDetailRoute = backStackEntry.toRoute()
            ApodDetailScreen(apodDetailRoute.apodApiModel)
        }
    }
}

@Serializable
object ApodListRoute

@Serializable
data class ApodDetailRoute(
    val apodApiModel: ApodApiModel,
)