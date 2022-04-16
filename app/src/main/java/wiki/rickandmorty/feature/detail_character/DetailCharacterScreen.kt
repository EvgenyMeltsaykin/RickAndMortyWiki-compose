package wiki.rickandmorty.feature.detail_character

import androidx.compose.runtime.Composable
import kotlinx.parcelize.Parcelize
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf
import wiki.rickandmorty.core.base.BaseScreen
import wiki.rickandmorty.core.base.EventScreen
import wiki.rickandmorty.core.base.ViewStateScreen

@Parcelize
class DetailCharacterScreen(
    private val idCharacter:Int
):BaseScreen<DetailCharacterScreen.ScreenEvent,
    DetailCharacterScreen.DetailCharacterViewState,
    DetailCharacterViewModel>() {

    object ScreenEvent:EventScreen
    object DetailCharacterViewState: ViewStateScreen

    @Composable
    override fun Content() {
        val viewModel:DetailCharacterViewModel = getViewModel{ parametersOf(idCharacter)}
        BindScreen(viewModel = viewModel)
    }

    override fun bindEvents(event: ScreenEvent) {

    }
}