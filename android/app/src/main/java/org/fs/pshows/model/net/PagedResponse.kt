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

package org.fs.pshows.model.net

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PagedResponse<T>(
  val page: Int? = null,
  @Json(name = "total_pages") val totalPages: Int? = null,
  val results: T? = null, // since 'Moshi' does not support alternate keys in mapping we had to add all distinct keys in here

  val backdrops: T? = null, // only exist in images api
  val cast: T? = null, // only exist in credit api
  val crew: T? = null, // only exists in credit api
  @Json(name = "status_message") val message: String? = null,
  @Json(name = "status_code") val code: Int? = null)