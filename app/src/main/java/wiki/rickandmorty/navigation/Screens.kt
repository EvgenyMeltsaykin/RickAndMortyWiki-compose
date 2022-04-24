package wiki.rickandmorty.navigation

import wiki.rickandmorty.data.CharacterDto
import wiki.rickandmorty.feature.characters.CharactersListScreen
import wiki.rickandmorty.f_detail_character.DetailCharacterScreen

object Screens {

    fun CharactersList() = CharactersListScreen()

    fun DetailCharacter(
        character: CharacterDto
    ) = DetailCharacterScreen(character = character)

}