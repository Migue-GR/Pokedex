package com.pokedex.app.di

import android.app.Application
import android.content.Context
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.pokedex.BuildConfig
import com.pokedex.datasource.local.AppDataBase
import com.pokedex.datasource.remote.PokeService
import com.pokedex.repository.AbilityRepository
import com.pokedex.repository.EvolutionRepository
import com.pokedex.repository.PokeRepository
import com.pokedex.viewmodel.AbilityViewModel
import com.pokedex.viewmodel.EvolutionViewModel
import com.pokedex.viewmodel.PokeViewModel
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val viewModelsModule = module {
    viewModel { PokeViewModel(get()) }
    viewModel { EvolutionViewModel(get()) }
    viewModel { AbilityViewModel(get()) }
}

val repositoriesModule = module {
    factory { PokeRepository(get(), get()) }
    factory { EvolutionRepository(get(), get()) }
    factory { AbilityRepository(get(), get()) }
}

val remoteDataSourceModule: Module = module {
    fun providePokeService(retrofit: Retrofit) = retrofit.create(PokeService::class.java)
    single { providePokeService(get()) }
}

val localDataSourceModule = module {
    fun provideAppDatabase(context: Context) = AppDataBase.getInstance(context)
    single { provideAppDatabase(androidApplication()) }
}

val netModule: Module = module {
    fun provideCache(application: Application): Cache {
        val cacheSize = 10 * 1024 * 1024
        return Cache(application.cacheDir, cacheSize.toLong())
    }

    fun provideHttpClient(
        cache: Cache
    ): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                if (BuildConfig.DEBUG) {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            })
            .cache(cache)

        return okHttpClientBuilder.build()
    }

    fun provideGson(): Gson {
        return GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create()
    }

    fun provideRetrofit(factory: Gson, client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(factory))
            .client(client)
            .build()
    }

    single { provideCache(androidApplication()) }
    single { provideHttpClient(get()) }
    single { provideGson() }
    single { provideRetrofit(get(), get()) }
}