package wiki.rickandmorty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.ui.Modifier
import com.github.terrakok.modo.*
import wiki.rickandmorty.ui.theme.RickAndMortyWikiTheme
import com.github.terrakok.modo.android.compose.ComposeRender
import com.github.terrakok.modo.android.compose.init
import com.github.terrakok.modo.android.compose.saveState
import wiki.rickandmorty.feature.characters.CharactersListScreen

class MainActivity : ComponentActivity() {
    private val modo get() = RickAndMortyWikiApp.INSTANCE.modo
    private val render = ComposeRender(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        modo.init(savedInstanceState) { CharactersListScreen() }
        setContent {
            RickAndMortyWikiTheme {
                Scaffold(modifier = Modifier.fillMaxSize(),
                bottomBar = { BottomNavigationBar(modo) }) {
                    render.Content()
                }

            }
        }
    }

    override fun onResume() {
        super.onResume()
        modo.render = render
    }

    override fun onPause() {
        super.onPause()
        modo.render = null
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        modo.saveState(outState)
    }

    override fun onBackPressed() {
        modo.back()
    }
}