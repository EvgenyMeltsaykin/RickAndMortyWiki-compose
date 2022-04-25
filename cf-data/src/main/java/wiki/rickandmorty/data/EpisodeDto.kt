package wiki.rickandmorty.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class EpisodeDto(
    val id: Int,
    val name: String,
    val airDate: String,
    val shortEpisode: String,
    val charactersIds: List<String>,
    val created: String,
) : Parcelable