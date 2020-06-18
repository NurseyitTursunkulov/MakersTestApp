package com.example.data

interface ItemsRepositoryUtil {
    val START_PAGE: Int
        get() = 1

    suspend fun getFactsSize():Int
}