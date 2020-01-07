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
import org.fs.pshows.util.C.Companion.DETAIL_TYPE_VIDEO

@JsonClass(generateAdapter = true)
@Parcelize
data class Video(
  val id: String? = null,
  @Json(name = "iso_639_1") val iso: String? = null,
  @Json(name = "iso_3166_1") val iso2: String? = null,
  val key: String? = null,
  val name: String? = null,
  val site: String? = null,
  val size: Int? = null,
  val type: String? = null): Parcelable, Extra {

  override fun type(): Int = DETAIL_TYPE_VIDEO

  companion object {
    val EMPTY = Video()
  }
}