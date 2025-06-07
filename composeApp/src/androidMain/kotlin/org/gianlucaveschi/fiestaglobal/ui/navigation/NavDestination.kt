package org.gianlucaveschi.fiestaglobal.ui.navigation

import ArtistDetailScreen
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import org.gianlucaveschi.fiestaglobal.data.model.ArtistItemResponse
import org.gianlucaveschi.fiestaglobal.ui.ArtistsUiState
import org.gianlucaveschi.fiestaglobal.ui.artists.ArtistsContent
import kotlinx.serialization.json.Json
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

object NavDestination {
    const val ARTISTS_ROUTE = "artists"
    const val ARTIST_DETAIL_ROUTE = "artist_detail"
    const val ARTIST_DETAIL_ARG = "artist_json"

    fun artistDetailRoute(artist: ArtistItemResponse): String {
        val artistJson = Json.encodeToString(ArtistItemResponse.serializer(), artist)
        val encodedJson = URLEncoder.encode(artistJson, StandardCharsets.UTF_8.toString())
        return "$ARTIST_DETAIL_ROUTE/$encodedJson"
    }
}

@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController(),
    startDestination: String = NavDestination.ARTISTS_ROUTE,
    uiState: ArtistsUiState,
    onRetry: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(NavDestination.ARTISTS_ROUTE) {
            ArtistsContent(
                artists = uiState.daySchedules.flatMap { it.artists },
                onRetry = onRetry,
                onArtistClick = { artist ->
                    navController.navigate(NavDestination.artistDetailRoute(artist))
                }
            )
        }

        composable(
            route = "${NavDestination.ARTIST_DETAIL_ROUTE}/{${NavDestination.ARTIST_DETAIL_ARG}}",
            arguments = listOf(                navArgument(NavDestination.ARTIST_DETAIL_ARG) { type = NavType.StringType }),
        ) { backStackEntry ->
            val artistJson = backStackEntry.arguments?.getString(NavDestination.ARTIST_DETAIL_ARG)
            artistJson?.let {
                val decodedJson = URLDecoder.decode(it, StandardCharsets.UTF_8.toString())
                val artist = Json.decodeFromString<ArtistItemResponse>(decodedJson)
                ArtistDetailScreen(
                    artist = artist,
                    onBackClick = { navController.navigateUp() }
                )
            }
        }
    }
}