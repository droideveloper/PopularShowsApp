/*
 * Popular Shows Android Kotlin Copyright (C) 2019 Fatih, Open Soruce.
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

package org.fs.pshows.common.interceptors

import okhttp3.Interceptor
import okhttp3.Response
import org.fs.pshows.BuildConfig
import org.fs.pshows.util.C.Companion.AUTH_INTER
import org.fs.pshows.util.C.Companion.KEY_API_KEY
import org.fs.pshows.util.C.Companion.KEY_LANGUAGE
import java.util.*
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Named(value = AUTH_INTER) @Singleton
class AuthInterceptor @Inject constructor() : Interceptor {

  override fun intercept(chain: Interceptor.Chain): Response {
    val request = chain.request()
    val requestBuilder = chain.request()
      .newBuilder()

    val locale = Locale.getDefault()

    val urlBuilder = request.url()
      .newBuilder()
      .addQueryParameter(KEY_API_KEY, BuildConfig.API_KEY)
      .addQueryParameter(KEY_LANGUAGE, locale.language)

    val newUrl = urlBuilder.build()
    val newRequest = requestBuilder.url(newUrl)
      .build()

    return chain.proceed(newRequest)
  }
}