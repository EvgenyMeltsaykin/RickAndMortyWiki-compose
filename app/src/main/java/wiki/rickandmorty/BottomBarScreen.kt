package wiki.rickandmorty

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen(
    val route:String,
    val title:String,
    val icon: ImageVector
){
    object Characters: BottomBarScreen(
        route = "characters",
        title = "Characters",
        icon = Icons.Default.Home
    )

    object Location: BottomBarScreen(
        route = "location",
        title = "Location",
        icon = Icons.Default.Home
    )

    object Episodes: BottomBarScreen(
        route = "episodes",
        title = "Episodes",
        icon = Icons.Default.Home
    )
}