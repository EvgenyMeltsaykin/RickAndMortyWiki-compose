package wiki.rickandmorty.feature.characters

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.parcelize.Parcelize
import org.koin.androidx.compose.getViewModel
import wiki.rickandmorty.core.base.BaseScreen
import wiki.rickandmorty.core.base.EventScreen
import wiki.rickandmorty.core.base.ViewStateScreen
import wiki.rickandmorty.data.CharacterDto
import wiki.rickandmorty.core.extensions.isScrolledToTheEnd
import wiki.rickandmorty.feature.characters.columnItem.CharacterItem


@Parcelize
class CharactersListScreen : BaseScreen<
    CharactersListScreen.ScreenEvent,
    CharactersListScreen.CharactersListViewState,
    CharactersListViewModel>() {

    data class CharactersListViewState(
        val characters: List<CharacterDto> = emptyList(),
        val isLoading: Boolean = false,
        val page: Int = 1,
        val endReached: Boolean = false,
    ) : ViewStateScreen

    sealed class ScreenEvent : EventScreen {
        data class NavigateToDetailCharacter(val id: String) : ScreenEvent()
    }

    override suspend fun bindEventsFromScreen(viewModel: CharactersListViewModel) {
        viewModel.eventFlow.collect { event ->
            when (event) {
                is ScreenEvent.NavigateToDetailCharacter -> {

                }
            }
        }
    }

    @Composable
    override fun Content() {
        val viewModel: CharactersListViewModel = getViewModel()
        BindScreen(viewModel = viewModel)
        CharactersListContent(viewModel)
    }

    @Composable
    fun CharactersListContent(
        viewModel: CharactersListViewModel
    ) {
        val viewState by viewModel.state.collectAsState()
        val listState = rememberLazyListState()
        val characters by remember(viewState.characters) {
            derivedStateOf { viewState.characters }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colors.background)
        ) {
            LazyColumn(
                state = listState,
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                itemsIndexed(items = characters) { index, character ->
                    CharacterItem(
                        character = character,
                        modifier = Modifier
                            .clickable(
                                onClick = {
                                    viewModel.showSnackBar("test")
                                }
                            )
                    )
                    if (index < viewState.characters.lastIndex) {
                        Divider(
                            color = Color.LightGray,
                            thickness = 0.5f.dp,
                            modifier = Modifier.padding(horizontal = 4.dp),
                        )
                    }

                }
                item {
                    if (viewState.isLoading) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
            if (listState.isScrollInProgress && listState.isScrolledToTheEnd()) {
                viewModel.loadNextPage()
            }
        }
    }
}
