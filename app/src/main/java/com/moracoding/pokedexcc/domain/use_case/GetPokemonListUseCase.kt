package com.moracoding.pokedexcc.domain.use_case

import com.moracoding.pokedexcc.data.repository.PokemonRepositoryImpl
import com.moracoding.pokedexcc.domain.model.responses.PokemonList
import com.moracoding.pokedexcc.util.Resource
import javax.inject.Inject

class GetPokemonListUseCase @Inject constructor(
    private val repository: PokemonRepositoryImpl
) {

    suspend operator fun invoke(offset:Int,limit:Int): Resource<PokemonList> = repository.getPokemonList(offset, limit)
}