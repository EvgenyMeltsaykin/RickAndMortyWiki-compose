package wiki.rickandmorty.i_episode.use_cases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import wiki.rickandmorty.data.EpisodeDto
import wiki.rickandmorty.i_episode.EpisodeApiService
import wiki.rickandmorty.i_episode.data.EpisodeInfoResponse

interface GetEpisodesByIdsUseCase {
    suspend operator fun invoke(ids: List<String>):Flow<List<EpisodeDto>>
}

class GetEpisodesByIdsUseCaseImpl(
    private val apiService: EpisodeApiService
) : GetEpisodesByIdsUseCase {

    override suspend fun invoke(ids: List<String>): Flow<List<EpisodeDto>> {
        val idsString = ids.joinToString(separator = ",")
        return if(ids.size > 1){
            flowOf(apiService.getEpisodes(idsString).map{it.toEpisodeDto()})
        }else{
            flowOf(listOf(apiService.getEpisode(idsString).toEpisodeDto()))
        }
    }

}