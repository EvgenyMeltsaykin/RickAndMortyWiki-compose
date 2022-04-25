package wiki.rickandmorty.cf_core

import com.github.terrakok.modo.Screen
import wiki.rickandmorty.data.CharacterDto
import wiki.rickandmorty.data.EpisodeDto

interface ScreenRouter {
    fun CharactersList():Screen
    fun DetailCharacter(character: CharacterDto):Screen
    fun Episode(episode: EpisodeDto):Screen
}