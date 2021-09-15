package com.mbds.anneflixproject.models

sealed class FavoriteType {

    object MovieType : FavoriteType()
    object SerieType : FavoriteType()
}
