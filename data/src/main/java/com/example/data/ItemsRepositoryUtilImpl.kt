package com.example.data

import com.google.gson.Gson

class ItemsRepositoryUtilImpl( val content: String) :ItemsRepositoryUtil {
    override suspend fun getFactsSize(): Int {
        val gson = Gson()
        val items: Array<Item> = gson.fromJson(content, Array<Item>::class.java)
        return items.size
    }
}