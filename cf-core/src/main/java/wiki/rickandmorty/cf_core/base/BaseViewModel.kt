package wiki.rickandmorty.cf_core.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import wiki.rickandmorty.cf_network.NetworkException

abstract class BaseViewModel<EventsFromScreen: EventScreen, ViewStateFromScreen: ViewStateScreen>(
    private val initialViewState: ViewStateFromScreen
) : ViewModel() {
    protected val eventChanel = Channel<EventsFromScreen>()
    protected val _state:MutableStateFlow<ViewStateFromScreen> = MutableStateFlow(initialViewState)
    val eventFlow = eventChanel.receiveAsFlow()
    val state: StateFlow<ViewStateFromScreen>
        get() = _state

    private val baseEventChanel = Channel<BaseEventScreen>()
    val baseEventFlow = baseEventChanel.receiveAsFlow()

    fun showToast(text: String) {
        viewModelScope.launch {
            baseEventChanel.send(BaseEventScreen.ShowToast(text))
        }
    }

    fun showSnackBar(text: String?) {
        viewModelScope.launch {
            baseEventChanel.send(BaseEventScreen.ShowSnackBar(text))
        }
    }

    fun launchInternetRequest(block: suspend () -> Unit) {
        viewModelScope.launch {
            try {
                block()
            } catch (e: NetworkException) {
                showSnackBar(e.messageError)
            }
        }
    }
}