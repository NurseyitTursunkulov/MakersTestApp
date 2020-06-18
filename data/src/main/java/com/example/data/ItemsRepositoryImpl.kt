package com.example.data

import com.google.gson.Gson

class ItemsRepositoryImpl(
    override val itemsRepositoryUtil: ItemsRepositoryUtil,
    val content: String
) : ItemsRepository {

    override suspend fun getItems(page: Int): Result<List<Item>> {
        val gson = Gson()
        val items: Array<Item> = gson.fromJson(content, Array<Item>::class.java)
        return Result.Success(items.asList())
    }

    override suspend fun getItemsSortedByPrice(): Result<List<Item>> {
        val gson = Gson()
        val items: Array<Item> = gson.fromJson(content, Array<Item>::class.java)
        items.sortBy {
            it.price
        }
        return Result.Success(items.asList())
    }
    override suspend fun getItemsSortedByCategory(): Result<List<Item>> {
        val gson = Gson()
        val items: Array<Item> = gson.fromJson(content, Array<Item>::class.java)
        items.sortBy {
            it.category
        }
        return Result.Success(items.asList())
    }

}