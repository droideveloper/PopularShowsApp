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

package org.fs.pshows.model.entity

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class Image(
  @Json(name = "aspect_ratio") val aspectRatio: Double? = null,
  @Json(name = "fill_path") val fillPath: String? = null,
  val height: Int? = null,
  val width: Int? = null,
  @Json(name = "iso_639_1") val iso: String? = null,
  @Json(name = "vote_average") val voteAverage: Double? = null,
  @Json(name = "vote_count") val voteCount: Long? = null): Parcelable {

  companion object {
    val EMPTY = Image()
  }
}