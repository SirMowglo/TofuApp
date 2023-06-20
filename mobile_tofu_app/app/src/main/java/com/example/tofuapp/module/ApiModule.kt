package com.example.tofuapp.module

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.tofuapp.data.service.AuthService
import com.example.tofuapp.data.service.RecipeService
import com.example.tofuapp.data.service.UserService
import com.example.tofuapp.util.API_BASE_URL
import com.example.tofuapp.util.AuthAuthenticator
import com.example.tofuapp.util.AuthInterceptor
import com.example.tofuapp.util.TokenManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.Retrofit.Builder
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "data_store")

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {
    @Singleton
    @Provides
    fun provideTokenManager(@ApplicationContext context: Context): TokenManager =
        TokenManager(context)

    @Singleton
    @Provides
    fun provideOkHttpClient(
        authInterceptor: AuthInterceptor,
        authAuthenticator: AuthAuthenticator,
    ): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
            .authenticator(authAuthenticator)
            .build()
    }

    @Singleton
    @Provides
    fun provideAuthInterceptor(tokenManager: TokenManager): AuthInterceptor =
        AuthInterceptor(tokenManager)

    @Singleton
    @Provides
    fun provideAuthAuthenticator(tokenManager: TokenManager): AuthAuthenticator =
        AuthAuthenticator(tokenManager)

    @Singleton
    @Provides
    fun provideRetrofitBuilder(): Retrofit.Builder =
        Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())

    @Singleton
    @Provides
    fun provideAuthApiService(retrofit: Retrofit.Builder):AuthService =
        retrofit
            .build()
            .create(AuthService::class.java)

    @Singleton
    @Provides
    fun provideUserApiService(okHttpClient: OkHttpClient ,retrofit: Builder): UserService =
        retrofit
            .client(okHttpClient)
            .build()
            .create(UserService::class.java)
    @Singleton
    @Provides
    fun provideRecipeApiService(okHttpClient: OkHttpClient ,retrofit: Builder): RecipeService =
        retrofit
            .client(okHttpClient)
            .build()
            .create(RecipeService::class.java)
}