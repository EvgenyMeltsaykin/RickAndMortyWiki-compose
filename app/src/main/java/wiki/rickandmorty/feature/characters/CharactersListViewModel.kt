package wiki.rickandmorty.feature.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import wiki.rickandmorty.data.CharacterDto
import wiki.rickandmorty.i_characters.use_cases.GetAllCharactersUseCase
import wiki.rickandmorty.util.DefaultPaginator

class CharactersListViewModel(
    private val getAllCharactersUseCase: GetAllCharactersUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(CharactersListViewState())

    val state: StateFlow<CharactersListViewState>
        get() = _state

    private val paginator = DefaultPaginator(
        initialKey = state.value.page,
        onLoadUpdated = { isLoading->
            _state.update {
                it.copy(isLoading = isLoading)
            }
        },
        onRequest = { nextPage->
            getAllCharactersUseCase(nextPage)
        },
        getNextKey = {
            state.value.page + 1
        },
        onError = {

        },
        onSuccess = { items, newKey->
            items.map { response->
                response.result.map { it.toCharacterDto() }
            }.collect{ characters->
                _state.update {
                    it.copy(
                        characters = it.characters + characters,
                        page = newKey,
                        endReached = characters.isEmpty()
                    )
                }
            }
        }
    )

    init {
        loadNextPage()
    }

    fun loadNextPage() {
        viewModelScope.launch {
            paginator.loadNextItems()
        }
    }
}

data class CharactersListViewState(
    val characters: List<CharacterDto> = emptyList(),
    val isLoading: Boolean = false,
    val page: Int = 1,
    val endReached: Boolean = false
)