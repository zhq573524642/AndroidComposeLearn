package com.zhq.jetpackcomposelearn.module

import com.zhq.jetpackcomposelearn.api.ApiService
import com.zhq.jetpackcomposelearn.repo.HomeRepository
import com.zhq.jetpackcomposelearn.repo.HomeRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * @Author ZhangHuiQiang
 * @Date 2025/2/14 10:29
 * Description
 */

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideHomeRepository(apiService: ApiService): HomeRepositoryImpl =
        HomeRepositoryImpl(apiService)
}