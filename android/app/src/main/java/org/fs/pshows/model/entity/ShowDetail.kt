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

@JsonClass(generateAdapter = true)
@Parcelize
data class ShowDetail(
  @Json(name = "backdrop_path") val backdropPath: String? = null,
  @Json(name = "created_by") val createdBy: List<Credit>? = null,
  @Json(name = "episode_run_time") val episodeRunTime: List<Int>? = null,
  @Json(name = "first_air_date") val firstAirDate: Date? = null,
  val genres: List<Genre>? = null,
  val homepage: String? = null,
  val id: Long? = null,
  @Json(name = "in_production") val inProduction: Boolean? = null,
  val languages: List<String>? = null,
  @Json(name = "last_air_date") val lastAirDate: Date? = null,
  @Json(name = "last_episode_to_air") val lastEpisodeToAir: Episode? = null,
  val name: String? = null,
  @Json(name = "next_episode_to_air") val nextEpisodeToAir: Episode? = null,
  val networks: List<Network>? = null,
  @Json(name = "number_of_episodes") val numberOfEpisodes: Int? = null,
  @Json(name = "number_of_seasons") val numberOfSeasons: Int? = null,
  @Json(name = "origin_country") val originCountry: List<String>? = null,
  @Json(name = "original_language") val originalLanguage: String? = null,
  @Json(name = "original_name") val originalName: String? = null,
  val overview: String? = null,
  val popularity: Double? = null,
  @Json(name = "poster_path") val posterPath: String? = null,
  @Json(name = "production_companies") val productionCompanies: List<Company>? = null,
  val seasons: List<Season>? = null,
  val status: String? = null,
  val type: String? = null,
  @Json(name = "vote_average") val voteAverage: Double? = null,
  @Json(name = "vote_count") val voteCount: Long? = null): Parcelable {

  companion object {
    val EMPTY = ShowDetail()
  }
}