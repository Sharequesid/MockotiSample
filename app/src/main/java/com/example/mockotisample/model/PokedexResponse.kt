package com.example.mockotisample.model

import com.google.gson.annotations.SerializedName

data class PokedexResponse(
    val id: Long,
    val name: String,
    @SerializedName("pokemon_entries") val pokemonEntries: List<PokemonEntryResponse>
)

data class PokemonEntryResponse(
    @SerializedName("entry_number") val entryNumber: Long,
    @SerializedName("pokemon_species") val pokemonSpecies: PokemonResponse
)

data class PokemonResponse(
    val name: String,
    val url: String
)

fun PokemonEntryResponse.toPokemon(): Pokemon {
    return Pokemon(
        entryNumber = this.entryNumber,
        name = this.pokemonSpecies.name,
        url = this.pokemonSpecies.url
    )
}