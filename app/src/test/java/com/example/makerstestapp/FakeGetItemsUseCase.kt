package com.example.makerstestapp

import androidx.annotation.VisibleForTesting
import com.example.data.Item
import com.example.data.Result
import com.example.domain.GetItemsUseCase

class FakeGetItemsUseCase : GetItemsUseCase {
    val items: LinkedHashMap<Int, Item> = LinkedHashMap()
    var result: Result<List<Item>>?=null
    private val startPage = 1

    override suspend fun invoke(): Result<List<Item>> {
        return result?:Result.Success(items.values.toList())
    }

    override var page: Int = startPage

    override suspend fun getFactsItemsSize(): Int {
        return items.size
    }

    override suspend fun getItemsSortedByPrice(): Result<List<Item>> {
        return Result.Success(items.values.toList().sortedBy {
            it.price
        })
    }

    override suspend fun getItemsSortedByCategory(): Result<List<Item>> {
        return Result.Success(items.values.toList().sortedBy {
            it.category
        })
    }

    @VisibleForTesting
    fun addNews(vararg news: Item) {
        news.forEachIndexed { index, item ->
            items[index]=item
        }

    }

}