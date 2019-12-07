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

sealed class C {

  companion object {

    private const val ENDPOINT_SUFFIX = "/3/tv"

    const val POPULAR_SHOWS = "$ENDPOINT_SUFFIX/popular"
    const val SHOW_EXTRA = "$ENDPOINT_SUFFIX/{showId}"
    const val SIMILAR_SHOWS = "$ENDPOINT_SUFFIX/{showId}/similar"
    const val SHOW_IMAGES = "$ENDPOINT_SUFFIX/{showId}/images"
    const val SHOW_VIDEOS = "$ENDPOINT_SUFFIX/{showId}/videos"
    const val SHOW_CREDITS = "$ENDPOINT_SUFFIX/{showId}/credits"
  }
}