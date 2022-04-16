package wiki.rickandmorty.i_characters

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import wiki.rickandmorty.cf_network.HttpClient
import wiki.rickandmorty.i_characters.data.CharactersResponse

interface CharactersApiService {

    @GET(CHARACTER)
    suspend fun getAllCharacters(
        @Query("page") page:Int
    ): CharactersResponse

    companion object {
        private const val CHARACTER = "character"
        fun create(
            okHttpClient: OkHttpClient
        ):CharactersApiService{
            return Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(HttpClient.BASE_URL)
                .build()
                .create(CharactersApiService::class.java)
        }
    }
}