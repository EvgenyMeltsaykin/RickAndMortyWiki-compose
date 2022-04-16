package wiki.rickandmorty.di.features

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import wiki.rickandmorty.MainViewModel
import wiki.rickandmorty.feature.characters.CharactersListViewModel
import wiki.rickandmorty.feature.detail_character.DetailCharacterViewModel

val charactersListModule  = module {
    viewModel{ CharactersListViewModel(get())}
    viewModel{ MainViewModel()}
    viewModel{ (idCharacter:Int) -> DetailCharacterViewModel(idCharacter = idCharacter) }
}