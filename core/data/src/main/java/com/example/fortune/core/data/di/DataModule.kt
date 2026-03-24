package com.example.fortune.core.data.di

import com.example.fortune.core.data.repository.AuthRepository
import com.example.fortune.core.data.repository.AuthRepositoryImpl
import com.example.fortune.core.data.repository.FortuneRepository
import com.example.fortune.core.data.repository.FortuneRepositoryImpl
import com.example.fortune.core.data.repository.fake.MockFortuneRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        impl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    @Singleton
    abstract fun bindFortuneRepository(
        // ✅ Mock 사용 중 - API 완성 후 FortuneRepositoryImpl 로 교체
        impl: MockFortuneRepositoryImpl,
    ): FortuneRepository
}
