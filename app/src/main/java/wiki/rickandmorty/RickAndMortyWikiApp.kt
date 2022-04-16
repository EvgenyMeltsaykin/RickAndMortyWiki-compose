package wiki.rickandmorty

import android.app.Application
import com.github.terrakok.modo.Modo
import com.github.terrakok.modo.android.compose.AppReducer
import com.github.terrakok.modo.android.compose.LogReducer
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.logger.Level
import wiki.rickandmorty.di.features.charactersListModule
import wiki.rickandmorty.di.network.charactersNetworkModule
import wiki.rickandmorty.di.network.mainNetworkModule
import wiki.rickandmorty.navigation.CustomModoReducer

class RickAndMortyWikiApp:Application() {
    val modo = Modo(LogReducer(CustomModoReducer(AppReducer(this))))

    override fun onCreate() {
        INSTANCE = this
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@RickAndMortyWikiApp)
            androidLogger(Level.ERROR)
            modules(
                mainNetworkModule,
                charactersNetworkModule,
                charactersListModule
            )
        }
    }

    companion object {
        lateinit var INSTANCE: RickAndMortyWikiApp
            private set
    }
}