package wiki.rickandmorty.navigation

import com.github.terrakok.modo.Screen
import wiki.rickandmorty.cf_core.ScreenRouter
import wiki.rickandmorty.data.CharacterDto
import wiki.rickandmorty.data.EpisodeDto
import wiki.rickandmorty.feature.characters.CharactersListScreen
import wiki.rickandmorty.f_detail_character.DetailCharacterScreen
import wiki.rickandmorty.f_episode.EpisodeScreen

object Screens: ScreenRouter {

    override fun CharactersList() = CharactersListScreen()

    override fun DetailCharacter(
        character: CharacterDto
    ) = DetailCharacterScreen(character = character)

    override fun Episode(
        episode: EpisodeDto
    ) = EpisodeScreen(episode = episode)

}