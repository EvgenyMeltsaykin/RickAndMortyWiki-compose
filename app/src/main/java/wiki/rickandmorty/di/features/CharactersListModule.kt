package wiki.rickandmorty.di.features

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import wiki.rickandmorty.feature.characters.CharactersListViewModel

val charactersListModule  = module {
    viewModel{ CharactersListViewModel(get())}
}