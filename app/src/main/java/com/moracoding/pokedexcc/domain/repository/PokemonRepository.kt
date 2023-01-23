package com.moracoding.pokedexcc.domain.repository

import com.moracoding.pokedexcc.domain.model.responses.Pokemon
import com.moracoding.pokedexcc.domain.model.responses.PokemonList
import com.moracoding.pokedexcc.util.Resource

interface PokemonRepository {

    suspend fun getPokemonList(offset:Int,limit:Int): Resource<PokemonList>
    suspend fun getPokemonInfo(name: String): Resource<Pokemon>
    suspend fun getPokemonInfoById(id:Int): Resource<Pokemon>
}