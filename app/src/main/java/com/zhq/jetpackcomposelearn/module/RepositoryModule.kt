package com.zhq.jetpackcomposelearn.module

import com.zhq.jetpackcomposelearn.api.ApiService
import com.zhq.jetpackcomposelearn.repo.CourseRepository
import com.zhq.jetpackcomposelearn.repo.CourseRepositoryImpl
import com.zhq.jetpackcomposelearn.repo.HarmonyRepositoryImpl
import com.zhq.jetpackcomposelearn.repo.HomeRepository
import com.zhq.jetpackcomposelearn.repo.HomeRepositoryImpl
import com.zhq.jetpackcomposelearn.repo.ProjectRepositoryImp
import com.zhq.jetpackcomposelearn.repo.SystemsRepositoryImpl
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

    @Provides
    @Singleton
    fun provideHarmonyRepository(apiService: ApiService): HarmonyRepositoryImpl =
        HarmonyRepositoryImpl(apiService)

    @Provides
    @Singleton
    fun provideProjectRepository(apiService: ApiService): ProjectRepositoryImp =
        ProjectRepositoryImp(apiService)

    @Provides
    @Singleton
    fun provideSystemRepository(apiService: ApiService): SystemsRepositoryImpl =
        SystemsRepositoryImpl(apiService)

    @Provides
    @Singleton
    fun provideCourseRepository(apiService: ApiService):CourseRepositoryImpl=
        CourseRepositoryImpl(apiService)
}

