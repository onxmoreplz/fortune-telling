package com.example.fortune.core.network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.internal.ignoreIoExceptions
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    // TODO: 실제 URL로 교체
    private const val BASE_URL = "https://your-api-base-url.com/api/v1/"

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
            .build()


    @Provides
    @Singleton
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true // JSON에 정의되지 않은 필드가 있어도 에러내지 않음
        coerceInputValues = true // 타입이 안맞을 때 기본값 시도
    }

    @Provides
    @Singleton
    fun provideRetrofit(json: Json): Retrofit {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }

    @Provides
    @Singleton
    fun provideFortuneNetworkApi(retrofit: Retrofit): FortuneNetworkApi {
        return retrofit.create(FortuneNetworkApi::class.java)
    }
}