package br.com.isgreen.calculatorsimulator.data.api

import br.com.isgreen.calculatorsimulator.BuildConfig
import br.com.isgreen.calculatorsimulator.data.model.Simulation
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

/**
 * Created by Éverdes Soares on 08/06/2019.
 */

interface Api {

    class Factory {

        companion object {
            fun create(): Api {
                val okHttpClient: OkHttpClient.Builder =
                    OkHttpClient.Builder()
                        .connectTimeout(1, TimeUnit.MINUTES)
                        .readTimeout(1, TimeUnit.MINUTES)

                val interceptor = HttpLoggingInterceptor()
                interceptor.level = HttpLoggingInterceptor.Level.BODY

                val retrofit = Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                    .addCallAdapterFactory(CoroutineCallAdapterFactory())
                    .client(okHttpClient.build())
                    .build()

                return retrofit.create(Api::class.java)
            }
        }
    }

    @GET("/calculator/simulate")
    suspend fun getSimulation(
        @Query("rate") rate: Int,
        @Query("index") index: String,
        @Query("isTaxFree") isTaxFree: Boolean,
        @Query("maturityDate") maturityDate: String,
        @Query("investedAmount") investedAmount: Double
    ): Simulation
}