package wiki.rickandmorty.f_detail_character

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import wiki.rickandmorty.cf_core.base.BaseViewModel
import wiki.rickandmorty.data.CharacterDto
import wiki.rickandmorty.data.EpisodeDto
import wiki.rickandmorty.i_episode.use_cases.GetEpisodesByIdsUseCase

class DetailCharacterViewModel(
    private val character: CharacterDto,
    private val getEpisodesByIdsUseCase: GetEpisodesByIdsUseCase
) : BaseViewModel<
    DetailCharacterScreen.ScreenEvent,
    DetailCharacterScreen.DetailCharacterViewState>(
    DetailCharacterScreen.DetailCharacterViewState(
        name = character.name,
        imageUrl = character.imageUrl,
        gender = character.gender,
        lifeStatus = character.lifeStatus,
        species = character.species,
        lastKnownLocation = character.lastKnownLocation.value,
        originLocation = character.originLocation.value
    )
) {

    private var progressMotion: Float = 0f

    init {
        getCharacterInfo()
    }

    private fun getCharacterInfo() {
        launchInternetRequest {
            getEpisodesByIdsUseCase(character.episodeIds).collect { response ->
                val firstSeenInLocation: String = response.firstOrNull()?.name ?: "unknown"
                _state.update {
                    it.copy(
                        firstSeenInEpisodeName = firstSeenInLocation,
                        episodes = response
                    )
                }
            }
        }
    }

    fun setProgressMotion(progress: Float) {
        progressMotion = progress
        when {
            progressMotion >= 1 -> {
                _state.update {
                    it.copy(
                        isAutoPlay = false,
                        isShowEpisodes = true
                    )
                }
            }
            progressMotion < 0.9 -> {
                _state.update {
                    it.copy(
                        isShowEpisodes = false
                    )
                }
            }
        }
    }

    fun onPortalClick() {
        _state.update {
            it.copy(
                isAutoPlay = true
            )
        }
    }


    fun onPortalCloseClick() {
        viewModelScope.launch {
            eventChanel.send(DetailCharacterScreen.ScreenEvent.OnNavigateBack)
        }
    }

    fun onEpisodeClick(episode: EpisodeDto) {
        viewModelScope.launch {
            eventChanel.send(DetailCharacterScreen.ScreenEvent.NavigateToEpisode(episode = episode))
        }
    }

}