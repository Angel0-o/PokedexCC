package com.moracoding.pokedexcc.data.repository

import com.moracoding.pokedexcc.data.data_source.PokemonApi
import com.moracoding.pokedexcc.domain.model.responses.Pokemon
import com.moracoding.pokedexcc.domain.model.responses.PokemonList
import com.moracoding.pokedexcc.domain.repository.PokemonRepository
import com.moracoding.pokedexcc.util.Resource
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class PokemonRepositoryImpl @Inject constructor(private val api: PokemonApi): PokemonRepository {
    override suspend fun getPokemonList(offset: Int, limit: Int): Resource<PokemonList> {
        val response = try {
            api.getPokemonList(offset, limit)
        }catch (e: Exception){
            return Resource.Error(message = "Unknow Error")
        }
        return Resource.Success(response)
    }

    override suspend fun getPokemonInfo(name: String): Resource<Pokemon> {
        val response = try {
            api.getPokemonDetail(name)
        }catch (e: Exception){
            return Resource.Error(message = "Unknow Error")
        }
        return Resource.Success(response)
    }

    override suspend fun getPokemonInfoById(id: Int): Resource<Pokemon> {
        val response = try {
            api.getPokemonDetail(id)
        }catch (e: Exception){
            return Resource.Error(message = "Unknow Error")
        }
        return Resource.Success(response)
    }
}