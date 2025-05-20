package com.example.mockotisample.utils

fun Long.toImageUrlById(): String {
    //Url not working updated
    //return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$this.png"
    return "https://unpkg.com/pokeapi-sprites@2.0.2/sprites/pokemon/other/dream-world/$this.svg"
}

fun Long.toEntryNumber(): String {
    val number = this.toString()
    val zeroPrefix = when (number.length) {
        1 -> "00"
        2 -> "0"
        else -> ""
    }
    return "$zeroPrefix$number"
}
