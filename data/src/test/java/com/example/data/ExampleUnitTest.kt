package com.example.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.testrussia.MainCoroutineRule
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.File

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    lateinit var itemsRepository: ItemsRepository

    @Before
    fun setupViewModel() {
        val jsonString: String = File("src/test/java/com/example/data/MOCK_DATA.json").readText(Charsets.UTF_8)
        itemsRepository = ItemsRepositoryImpl(
            itemsRepositoryUtil = ItemsRepositoryUtilImpl(
                content = jsonString
            ),
            content = jsonString
        )
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `loadSortedItemsByPrice() should return`() = runBlocking<Unit>{
        val resultItems = itemsRepository.getItemsSortedByPrice()
        Truth.assertThat(resultItems is Result.Success).isTrue()
        val items = (resultItems as Result.Success).data
        Truth.assertThat(items).hasSize(4)
        Truth.assertThat(items[0].price).isEqualTo(1)
        Truth.assertThat(items[1].price).isEqualTo(2)
        Truth.assertThat(items[2].price).isEqualTo(3)
        Truth.assertThat(items[3].price).isEqualTo(4)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `loadSortedItemsByCategory() should return`()= runBlocking<Unit>{
        val resultItems = itemsRepository.getItemsSortedByCategory()
        Truth.assertThat(resultItems is Result.Success).isTrue()
        val items = (resultItems as Result.Success).data
        Truth.assertThat(items).hasSize(4)
        Truth.assertThat(items[0].category).isEqualTo("cat1")
        Truth.assertThat(items[1].category).isEqualTo("cat2")
        Truth.assertThat(items[2].category).isEqualTo("cat3")
        Truth.assertThat(items[3].category).isEqualTo("cat4")

    }
}