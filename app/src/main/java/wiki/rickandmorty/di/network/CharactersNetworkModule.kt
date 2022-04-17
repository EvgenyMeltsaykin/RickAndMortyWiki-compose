package wiki.rickandmorty.di.network

import org.koin.core.module.Module
import org.koin.dsl.module
import wiki.rickandmorty.cf_network.HttpClient
import wiki.rickandmorty.i_characters.CharactersApiService
import wiki.rickandmorty.i_characters.use_cases.GetAllCharactersUseCase
import wiki.rickandmorty.i_characters.use_cases.GetAllCharactersUseCaseImpl
import wiki.rickandmorty.i_characters.use_cases.GetCharacterInfoUseCase
import wiki.rickandmorty.i_characters.use_cases.GetCharacterInfoUseCaseImpl

val charactersNetworkModule: Module = module {
    single { CharactersApiService.create(okHttpClient = get()) }
    single<GetAllCharactersUseCase> { GetAllCharactersUseCaseImpl(get()) }
    single<GetCharacterInfoUseCase> { GetCharacterInfoUseCaseImpl(get()) }
}