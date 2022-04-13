package wiki.rickandmorty.cf_network.data

import com.google.gson.annotations.SerializedName

data class PaginationInfo(
    @SerializedName("count") val count: Int,
    @SerializedName("pages") val pages: Int,
    @SerializedName("next") val next: String?,
    @SerializedName("prev") val prev:String?
)

open class BasePaginationResponse(
    @SerializedName("info") val info:PaginationInfo
)