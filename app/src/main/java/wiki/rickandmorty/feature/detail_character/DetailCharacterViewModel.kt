package wiki.rickandmorty.feature.detail_character

import kotlinx.coroutines.flow.update
import wiki.rickandmorty.core.base.BaseViewModel
import wiki.rickandmorty.i_characters.use_cases.GetCharacterInfoUseCase

class DetailCharacterViewModel(
    private val idCharacter: Int,
    private val getCharacterInfoUseCase: GetCharacterInfoUseCase
) : BaseViewModel<
    DetailCharacterScreen.ScreenEvent,
    DetailCharacterScreen.DetailCharacterViewState>(DetailCharacterScreen.DetailCharacterViewState()) {

    init {
        getCharacterInfo()
    }

    private fun getCharacterInfo(){
        launchInternetRequest {
            getCharacterInfoUseCase(idCharacter).collect{ response->
                _state.update {
                    it.copy(
                        name = response.name,
                        imageUrl = response.imageUrl,
                        gender = response.gender
                    )
                }
            }
        }
    }
}