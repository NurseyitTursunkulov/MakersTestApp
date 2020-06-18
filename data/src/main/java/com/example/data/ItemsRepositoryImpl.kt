package com.example.data

import com.google.gson.Gson

class ItemsRepositoryImpl(
    override val itemsRepositoryUtil: ItemsRepositoryUtil,
    val content: String
) : ItemsRepository {

    override suspend fun getFacts(page: Int): Result<List<Item>> {
        val gson = Gson()
        val items: Array<Item> = gson.fromJson(content, Array<Item>::class.java)
        return Result.Success(items.asList())
    }

}