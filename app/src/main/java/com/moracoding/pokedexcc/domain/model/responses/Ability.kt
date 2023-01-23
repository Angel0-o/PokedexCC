package com.moracoding.pokedexcc.domain.model.responses

data class Ability(
    val ability: AbilityX,
    val is_hidden: Boolean,
    val slot: Int
)