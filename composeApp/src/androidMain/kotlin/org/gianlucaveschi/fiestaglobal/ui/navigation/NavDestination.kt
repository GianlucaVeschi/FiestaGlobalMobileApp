package org.gianlucaveschi.fiestaglobal.ui.navigation

import EventDetailScreen
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import org.gianlucaveschi.fiestaglobal.data.model.EventItemResponse
import org.gianlucaveschi.fiestaglobal.ui.EventsUiState
import org.gianlucaveschi.fiestaglobal.ui.screens.events.EventContent
import kotlinx.serialization.json.Json
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

object NavDestination {
    const val EVENTS_ROUTE = "events"
    const val EVENT_DETAIL_ROUTE = "event_detail"
    const val EVENT_DETAIL_ARG = "event_json"

    fun eventDetailRoute(event: EventItemResponse): String {
        val eventJson = Json.encodeToString(EventItemResponse.serializer(), event)
        val encodedJson = URLEncoder.encode(eventJson, StandardCharsets.UTF_8.toString())
        return "$EVENT_DETAIL_ROUTE/$encodedJson"
    }
}

@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController(),
    startDestination: String = NavDestination.EVENTS_ROUTE,
    uiState: EventsUiState,
    onRetry: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(NavDestination.EVENTS_ROUTE) {
            EventContent(
                events = uiState.daySchedules.flatMap { it.events },
                onRetry = onRetry,
                onEventClick = { event ->
                    navController.navigate(NavDestination.eventDetailRoute(event))
                },
                searchQuery = ""
            )
        }

        composable(
            route = "${NavDestination.EVENT_DETAIL_ROUTE}/{${NavDestination.EVENT_DETAIL_ARG}}",
            arguments = listOf(                navArgument(NavDestination.EVENT_DETAIL_ARG) { type = NavType.StringType }),
        ) { backStackEntry ->
            val eventsJson = backStackEntry.arguments?.getString(NavDestination.EVENT_DETAIL_ARG)
            eventsJson?.let {
                val decodedJson = URLDecoder.decode(it, StandardCharsets.UTF_8.toString())
                val event = Json.decodeFromString<EventItemResponse>(decodedJson)
                EventDetailScreen(
                    event = event,
                    onBackClick = { navController.navigateUp() }
                )
            }
        }
    }
}