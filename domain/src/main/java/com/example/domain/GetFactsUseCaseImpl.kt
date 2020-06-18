package com.example.domain

import com.example.data.Item
import com.example.data.ItemsRepository
import com.example.data.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetFactsUseCaseImpl(
    val factRepository: ItemsRepository,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : GetItemsUseCase {

    override var page = factRepository.itemsRepositoryUtil.START_PAGE

    override suspend fun invoke(): Result<List<Item>> {

        return withContext(ioDispatcher) {
            factRepository.getItems((page)).also { result ->
                if (result is Result.Success) page += 1
            }
        }
    }


    override suspend fun getFactsItemsSize(): Int {
        return withContext(ioDispatcher) {
            factRepository.itemsRepositoryUtil.getFactsSize()
        }
    }

    override suspend fun getItemsSortedByPrice(): Result<List<Item>> {
        return withContext(ioDispatcher) {
            factRepository.getItemsSortedByPrice()
        }
    }

    override suspend fun getItemsSortedByCategory(): Result<List<Item>> {
        return withContext(ioDispatcher) {
            factRepository.getItemsSortedByCategory()
        }
    }
}