package wiki.rickandmorty.cf_network.util

import wiki.rickandmorty.cf_network.NetworkException

class DefaultPaginator<Key, Item>(
    private val initialKey: Key,
    private inline val onLoadUpdated: (Boolean) -> Unit,
    private inline val onRequest: suspend (nextKey: Key) -> Result<Item>,
    private inline val getNextKey: suspend () -> Key,
    private inline val onError: suspend (NetworkException?) -> Unit,
    private inline val onSuccess: suspend (items:Item, newKey: Key) -> Unit,
): Paginator<Key, Item> {

    private var currentKey:Key = initialKey
    private var isMakingRequest: Boolean = false

    override suspend fun loadNextItems() {
        try{
            if (isMakingRequest) return
            isMakingRequest = true
            onLoadUpdated(true)
            val result = onRequest(currentKey)
            isMakingRequest = false
            val items:Item = result.getOrElse {
                val networkException = (it as? NetworkException)?:NetworkException.Other
                onError(networkException)
                onLoadUpdated(false)
                return
            }
            currentKey = getNextKey()
            onSuccess(items,currentKey)
            onLoadUpdated(false)
        }catch (networkException:NetworkException){
            onLoadUpdated(false)
            isMakingRequest = false
            throw networkException
        }
    }

    override fun reset() {
        currentKey = initialKey
    }
}