package wiki.rickandmorty.i_characters.data

import com.google.gson.annotations.SerializedName
import wiki.rickandmorty.cf_network.data.PaginationInfo
import wiki.rickandmorty.data.CharacterDto

data class CharactersResponse(
    @SerializedName("results") val result: List<CharacterInfoResponse>,
    @SerializedName("info") val info: PaginationInfo
)

data class SimpleResponse(
    @SerializedName("name") val name : String,
    @SerializedName("url") val url : String
)

data class CharacterInfoResponse (
    @SerializedName("id") val id : Int,
    @SerializedName("name") val name : String,
    @SerializedName("status") val status : String,
    @SerializedName("species") val species : String,
    @SerializedName("type") val type : String,
    @SerializedName("gender") val gender : String,
    @SerializedName("origin") val origin : SimpleResponse,
    @SerializedName("location") val location : SimpleResponse,
    @SerializedName("image") val imageUrl : String,
    @SerializedName("episode") val episode : List<String>,
    @SerializedName("url") val url : String,
    @SerializedName("created") val created : String
){
    fun toCharacterDto():CharacterDto = CharacterDto(
        id = id,
        name = name,
        status = status,
        species = species,
        gender = gender,
        imageUrl = imageUrl
    )
}


