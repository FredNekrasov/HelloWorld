package com.fredprojects.helloworld.data.remote

import com.fredprojects.helloworld.data.remote.services.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * ApiServiceBuilder is used to create the Retrofit services
 */
fun provideRetrofit(baseUrl: String): Retrofit = Retrofit.Builder()
    .baseUrl(baseUrl)
    .addConverterFactory(GsonConverterFactory.create())
    .build()
fun createAQService(): IAQService = provideRetrofit(IAQService.BASE_URL).create(IAQService::class.java)
fun createAstronomyInfoService(): IAstronomyInfoService = provideRetrofit(IAstronomyInfoService.BASE_URL).create(IAstronomyInfoService::class.java)
fun createMathService(): IMathService = provideRetrofit(IMathService.BASE_URL).create(IMathService::class.java)