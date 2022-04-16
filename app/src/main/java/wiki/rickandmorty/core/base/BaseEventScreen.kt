package wiki.rickandmorty.core.base

interface EventScreen

sealed class BaseEventScreen: EventScreen {
    data class ShowToast(val text:String): BaseEventScreen()
    data class ShowSnackBar(val text:String?): BaseEventScreen()
}