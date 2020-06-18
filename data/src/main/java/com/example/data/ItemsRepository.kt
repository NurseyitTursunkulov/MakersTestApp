package com.example.data


interface ItemsRepository {
    val itemsRepositoryUtil: ItemsRepositoryUtil
    suspend fun getItems(page: Int): Result<List<Item>>
    suspend fun getItemsSortedByPrice():Result<List<Item>>
    suspend fun getItemsSortedByCategory():Result<List<Item>>
}

