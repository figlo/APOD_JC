package sk.figlar.apodjc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable
import sk.figlar.apodjc.model.ApodApiModel
import sk.figlar.apodjc.ui.ApodDetailScreen
import sk.figlar.apodjc.ui.ApodGalleryScreen
import sk.figlar.apodjc.ui.theme.APODJCTheme
import kotlin.reflect.typeOf

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // hide status bar
        val insetsController = WindowCompat.getInsetsController(window, window.decorView)
        insetsController.apply {
            hide(WindowInsetsCompat.Type.statusBars())
            systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }

        enableEdgeToEdge()
        setContent {
            APODJCTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
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

            }
        }
    }
}

@Serializable
object ApodListRoute

@Serializable
data class ApodDetailRoute(
    val apodApiModel: ApodApiModel,
)

