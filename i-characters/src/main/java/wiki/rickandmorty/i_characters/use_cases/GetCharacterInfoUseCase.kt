package wiki.rickandmorty.i_characters.use_cases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import wiki.rickandmorty.data.CharacterDto
import wiki.rickandmorty.i_characters.CharactersApiService
import wiki.rickandmorty.i_characters.data.CharacterInfoResponse
import wiki.rickandmorty.i_characters.data.CharactersResponse

interface GetCharacterInfoUseCase {
    suspend operator fun invoke(id:Int): Flow<CharacterDto>
}

class GetCharacterInfoUseCaseImpl(
    private val apiService:CharactersApiService
):GetCharacterInfoUseCase {

    override suspend fun invoke(id: Int): Flow<CharacterDto> {
        return flowOf(apiService.getCharacter(id).toCharacterDto())
    }

}