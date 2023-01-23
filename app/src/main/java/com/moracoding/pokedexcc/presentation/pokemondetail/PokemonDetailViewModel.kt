package com.moracoding.pokedexcc.presentation.pokemondetail

import androidx.lifecycle.ViewModel
import com.moracoding.pokedexcc.domain.model.responses.Pokemon
import com.moracoding.pokedexcc.domain.use_case.GetPokemonInfoUseCase
import com.moracoding.pokedexcc.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject  constructor(
    private val getPokemonInfoUseCase: GetPokemonInfoUseCase
): ViewModel() {

    suspend fun getPokemonInfo(pokemonName: String): Resource<Pokemon> = getPokemonInfoUseCase(pokemonName)
}