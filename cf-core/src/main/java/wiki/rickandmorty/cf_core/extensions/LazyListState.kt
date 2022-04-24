package wiki.rickandmorty.cf_core.extensions

import androidx.compose.foundation.lazy.LazyListState

fun LazyListState.isScrolledToTheEnd(): Boolean {
    return layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0 >= layoutInfo.totalItemsCount - 5
}