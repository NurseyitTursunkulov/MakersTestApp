package com.example.domain

import com.example.data.*
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
            factRepository.getFacts((page)).also { result ->
                if (result is Result.Success) page += 1
            }
        }
    }

//    override suspend fun refreshFactsRepository(): Result<String> {
//        return factRepository.refreshFactsRepository()
//    }

    override suspend fun getFactsItemsSize(): Int {
        return factRepository.itemsRepositoryUtil.getFactsSize()
    }
}