package wiki.rickandmorty.cf_network.interceptors

import okhttp3.Interceptor
import okhttp3.Response
import wiki.rickandmorty.cf_network.NetworkException
import wiki.rickandmorty.cf_network.util.ConnectivityService

class ConnectivityInterceptor(
    private val connectivityService: ConnectivityService
):Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        if (connectivityService.isOffline()){
            throw NetworkException.NoConnectivity
        }else{
            return chain.proceed(chain.request())
        }
    }
}