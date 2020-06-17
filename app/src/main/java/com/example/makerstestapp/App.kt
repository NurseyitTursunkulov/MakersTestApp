package com.example.makerstestapp

import android.app.Application
import com.example.data.FactRepositoryImpl
import com.example.data.FactsRepository
import com.example.data.FactsRepositoryUtil
import com.example.data.FactsRepositoryUtilImpl
import com.example.data.local.FactsDao
import com.example.data.local.FactsDataBase
import com.example.data.remote.FactServiceApi
import com.example.domain.GetFactsUseCase
import com.example.domain.GetFactsUseCaseImpl
import com.example.makerstestapp.factList.FactsViewModel
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

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

        factory {
            OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .build()
        }

        single {
            Retrofit.Builder()
                .client(get())
                .baseUrl("https://catfact.ninja/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()
        }

        factory { get<Retrofit>().create(FactServiceApi::class.java) }

        single<FactsRepositoryUtil> {
            FactsRepositoryUtilImpl(
                factServiceApi = get(),
                factsDao = get()
            )
        }

        single<FactsRepository> {
            FactRepositoryImpl(
                factServiceApi = get(),
                factsDao = get(),
                factsRepositoryUtil = get()
            )
        }

        single<GetFactsUseCase> { GetFactsUseCaseImpl(factRepository = get()) }
        viewModel { FactsViewModel(getFactsUseCase = get()) }

        single<FactsDao> { FactsDataBase.getInstance(androidApplication()).factsDao() }
    }
}