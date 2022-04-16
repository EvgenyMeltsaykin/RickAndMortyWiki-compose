package wiki.rickandmorty.di.network

import org.koin.core.module.Module
import org.koin.dsl.module
import wiki.rickandmorty.cf_network.util.ConnectivityService
import wiki.rickandmorty.cf_network.util.ConnectivityServiceImpl
import wiki.rickandmorty.cf_network.HttpClient
import wiki.rickandmorty.cf_network.interceptors.ConnectivityInterceptor

val mainNetworkModule:Module = module(createdAtStart = true) {
    single { HttpClient.createHttpClient(connectivityInterceptor = get()) }
    single<ConnectivityService> { ConnectivityServiceImpl(get()) }
    single { ConnectivityInterceptor(get()) }
}