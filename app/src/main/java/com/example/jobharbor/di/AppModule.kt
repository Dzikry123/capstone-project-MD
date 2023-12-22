package com.example.jobharbor.di

import android.app.Application
import com.example.jobharbor.data.AuthRepo
import com.example.jobharbor.data.JobRepository
import com.example.jobharbor.data.remote.JobApi
import com.example.jobharbor.preferences.manager.LocalUserManager
import com.example.jobharbor.preferences.manager.LocalUserMangerImpl
import com.example.jobharbor.preferences.usecases.AppEntryUseCases
import com.example.jobharbor.preferences.usecases.ReadAppEntry
import com.example.jobharbor.preferences.usecases.SaveAppEntry
import com.example.jobharbor.ui.login.LoginViewModel
import com.example.jobharbor.ui.screen.home.HomeViewModel
import com.example.jobharbor.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideLocalUserManager(
        application: Application
    ): LocalUserManager = LocalUserMangerImpl(context = application)

    @Provides
    @Singleton
    fun provideAppEntryUseCases(
        localUserManager: LocalUserManager
    ): AppEntryUseCases = AppEntryUseCases(
        readAppEntry = ReadAppEntry(localUserManager),
        saveAppEntry = SaveAppEntry(localUserManager)
    )


    @Provides
    @Singleton
    fun provideApiInstance(): JobApi {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        return Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(JobApi::class.java)
    }

    @Provides
    fun provideHomeViewModel(repository: JobRepository): HomeViewModel {
        return HomeViewModel(repository)
    }

    @Provides
    fun provideLoginViewModel(repository: AuthRepo, appEntryUseCases: AppEntryUseCases): LoginViewModel {
        return LoginViewModel(repository, appEntryUseCases)
    }

    @Module
    @InstallIn(SingletonComponent::class)
    object AuthRepoModule {

        @Provides
        @Singleton
        fun provideAuthRepo(jobApi: JobApi): AuthRepo {
            return AuthRepo(jobApi)
        }
    }

}