package com.moracoding.pokedexcc.presentation.pokemonlist

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.palette.graphics.Palette
import com.moracoding.pokedexcc.domain.model.PokedexListEntry
import com.moracoding.pokedexcc.domain.use_case.GetPokemonListUseCase
import com.moracoding.pokedexcc.util.Constants.PAGE_SIZE
import com.moracoding.pokedexcc.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val getPokemonListUseCase: GetPokemonListUseCase
): ViewModel() {

    private var currentPage = 0

    var pokemonList = mutableStateOf<List<PokedexListEntry>>(listOf())
    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)
    var endReached = mutableStateOf(false)

    private var cachePokemonList = listOf<PokedexListEntry>()
    private var isSearchStarting = true
    var isSearching = mutableStateOf(false)

    init {
        loadPokemonPaginated()
    }

    fun searchPokemonList(query: String){
        val listToSearch = if (isSearchStarting) pokemonList.value else cachePokemonList
        viewModelScope.launch(Dispatchers.Default) {
            if (query.isEmpty()){
                pokemonList.value = cachePokemonList
                isSearching.value = true
                isSearchStarting = true
                return@launch
            }
            val results = listToSearch.filter {
                it.pokemonName.contains(query.trim(), ignoreCase = true) || it.number.toString() == query.trim()
            }
            if (isSearchStarting){
                cachePokemonList = pokemonList.value
                isSearchStarting = false
            }
            pokemonList.value = results
            isSearching.value = true
        }
    }

    fun loadPokemonPaginated(){
        viewModelScope.launch {
            isLoading.value = true
            when(val result = getPokemonListUseCase(currentPage * PAGE_SIZE,PAGE_SIZE)){
                is Resource.Success ->{
                    endReached.value = currentPage * PAGE_SIZE >= result.data!!.count
                    val pokedexEntries = result.data.results.mapIndexed { _, entry ->
                        val number = if (entry.url.endsWith("/"))
                            entry.url.dropLast(1).takeLastWhile { it.isDigit() }
                        else
                            entry.url.takeLastWhile { it.isDigit() }
                        val url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${number}.png"
                        PokedexListEntry(entry.name.capitalize(Locale.ROOT), url, number.toInt())
                    }
                    currentPage++

                    loadError.value = ""
                    isLoading.value = false
                    pokemonList.value += pokedexEntries
                }
                is Resource.Error ->{
                    loadError.value = result.message!!
                    isLoading.value = false
                }
                is Resource.Loading ->{}
            }
        }
    }

    fun calcDominantColor(drawable: Drawable, onFinish: (Color) -> Unit){
        val bmp = (drawable as BitmapDrawable).bitmap.copy(Bitmap.Config.ARGB_8888, true)

        Palette.from(bmp).generate { palette ->
            palette?.dominantSwatch?.rgb?.let{ colorValue ->
                onFinish(Color(colorValue))
            }
        }
    }

}