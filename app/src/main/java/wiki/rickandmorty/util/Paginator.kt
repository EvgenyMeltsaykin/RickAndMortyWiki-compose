package wiki.rickandmorty.util

interface Paginator<Key, Item> {
    suspend fun loadNextItems()
    fun reset()
}