package com.example.movierating.data.network

import android.content.Context
import com.example.movierating.data.repository.RemoteRepository
import com.example.movierating.data.repository.RemoteRepositoryImpl
import com.example.movierating.util.Constant
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

private const val MODULE_NAME = "Network Module"

val networkModule = DI.Module(MODULE_NAME, false) {
//    bind<File>() with singleton { getCacheFile(instance()) }
//    bind<Cache>() with singleton { getCache(instance()) }
    bind<OkHttpClient>() with singleton { getOkHttpClient() }
    bind<Retrofit>() with singleton { getRetrofit(instance()) }
    bind<ApiService>() with singleton { getApiService() }
    bind<RemoteRepository>() with singleton { getRemoteRepository(instance()) }
}

fun getCacheFile(context: Context): File {
    return File(context.cacheDir, "okhttp_cache")
}

private fun getOkHttpClient(): OkHttpClient {
    val loggingInterceptor = HttpLoggingInterceptor()
    loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

    return OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
//        .addInterceptor(DecryptionInterceptor())
        .connectTimeout(1, TimeUnit.MINUTES)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(15, TimeUnit.SECONDS)
        .build()
}

private fun getGson(): Gson {
    val gsonBuilder = GsonBuilder()
    gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
    return gsonBuilder.create()
}

private fun getRetrofit(okHttpClient: OkHttpClient): Retrofit {

    return Retrofit.Builder()
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(getGson()))
        .baseUrl(Constant.BASE_URL)
        .build()
}

fun getApiService(): ApiService {
    return getRetrofit(getOkHttpClient()).create(ApiService::class.java)
}

private fun getRemoteRepository(apiService: ApiService): RemoteRepository =
    RemoteRepositoryImpl(apiService)
