package com.moracoding.pokedexcc.data.data_source

import com.moracoding.pokedexcc.domain.model.responses.Pokemon
import com.moracoding.pokedexcc.domain.model.responses.PokemonList
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApi {

    @GET("pokemon/")
    suspend fun getPokemonList(
        @Query("offset") offset:Int,
        @Query("limit") limit:Int,
    ): PokemonList

    @GET("pokemon/{name}")
    suspend fun getPokemonDetail(
        @Path("name") name: String
    ): Pokemon

    @GET("pokemon/{id}")
    suspend fun getPokemonDetail(
        @Path("id") id:Int
    ): Pokemon
}