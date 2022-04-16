package wiki.rickandmorty.i_characters.use_cases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import wiki.rickandmorty.data.CharacterDto
import wiki.rickandmorty.i_characters.CharactersApiService
import wiki.rickandmorty.i_characters.data.CharacterInfoResponse
import wiki.rickandmorty.i_characters.data.CharactersResponse

interface GetAllCharactersUseCase {
    suspend operator fun invoke(page:Int = 0): Result<Flow<CharactersResponse>>
}

class GetAllCharactersUseCaseImpl(
    private val apiService:CharactersApiService
):GetAllCharactersUseCase {

    override suspend fun invoke(page: Int): Result<Flow<CharactersResponse>> {
        return Result.success(flowOf( apiService.getAllCharacters(page)))
    }

}