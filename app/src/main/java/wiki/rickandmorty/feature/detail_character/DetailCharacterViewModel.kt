package wiki.rickandmorty.feature.detail_character

import wiki.rickandmorty.core.base.BaseViewModel

class DetailCharacterViewModel(
    private val idCharacter: Int
):BaseViewModel<
    DetailCharacterScreen.ScreenEvent,
    DetailCharacterScreen.DetailCharacterViewState>(DetailCharacterScreen.DetailCharacterViewState) {
}