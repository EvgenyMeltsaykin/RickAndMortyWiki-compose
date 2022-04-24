package wiki.rickandmorty.i_episode.use_cases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import wiki.rickandmorty.i_episode.EpisodeApiService
import wiki.rickandmorty.i_episode.data.EpisodesResponse

interface GetAllEpisodesUseCase {
    suspend operator fun invoke(page: Int = 0): Result<Flow<EpisodesResponse>>
}

class GetAllEpisodesUseCaseImpl(
    private val apiService: EpisodeApiService
) : GetAllEpisodesUseCase {

    override suspend fun invoke(page: Int): Result<Flow<EpisodesResponse>> {
        return Result.success(flowOf(apiService.getAllEpisodes(page)))
    }

}