package wiki.rickandmorty.cf_network.util

interface Paginator<Key, Item> {
    suspend fun loadNextItems()
    fun reset()
}