package wiki.rickandmorty.i_episode.data

import com.google.gson.annotations.SerializedName
import wiki.rickandmorty.cf_network.data.PaginationInfo
import wiki.rickandmorty.data.EpisodeDto

data class EpisodesResponse(
    @SerializedName("results") val result: List<EpisodeInfoResponse>,
    @SerializedName("info") val info: PaginationInfo
)

data class EpisodeInfoResponse (
    @SerializedName("id") val id : Int,
    @SerializedName("name") val name : String,
    @SerializedName("air_date") val airDate : String,
    @SerializedName("episode") val shortEpisode : String,
    @SerializedName("characters") val charactersUrl : List<String>,
    @SerializedName("url") val url : String,
    @SerializedName("created") val created : String,
){
    fun toEpisodeDto(): EpisodeDto = EpisodeDto(
        id = id,
        name = name,
        airDate = airDate,
        shortEpisode = shortEpisode,
        charactersIds = charactersUrl.map { it.substringAfterLast("/") },
        created = created
    )
}


