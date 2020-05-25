package com.vbstudio.covid19.core.networking

import android.net.Uri
import android.text.TextUtils
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.vbstudio.covid19.Covid19Application
import okhttp3.Cache
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.UnsupportedEncodingException
import java.net.URLDecoder
import java.util.*

class BaseRetrofitBuilder(private val application: Covid19Application) {
    // 5MB Cache support
    val CACHE_SIZE = 5 * 1024 * 1024.toLong()
    val CACHE_DIR = "NetworkCache"

    private lateinit var dispatcher: Dispatcher
    private var retrofitBuilder: Retrofit.Builder
    private var okHttpBuilder: OkHttpClient.Builder

    init {
        // Setup RetrofitBuilder
        retrofitBuilder = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))

        // Setup OkHttpBuilder
        okHttpBuilder = OkHttpClient.Builder()
        okHttpBuilder.addInterceptor { chain ->
            val builder = chain.request().newBuilder()
            var url = chain.request().url().toString()
            try {
                url = Uri.parse(URLDecoder.decode(url, "UTF-8"))
                    .buildUpon()
                    .build().toString()
            } catch (e: UnsupportedEncodingException) {
                e.printStackTrace()
            }
            builder.url(url)
            for ((key, value) in getAuthenticationHeaders().entries) {
                if (!TextUtils.isEmpty(key) && !TextUtils.isEmpty(value)) {
                    if (chain.request().headers()[key] == null) {
                        builder.header(key, value)
                    }
                }
            }
            chain.proceed(builder.build())
        }

        addCacheSupport()
        addLoggingSupport()
    }

    private fun getClient(): OkHttpClient {
        okHttpBuilder.retryOnConnectionFailure(true)
        val client = okHttpBuilder.build()
        dispatcher = client.dispatcher()
        return client
    }

    private fun getAuthenticationHeaders(): Map<String?, String?> {
        return HashMap()
    }

    private fun addCacheSupport() {
        val cache = Cache(
            File(
                application.getCacheDir().getAbsolutePath(), CACHE_DIR
            ), CACHE_SIZE
        )
        okHttpBuilder.cache(cache)
    }

    private fun addLoggingSupport() {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        okHttpBuilder.addInterceptor(loggingInterceptor)
    }

    fun build(baseUrl: String): Retrofit? {
        return retrofitBuilder
            .baseUrl("$baseUrl/")
            .client(getClient())
            .build()
    }
}