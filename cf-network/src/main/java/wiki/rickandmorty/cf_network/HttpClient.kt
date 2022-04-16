package wiki.rickandmorty.cf_network

import android.content.Context
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import wiki.rickandmorty.cf_network.interceptors.ConnectivityInterceptor
import java.util.concurrent.TimeUnit

object HttpClient {
    private const val TIMEOUT: Long = 5000
    const val BASE_URL = "https://rickandmortyapi.com/api/"

    fun createHttpClient(
        connectivityInterceptor: ConnectivityInterceptor
    ): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        return OkHttpClient.Builder().apply {
            connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            readTimeout(TIMEOUT, TimeUnit.SECONDS)
            writeTimeout(TIMEOUT, TimeUnit.SECONDS)
            addInterceptor(connectivityInterceptor)
            addInterceptor(loggingInterceptor)
        }.build()
    }

}