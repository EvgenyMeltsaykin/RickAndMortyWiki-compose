package wiki.rickandmorty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewModelScope
import com.github.terrakok.modo.*
import wiki.rickandmorty.ui.theme.RickAndMortyWikiTheme
import com.github.terrakok.modo.android.compose.ComposeRender
import com.github.terrakok.modo.android.compose.init
import com.github.terrakok.modo.android.compose.saveState
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.getViewModel
import wiki.rickandmorty.core.base.EventScreen
import wiki.rickandmorty.core.base.ViewStateScreen
import wiki.rickandmorty.navigation.Screens
import wiki.rickandmorty.ui.controllers.SnackBarController

object MainViewEvent: EventScreen
object MainViewState: ViewStateScreen

class MainActivity : ComponentActivity(), SnackBarController {
    private val router get() = RickAndMortyWikiApp.INSTANCE.modo
    private val render = ComposeRender(this)

    private var mainScaffoldState:ScaffoldState? = null
    private var mainViewModel: MainViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        router.init(savedInstanceState) { Screens.CharactersList() }

        setContent {
            mainScaffoldState = rememberScaffoldState()
            mainViewModel = getViewModel()
            RickAndMortyWikiTheme {

                Scaffold(
                    scaffoldState = mainScaffoldState!!,
                    modifier = Modifier.fillMaxSize(),
                bottomBar = { /*BottomNavigationBar(router)*/ }) {
                    render.Content()
                }
            }
        }
    }

    override fun showSnackBar(text: String?) {
        if (text == null)return
        mainViewModel?.viewModelScope?.launch {
            mainScaffoldState?.snackbarHostState?.showSnackbar(
                message = text
            )
        }
    }

    override fun onResume() {
        super.onResume()
        router.render = render
    }

    override fun onPause() {
        super.onPause()
        router.render = null
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        router.saveState(outState)
    }

    override fun onBackPressed() {
        router.back()
    }
}