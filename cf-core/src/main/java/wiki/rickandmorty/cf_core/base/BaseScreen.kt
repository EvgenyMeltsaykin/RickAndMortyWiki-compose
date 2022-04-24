package wiki.rickandmorty.cf_core.base

import android.content.Context
import android.widget.Toast
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.github.terrakok.modo.Modo
import com.github.terrakok.modo.android.compose.ComposeScreen
import com.github.terrakok.modo.android.compose.uniqueScreenKey
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import org.koin.androidx.compose.inject
import wiki.rickandmorty.cf_core.controllers.SnackBarController
import wiki.rickandmorty.cf_core.extensions.getActivity

interface Binder<
    EventFromScreen : EventScreen,
    ViewStateFromScreen : ViewStateScreen,
    ViewModel:BaseViewModel<EventFromScreen, ViewStateFromScreen>>{
    fun bindEvents(event: EventFromScreen, router:Modo)
}

abstract class BaseScreen<
    EventFromScreen : EventScreen,
    ViewStateFromScreen : ViewStateScreen,
    ViewModel : BaseViewModel<EventFromScreen, ViewStateFromScreen>>(
    override val screenKey: String = uniqueScreenKey
) : ComposeScreen(
    BaseScreen::class.simpleName.toString()
), Binder<EventFromScreen,ViewStateFromScreen,ViewModel> {


    @Composable
    fun BindScreen(viewModel: ViewModel) {
        val router:Modo by inject()
        SetupStatusBar()
        val context = LocalContext.current
        LaunchedEffect(key1 = true) {
            viewModel.eventFlow.collect{ bindEvents(it,router) }
        }
        LaunchedEffect(key1 = true){
            bindBaseEvent(context = context, viewModel = viewModel)
        }
    }

    @Composable
    fun SetupStatusBar(color: Color =MaterialTheme.colors.background) {
        val systemUiController = rememberSystemUiController()
        systemUiController.setStatusBarColor(
            color = color
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