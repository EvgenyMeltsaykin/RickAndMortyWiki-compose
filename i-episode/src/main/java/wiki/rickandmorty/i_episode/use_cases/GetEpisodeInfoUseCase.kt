package wiki.rickandmorty.i_episode.use_cases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import wiki.rickandmorty.data.EpisodeDto
import wiki.rickandmorty.i_episode.EpisodeApiService
import wiki.rickandmorty.i_episode.data.EpisodeInfoResponse

interface GetEpisodeInfoUseCase {
    suspend operator fun invoke(id: String): Flow<EpisodeDto>
}

class GetEpisodeInfoUseCaseImpl(
    private val apiService: EpisodeApiService
) : GetEpisodeInfoUseCase {

    override suspend fun invoke(id: String): Flow<EpisodeDto> {
        return flowOf(apiService.getEpisode(id).toEpisodeDto())
    }

}