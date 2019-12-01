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
import java.util.*

@Parcelize
@JsonClass(generateAdapter = true)
data class Season(
  @Json(name = "air_date") val airDate: Date? = null,
  @Json(name = "episode_count") val episodeCount: Int? = null,
  val id: Long? = null,
  val name: String? = null,
  val overview: String? = null,
  @Json(name = "poster_path") val posterPath: String? = null,
  @Json(name = "season_number") val seasonNumber: Int? = null): Parcelable {

  /*
     {
      "air_date": "2010-12-05",
      "episode_count": 14,
      "id": 3627,
      "name": "Specials",
      "overview": "",
      "poster_path": "/kMTcwNRfFKCZ0O2OaBZS0nZ2AIe.jpg",
      "season_number": 0
    }
   */

  companion object {
    val EMPTY = Season()
  }
}