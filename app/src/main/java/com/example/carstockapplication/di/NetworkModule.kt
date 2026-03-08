package com.example.carstockapplication.di

import com.example.carstockapplication.data.api.AuthApi
import com.example.carstockapplication.data.api.VehicleApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

class SessionCookieJar : CookieJar {
    private val cookieStore = mutableListOf<Cookie>()

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        cookieStore.removeAll { existing -> cookies.any { it.name == existing.name } }
        cookieStore.addAll(cookies)
        android.util.Log.d("CookieJar", "Cookies salvos: ${cookieStore.map { it.name }}")
    }

    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        android.util.Log.d("CookieJar", "Cookies enviados: ${cookieStore.map { it.name }}")
        return cookieStore
    }
}

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideCookieJar(): SessionCookieJar = SessionCookieJar()

    @Provides
    @Singleton
    fun provideOkHttp(cookieJar: SessionCookieJar): OkHttpClient {
        return OkHttpClient.Builder()
            .cookieJar(cookieJar)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://192.168.0.169:8080/api/v1/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideVehicleApi(retrofit: Retrofit): VehicleApi = retrofit.create(VehicleApi::class.java)

    @Provides
    @Singleton
    fun provideAuthApi(retrofit: Retrofit): AuthApi = retrofit.create(AuthApi::class.java)
}