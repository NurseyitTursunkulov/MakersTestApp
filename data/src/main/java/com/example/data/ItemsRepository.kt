package com.example.data

import com.google.gson.Gson


interface ItemsRepository {
    val itemsRepositoryUtil: ItemsRepositoryUtil
    suspend fun getFacts(page: Int): Result<List<Item>>
}

