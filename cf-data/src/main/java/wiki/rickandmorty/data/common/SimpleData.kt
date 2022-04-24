package wiki.rickandmorty.data.common

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SimpleData(
    val value:String,
    val url:String
) : Parcelable