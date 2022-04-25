package wiki.rickandmorty.f_episode

import wiki.rickandmorty.cf_core.base.BaseViewModel
import wiki.rickandmorty.data.EpisodeDto

class EpisodeViewModel(
    private val episode:EpisodeDto
) :BaseViewModel<EpisodeScreen.ScreenEvent,EpisodeScreen.EpisodeViewState>(
    initialViewState = EpisodeScreen.EpisodeViewState(
        name = episode.name,
        shortName = episode.shortEpisode,
        releaseDate = episode.airDate
    )
) {

}