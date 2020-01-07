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

import org.fs.pshows.BuildConfig

sealed class C {

  companion object {

    const val RECYCLER_CACHE_SIZE = 10

    const val DB_VERSION = 1
    const val DB_NAME = "local_storage.db"

    const val KEY_API_KEY = "api_key"
    const val KEY_LANGUAGE = "language"

    const val AUTH_INTER = "authInterceptor"
    const val LOG_INTER = "logInterceptor"

    private const val ENDPOINT_SUFFIX = "/3/tv"

    const val POPULAR_SHOWS = "$ENDPOINT_SUFFIX/popular"
    const val SHOW_EXTRA = "$ENDPOINT_SUFFIX/{showId}"
    const val SIMILAR_SHOWS = "$ENDPOINT_SUFFIX/{showId}/similar"
    const val SHOW_IMAGES = "$ENDPOINT_SUFFIX/{showId}/images"
    const val SHOW_VIDEOS = "$ENDPOINT_SUFFIX/{showId}/videos"
    const val SHOW_CREDITS = "$ENDPOINT_SUFFIX/{showId}/credits"
    const val SHOW_GENRES = "/3/genre/tv/list" // this one is little bit weird of their pattern

    private const val IMAGE_SUFFIX = "/t/p/"
    const val IMAGE_URL = "${BuildConfig.IMAGE_BASE_URL}$IMAGE_SUFFIX"

    const val YOUTUBE_IMAGE_URL = "https://img.youtube.com/vi/"

    const val IMAGE_TYPE_SMALL = "w200"
    const val IMAGE_TYPE_LARGE = "w500"
    const val IMAGE_TYPE_ORIGINAL = "original"

    const val GENRE_JOINT = " \u2022 "

    const val VIEW_TYPE_SIMPLE = 0x01
    const val VIEW_TYPE_PROGRESS = 0x02

    const val DETAIL_TYPE_SPOT = 0x01
    const val DETAIL_TYPE_SEASON = 0x02
    const val DETAIL_TYPE_CREDIT = 0x03
    const val DETAIL_TYPE_SIMILAR = 0x04
    const val DETAIL_TYPE_IMAGE = 0x05
    const val DETAIL_TYPE_VIDEO = 0x06
    const val DETAIL_TYPE_TITLE = 0x07

    const val BUNDLE_EXTRA_SHOW = "bundle.extra.show"
  }
}