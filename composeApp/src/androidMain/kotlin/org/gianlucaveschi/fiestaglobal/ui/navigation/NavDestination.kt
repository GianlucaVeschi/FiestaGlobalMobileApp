package org.gianlucaveschi.fiestaglobal.ui.navigation

import EventDetailScreen
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import org.gianlucaveschi.fiestaglobal.domain.model.Event
import org.gianlucaveschi.fiestaglobal.ui.EventsUiState
import org.gianlucaveschi.fiestaglobal.ui.screens.events.EventsScreen
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

object NavDestination {
    const val EVENTS_ROUTE = "events"
    const val EVENT_DETAIL_ROUTE = "event_detail"
    const val EVENT_NAME_ARG = "event_name"
    const val EVENT_TIME_ARG = "event_time"
    const val EVENT_LOCATION_ARG = "event_location"

    fun eventDetailRoute(event: Event): String {
        val encodedName = URLEncoder.encode(event.name, StandardCharsets.UTF_8.toString())
        val encodedTime = URLEncoder.encode(event.time, StandardCharsets.UTF_8.toString())
        val encodedLocation = URLEncoder.encode(event.location, StandardCharsets.UTF_8.toString())
        return "$EVENT_DETAIL_ROUTE/$encodedName/$encodedTime/$encodedLocation"
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
            EventsScreen(
                uiState = uiState,
                onRetry = onRetry,
                onEventClick = { event ->
                    navController.navigate(NavDestination.eventDetailRoute(event))
                }
            )
        }

        composable(
            route = "${NavDestination.EVENT_DETAIL_ROUTE}/{${NavDestination.EVENT_NAME_ARG}}/{${NavDestination.EVENT_TIME_ARG}}/{${NavDestination.EVENT_LOCATION_ARG}}",
            arguments = listOf(
                navArgument(NavDestination.EVENT_NAME_ARG) { type = NavType.StringType },
                navArgument(NavDestination.EVENT_TIME_ARG) { type = NavType.StringType },
                navArgument(NavDestination.EVENT_LOCATION_ARG) { type = NavType.StringType }
            ),
        ) { backStackEntry ->
            val eventName = backStackEntry.arguments?.getString(NavDestination.EVENT_NAME_ARG)
            val eventTime = backStackEntry.arguments?.getString(NavDestination.EVENT_TIME_ARG)
            val eventLocation = backStackEntry.arguments?.getString(NavDestination.EVENT_LOCATION_ARG)
            
            if (eventName != null && eventTime != null && eventLocation != null) {
                val decodedName = URLDecoder.decode(eventName, StandardCharsets.UTF_8.toString())
                val decodedTime = URLDecoder.decode(eventTime, StandardCharsets.UTF_8.toString())
                val decodedLocation = URLDecoder.decode(eventLocation, StandardCharsets.UTF_8.toString())
                
                val event = Event(
                    name = decodedName,
                    time = decodedTime,
                    location = decodedLocation,
                    imageUrl = ""
                )
                
                EventDetailScreen(
                    event = event,
                    onBackClick = { navController.navigateUp() }
                )
            }
        }
    }
}