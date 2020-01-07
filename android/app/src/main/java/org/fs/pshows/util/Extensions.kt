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

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.BaseRequestOptions
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.fs.architecture.mvi.core.AbstractActivity
import org.fs.architecture.mvi.core.AbstractFragment
import org.fs.architecture.mvi.util.isNullOrEmpty
import org.fs.pshows.BuildConfig
import org.fs.pshows.R
import org.fs.pshows.common.rx.RetryFunction
import org.fs.pshows.model.entity.Extra
import org.fs.pshows.model.entity.Genre
import org.fs.pshows.model.net.PagedResponse
import org.fs.pshows.model.net.Resource
import org.fs.pshows.util.C.Companion.DETAIL_TYPE_SPOT
import org.fs.pshows.util.C.Companion.GENRE_JOINT
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
      response.genres != null -> Resource.Success(response.genres)
      else -> Resource.Failure<T>(404, "unexpected data, check serialization")
    }
  }
  return@map Resource.Failure<T>(response.code, response.message)
}.retryWith()

fun <T> Observable<T>.retryWith(): Observable<T> = retryWhen(RetryFunction.create()) // will create default policy

fun <T> Observable<T>.async(): Observable<T> = subscribeOn(Schedulers.io())
  .observeOn(AndroidSchedulers.mainThread())

fun BaseRequestOptions<*>.applyBase(placeHolderRes: Int = R.drawable.ic_placeholder, errorPlaceHolderRes: Int = R.drawable.ic_error_placeholder): BaseRequestOptions<*> = placeholder(placeHolderRes)
  .error(errorPlaceHolderRes)
  .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
  .dontAnimate()

fun List<Extra>?.toTypeOrDefault(defaultType: Int = DETAIL_TYPE_SPOT): Int = this?.firstOrNull { e -> e.type() != 0  }?.type() ?: defaultType

fun List<Genre>.toGenreText(): String = joinToString(GENRE_JOINT)

fun RecyclerView.addDecoration(drawable: Drawable?, orientation: Int = DividerItemDecoration.VERTICAL) {
  drawable?.let { d ->
    val divider = DividerItemDecoration(context, orientation)
    divider.setDrawable(d)
    addItemDecoration(divider)
  }
}

fun AbstractActivity<*,*>.resolveActivity(clazz: Class<*>, bundle: Bundle? = null) = startActivity(
  Intent(this, clazz).apply {
    bundle?.let { extra -> putExtras(extra) }
  })

fun AbstractFragment<*,*>.resolveActivity(clazz: Class<*>, bundle: Bundle? = null) = startActivity(
  Intent(context() ?: throw IllegalArgumentException("activity is not attached"), clazz).apply {
    bundle?.let { extra -> putExtras(extra) }
  })

fun SwipeRefreshLayout.bindProgress(showProgress: Boolean) {
  isRefreshing = showProgress
}

fun ProgressBar.bindProgress(showProgress: Boolean) {
  visibility = if (showProgress) View.VISIBLE  else View.GONE
  isIndeterminate = showProgress
}