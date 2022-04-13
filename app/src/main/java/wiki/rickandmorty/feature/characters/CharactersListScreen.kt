package wiki.rickandmorty.feature.characters

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.terrakok.modo.android.compose.ComposeScreen
import com.github.terrakok.modo.android.compose.uniqueScreenKey
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.parcelize.Parcelize
import org.koin.androidx.compose.getViewModel
import wiki.rickandmorty.feature.characters.columnItem.CharacterItem

@Parcelize
class CharactersListScreen(
    override val screenKey: String = uniqueScreenKey
) : ComposeScreen(CharactersListScreen::javaClass.name) {

    @Composable
    override fun Content() {
        CharactersListContent()
    }

}

@Preview
@Composable
fun CharactersListContent(){
    val viewModel: CharactersListViewModel = getViewModel()
    val viewState by viewModel.state.collectAsState()
    val listState = rememberLazyListState()
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(
        color = MaterialTheme.colors.background
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colors.background)
    ) {
        Text(text = "test")
        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            itemsIndexed(items = viewState.characters) { index, character ->
                CharacterItem(character = character)
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

fun LazyListState.isScrolledToTheEnd(): Boolean {
    return layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0 >= layoutInfo.totalItemsCount - 5
}