package wiki.rickandmorty.f_episode.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import wiki.rickandmorty.f_episode.EpisodeViewModel

val episodeModule = module {
    viewModel { EpisodeViewModel(get()) }
}