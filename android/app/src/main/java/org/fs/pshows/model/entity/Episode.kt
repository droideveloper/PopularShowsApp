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
data class Episode(
  @Json(name = "air_date") val airDate: Date? = null,
  @Json(name = "episode_number") val episodeNumber: Int? = null,
  val id: Long? = null,
  val name: String? = null,
  val overview: String? = null,
  @Json(name = "production_code") val productionCode: String? = null,
  @Json(name = "season_number") val seasonNumber: Int? = null,
  @Json(name = "show_id") val showId: Long? = null,
  @Json(name = "still_path") val stillPath: String? = null,
  @Json(name = "vote_average") val voteAverage: Double? = null,
  @Json(name = "vote_count") val voteCount: Int? = null): Parcelable {


  /*
  "air_date": "2017-08-27",
    "episode_number": 7,
    "id": 1340528,
    "name": "The Dragon and the Wolf",
    "overview": "A meeting is held in King's Landing. Problems arise in the North.",
    "production_code": "707",
    "season_number": 7,
    "show_id": 1399,
    "still_path": "/jLe9VcbGRDUJeuM8IwB7VX4GDRg.jpg",
    "vote_average": 9.145,
    "vote_count": 31

   */
  companion object {
    val EMPTY = Episode()
  }
}