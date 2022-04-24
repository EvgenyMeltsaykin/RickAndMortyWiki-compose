package wiki.rickandmorty.di.features

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import wiki.rickandmorty.MainViewModel
import wiki.rickandmorty.RickAndMortyWikiApp
import wiki.rickandmorty.data.CharacterDto
import wiki.rickandmorty.feature.characters.CharactersListViewModel
import wiki.rickandmorty.f_detail_character.DetailCharacterViewModel

val charactersListModule = module {
    viewModel { CharactersListViewModel(get()) }
    viewModel { MainViewModel() }
    viewModel { (character: CharacterDto) ->
        DetailCharacterViewModel(
            character = character,
            getEpisodesByIdsUseCase = get()
        )
    }
    single { RickAndMortyWikiApp.INSTANCE.modo }
}