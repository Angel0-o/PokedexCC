package com.moracoding.pokedexcc.domain.use_case

import com.moracoding.pokedexcc.data.repository.PokemonRepositoryImpl
import com.moracoding.pokedexcc.domain.model.responses.Pokemon
import com.moracoding.pokedexcc.util.Resource
import javax.inject.Inject

class GetPokemonInfoUseCase @Inject constructor(
    private val repository: PokemonRepositoryImpl
){
    suspend operator fun invoke(pokemonName: String): Resource<Pokemon> = repository.getPokemonInfo(pokemonName)
}