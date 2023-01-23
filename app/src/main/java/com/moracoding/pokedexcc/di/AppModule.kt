package com.moracoding.pokedexcc.di

import com.moracoding.pokedexcc.util.Constants.BASE_URL
import com.moracoding.pokedexcc.data.data_source.PokemonApi
import com.moracoding.pokedexcc.data.repository.PokemonRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providePokemonRepository(api: PokemonApi) = PokemonRepositoryImpl(api)

    @Singleton
    @Provides
    fun providePokemonInterceptor(): OkHttpClient.Builder = OkHttpClient.Builder().addInterceptor(
        HttpLoggingInterceptor().setLevel(
            HttpLoggingInterceptor.Level.BASIC))

    @Singleton
    @Provides
    fun providePokemonApi(okHttp: OkHttpClient.Builder):PokemonApi = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(okHttp.build())
        .build()
        .create(PokemonApi::class.java)
}