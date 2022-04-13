package wiki.rickandmorty.di.network

import org.koin.core.module.Module
import org.koin.dsl.module
import wiki.rickandmorty.cf_network.HttpClient

val mainNetworkModule:Module = module(createdAtStart = true) {
    single { HttpClient.createHttpClient() }
}