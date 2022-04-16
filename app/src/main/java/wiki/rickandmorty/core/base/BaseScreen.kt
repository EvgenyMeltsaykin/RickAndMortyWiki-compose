package wiki.rickandmorty.core.base

import android.content.Context
import android.widget.Toast
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.github.terrakok.modo.android.compose.ComposeScreen
import com.github.terrakok.modo.android.compose.uniqueScreenKey
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import wiki.rickandmorty.RickAndMortyWikiApp
import wiki.rickandmorty.core.extensions.getActivity
import wiki.rickandmorty.ui.controllers.SnackBarController

abstract class BaseScreen<
    EventFromScreen : EventScreen,
    ViewStateFromScreen : ViewStateScreen,
    ViewModel : BaseViewModel<EventFromScreen, ViewStateFromScreen>
    >(
    override val screenKey: String = uniqueScreenKey
) : ComposeScreen(
    BaseScreen::class.simpleName.toString()
) {
    private val router get() = RickAndMortyWikiApp.INSTANCE.modo

    abstract suspend fun bindEventsFromScreen(viewModel: ViewModel)

    @Composable
    fun BindScreen(viewModel: ViewModel) {
        SetupStatusBar()
        val context = LocalContext.current
        LaunchedEffect(key1 = true) {
            bindBaseEvent(context = context, viewModel = viewModel)
            bindEventsFromScreen(viewModel = viewModel)
        }
    }

    @Composable
    private fun SetupStatusBar() {
        val systemUiController = rememberSystemUiController()
        systemUiController.setStatusBarColor(
            color = MaterialTheme.colors.background
        )
    }

    private suspend fun bindBaseEvent(context: Context, viewModel: ViewModel) {
        viewModel.baseEventFlow.collect { event ->
            when (event) {
                is BaseEventScreen.ShowToast -> {
                    Toast.makeText(context, event.text, Toast.LENGTH_SHORT).show()
                }
                is BaseEventScreen.ShowSnackBar -> {
                    showSnackBar(context = context, text = event.text)
                }
            }
        }
    }

    private fun showSnackBar(context: Context, text: String?) {
        (context.getActivity() as? SnackBarController)?.showSnackBar(text = text)
    }

}