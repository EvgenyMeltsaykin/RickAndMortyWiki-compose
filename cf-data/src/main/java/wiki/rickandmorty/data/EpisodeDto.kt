package wiki.rickandmorty.data

data class EpisodeDto(
    val id: Int,
    val name: String,
    val airDate: String,
    val shortEpisode: String,
    val charactersIds: List<String>,
    val created: String,
)