package wiki.rickandmorty.navigation

import wiki.rickandmorty.feature.characters.CharactersListScreen
import wiki.rickandmorty.feature.detail_character.DetailCharacterScreen

object Screens {

    fun CharactersList() = CharactersListScreen()

    fun DetailCharacter(idCharacter: Int) = DetailCharacterScreen(idCharacter = idCharacter)

}