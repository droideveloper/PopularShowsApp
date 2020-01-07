/*
 * Open Source Copyright (C) 2019 Fatih, Popular Shows Android Kotlin.
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.fs.pshows.common.di.module

import android.content.Context
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.*
import org.fs.pshows.BuildConfig
import org.fs.pshows.common.moshi.factory.RxJava2CallAdapterFactory
import org.fs.pshows.net.Endpoint
import org.fs.pshows.util.C.Companion.AUTH_INTER
import org.fs.pshows.util.C.Companion.LOG_INTER
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.File
import java.lang.IllegalArgumentException
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module class NetworkModule {

  private companion object {
    private const val CACHE_DIR = "cache"
    private const val CACHE_SIZE = 12 * 1024 * 1024L
    private const val DEFAULT_TIMEOUT = 20L

    private const val MEDIA_TYPE = "application/json; charset=UTF-8"
  }

  @Singleton @Provides fun provideHttpUrl(): HttpUrl = HttpUrl.parse(BuildConfig.BASE_URL) ?: throw IllegalArgumentException("could not parse url string ${BuildConfig.BASE_URL}")

  @Singleton @Provides fun provideMediaType(): MediaType {
    val mediaType = MediaType.parse(MEDIA_TYPE)
    if (mediaType != null) {
      return mediaType
    }
    throw IllegalArgumentException("we can not parse media type string $MEDIA_TYPE")
  }

  @Singleton @Provides fun provideMoshiConverterFactory(moshi: Moshi): Converter.Factory = MoshiConverterFactory.create(moshi)

  @Singleton @Provides fun provideMoshi(adapter: JsonAdapter<Date>): Moshi = Moshi.Builder().add(adapter).build()

  @Singleton @Provides fun provideRetrofit(factory: Call.Factory, httpUrl: HttpUrl, converterFactory: Converter.Factory, moshi: Moshi): Retrofit = Retrofit.Builder()
    .baseUrl(httpUrl)
    .callFactory(factory)
    .addConverterFactory(converterFactory)
    .addCallAdapterFactory(RxJava2CallAdapterFactory.create(moshi))
    .build()

  @Singleton @Provides fun provideHttpFactory(context: Context, @Named(value = AUTH_INTER) auth: Interceptor, @Named(value = LOG_INTER) log: Interceptor): Call.Factory {
    val file = File(context.cacheDir, CACHE_DIR)
    val cache = Cache(file, CACHE_SIZE)

    val builder = OkHttpClient.Builder()
      .cache(cache)
      .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
      .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
      .addInterceptor(auth)
      .addInterceptor(log)

    return builder.build()
  }

  @Singleton @Provides fun provideEndpoint(retrofit: Retrofit): Endpoint = retrofit.create(Endpoint::class.java)
}