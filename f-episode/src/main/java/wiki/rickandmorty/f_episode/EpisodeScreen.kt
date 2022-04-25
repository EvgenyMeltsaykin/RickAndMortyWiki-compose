package wiki.rickandmorty.f_episode

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelStore
import com.github.terrakok.modo.Modo
import kotlinx.parcelize.Parcelize
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf
import wiki.rickandmorty.cf_core.ScreenRouter
import wiki.rickandmorty.cf_core.base.BaseScreen
import wiki.rickandmorty.cf_core.base.EventScreen
import wiki.rickandmorty.cf_core.base.ViewStateScreen
import wiki.rickandmorty.cf_ui.TextMontserrat
import wiki.rickandmorty.data.EpisodeDto

@Parcelize
class EpisodeScreen(
    private val episode:EpisodeDto
):BaseScreen<EpisodeScreen.ScreenEvent,
    EpisodeScreen.EpisodeViewState,
    EpisodeViewModel>() {

    sealed class ScreenEvent : EventScreen {
        object OnNavigateBack : ScreenEvent()
    }

    data class EpisodeViewState(
        val name:String,
        val releaseDate:String,
        val shortName:String
    ): ViewStateScreen

    override fun bindEvents(event: ScreenEvent, router: Modo,screens: ScreenRouter) {

    }

    @Composable
    override fun Content() {
        val viewModel:EpisodeViewModel = getViewModel(owner = { ViewModelStore() }) { parametersOf(episode) }
        BindScreen(viewModel = viewModel)
        SetupStatusBar(color = Color.Gray)
        EpisodeContent(viewModel)
    }

    @Composable
    fun EpisodeContent(viewModel: EpisodeViewModel){
        val viewState by viewModel.state.collectAsState()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Gray)
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(2f),
                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    TextMontserrat(
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Start,
                        color = Color.White,
                        text = stringResource(id = R.string.episode_release_date),
                    )
                    TextMontserrat(
                        textAlign = TextAlign.Start,
                        color = Color.White,
                        text = viewState.releaseDate,
                    )
                }

                TextMontserrat(
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.End,
                    text = viewState.shortName
                )
            }
        }
    }

}

