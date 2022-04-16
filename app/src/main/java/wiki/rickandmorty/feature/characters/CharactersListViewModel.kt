package wiki.rickandmorty.feature.characters

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import wiki.rickandmorty.core.base.BaseViewModel
import wiki.rickandmorty.i_characters.use_cases.GetAllCharactersUseCase
import wiki.rickandmorty.cf_network.util.DefaultPaginator
import wiki.rickandmorty.data.CharacterDto

class CharactersListViewModel(
    private val getAllCharactersUseCase: GetAllCharactersUseCase
) : BaseViewModel<CharactersListScreen.ScreenEvent,
    CharactersListScreen.CharactersListViewState
    >(CharactersListScreen.CharactersListViewState()) {

    private val pagination = DefaultPaginator(
        initialKey = state.value.page,
        onLoadUpdated = { isLoading ->
            _state.update {
                it.copy(isLoading = isLoading)
            }
        },
        onRequest = { nextPage ->
            getAllCharactersUseCase(nextPage)
        },
        getNextKey = {
            state.value.page + 1
        },
        onError = {
            showSnackBar(it?.messageError)
        },
        onSuccess = { items, newKey ->
            items.map { response ->
                response.result.map { it.toCharacterDto() }
            }.collect { characters ->
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
        launchInternetRequest {
            pagination.loadNextItems()
        }
    }

    fun onCharacterClick(character:CharacterDto) {
        viewModelScope.launch {
            eventChanel.send(CharactersListScreen.ScreenEvent.NavigateToDetailCharacter(
                id = character.id
            ))
        }
    }

}