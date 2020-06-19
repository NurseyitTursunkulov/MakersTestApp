package com.example.makerstestapp

import android.app.Application
import android.content.res.AssetManager
import com.example.data.ItemsRepository
import com.example.data.ItemsRepositoryImpl
import com.example.data.ItemsRepositoryUtil
import com.example.data.ItemsRepositoryUtilImpl
import com.example.domain.GetItemsUseCase
import com.example.domain.GetItemsUseCaseImpl
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(appModule)
        }
    }


    val appModule = module {

        single<ItemsRepositoryUtil> {
            ItemsRepositoryUtilImpl(
                content = androidApplication().assets.readAssetsFile("MOCK_DATA.json")
            )
        }
        single<ItemsRepository> {
            ItemsRepositoryImpl(
                itemsRepositoryUtil = get(),
                content = androidApplication().assets.readAssetsFile("MOCK_DATA.json")
            )
        }
        single<GetItemsUseCase> { GetItemsUseCaseImpl(factRepository = get()) }
        viewModel { MainViewModel(getItemsUseCase = get()) }

    }

    fun AssetManager.readAssetsFile(fileName: String): String =
        open(fileName).bufferedReader().use { it.readText() }
}