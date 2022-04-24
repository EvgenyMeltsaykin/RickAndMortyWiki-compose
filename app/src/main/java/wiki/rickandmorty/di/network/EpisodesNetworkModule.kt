package wiki.rickandmorty.di.network

import org.koin.core.module.Module
import org.koin.dsl.module
import wiki.rickandmorty.i_episode.EpisodeApiService
import wiki.rickandmorty.i_episode.use_cases.*

val episodesNetworkModule: Module = module {
    single { EpisodeApiService.create(okHttpClient = get()) }
    single<GetAllEpisodesUseCase> { GetAllEpisodesUseCaseImpl(get()) }
    single<GetEpisodeInfoUseCase> { GetEpisodeInfoUseCaseImpl(get()) }
    single<GetEpisodesByIdsUseCase> { GetEpisodesByIdsUseCaseImpl(get()) }
}