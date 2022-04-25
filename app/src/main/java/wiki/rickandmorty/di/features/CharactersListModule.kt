package wiki.rickandmorty.di.features

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import wiki.rickandmorty.MainViewModel
import wiki.rickandmorty.RickAndMortyWikiApp
import wiki.rickandmorty.cf_core.ScreenRouter
import wiki.rickandmorty.data.CharacterDto
import wiki.rickandmorty.feature.characters.CharactersListViewModel
import wiki.rickandmorty.f_detail_character.DetailCharacterViewModel
import wiki.rickandmorty.navigation.Screens

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
    single<ScreenRouter> { Screens }
}