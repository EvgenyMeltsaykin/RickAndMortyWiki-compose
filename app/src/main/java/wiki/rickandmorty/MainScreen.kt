package wiki.rickandmorty

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.github.terrakok.modo.Modo
import com.github.terrakok.modo.selectStack

@Preview
@Composable
fun BottomNavigationBar(
    modo: Modo
) {
    val screens = listOf(
        BottomBarScreen.Characters,
        BottomBarScreen.Location,
        BottomBarScreen.Episodes,
    )

    BottomNavigation(
        modifier = Modifier.fillMaxWidth()
    ) {
        screens.forEachIndexed { index, screen ->
            BottomNavigationItem(selected = false, onClick = {
                modo.selectStack(index)
            },
                icon = {
                    Icon(
                        imageVector = screen.icon,
                        contentDescription = null
                    )
                })
        }
    }
}