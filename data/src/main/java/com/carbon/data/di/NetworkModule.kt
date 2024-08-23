package com.carbon.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.carbon.data.BuildConfig
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.carbon.data.api.ApiService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    private const val TIMEOUT_VALUE = 120L
    private const val KEEP_ALIVE_DURATION = 5L

    @[Provides]
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @[Provides]
    @[Singleton]
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @[Provides]
    fun provideHttpClient(
        logging: HttpLoggingInterceptor
    ): OkHttpClient {
        return createOkHttpClient(logging)
    }

    private fun createOkHttpClient(
        logging: HttpLoggingInterceptor
    ): OkHttpClient {
        val builder = OkHttpClient().newBuilder()
            .connectionPool(ConnectionPool(0, KEEP_ALIVE_DURATION, TimeUnit.MINUTES))
            .addInterceptor(logging)

        builder.connectTimeout(TIMEOUT_VALUE, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT_VALUE, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_VALUE, TimeUnit.SECONDS)
            .protocols(arrayListOf(Protocol.HTTP_1_1))

        return builder.build()
    }

    @[Provides]
    fun provideRetrofit(moshi: Moshi, okHttpClient: OkHttpClient): ApiService {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(okHttpClient)
            .build()
            .create(ApiService::class.java)
    }

    val Context.dataStore by preferencesDataStore(name = "CarbonWeatherStorage")

    @Provides
    @Singleton
    fun providesDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.dataStore
    }
}