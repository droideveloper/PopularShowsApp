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

package org.fs.pshows.util

import android.util.Log
import io.reactivex.Observable
import org.fs.pshows.BuildConfig
import org.fs.pshows.common.rx.RetryFunction
import org.fs.pshows.model.net.PagedResponse
import org.fs.pshows.model.net.Resource
import retrofit2.Response
import java.io.PrintWriter
import java.io.StringWriter
import java.lang.IllegalArgumentException

// log extensions
inline fun <reified T> T.log(message: String) = log(Log.DEBUG, message)

inline fun <reified T> T.log(level: Int, message: String) {
  if (isLogEnabled()) {
    Log.println(level, getClassTag(), message)
  }
}

inline fun <reified T> T.log(error: Throwable) {
  val sw = StringWriter()
  val pw = PrintWriter(sw)
  error.printStackTrace(pw)
  log(Log.ERROR, sw.toString())
}

inline fun <reified T> T.isLogEnabled(): Boolean = BuildConfig.DEBUG
inline fun <reified T> T.getClassTag(): String = T::class.java.simpleName

fun <T> Observable<Response<T>>.applyResource(): Observable<Resource<T>> = map { response ->
  val code = response.code()
  if (code in 200..299) {
    return@map Resource.Success(response.body())
  }
  return@map Resource.Failure<T>(code, response.message())
}.retryWith()

fun <T> Observable<PagedResponse<T>>.toResource(): Observable<Resource<T>> = map { response ->
  val success = response.message == null
  if (success) {
    val page = response.page
    val totalPages = response.totalPages
    return@map when {
      response.results != null -> Resource.Success(response.results, page, totalPages)
      response.backdrops != null -> Resource.Success(response.backdrops)
      response.cast != null -> Resource.Success(response.cast)
      response.crew != null -> Resource.Success(response.crew)
      else -> Resource.Failure<T>(404, "unexpected data, check serialization")
    }
  }
  return@map Resource.Failure<T>(response.code, response.message)
}.retryWith()

fun <T> Observable<T>.retryWith(): Observable<T> = retryWhen(RetryFunction.create()) // will create default policy