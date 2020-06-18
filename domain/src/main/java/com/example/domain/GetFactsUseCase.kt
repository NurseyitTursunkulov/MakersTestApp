package com.example.domain

import com.example.data.Item
import com.example.data.Result


interface GetItemsUseCase {
    suspend operator fun invoke(): Result<List<Item>>
    var page: Int
    suspend fun getFactsItemsSize(): Int
}