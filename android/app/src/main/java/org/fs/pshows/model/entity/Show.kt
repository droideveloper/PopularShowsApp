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
data class Show(
  @Json(name = "poster_path") val posterPath: String? = null,
  val popularity: Double? = null,
  val id: Long? = null,
  @Json(name = "backdrop_path") val backdropPath: String? = null,
  @Json(name = "vote_average") val voteAverage: Double? = null,
  val overview: String? = null,
  @Json(name = "first_air_date") val firstAirDate: Date? = null,
  @Json(name = "origin_country") val originCountry: List<String>? = null,
  @Json(name = "genre_ids") val genreIds: List<Long>? = null,
  @Json(name = "original_language") val originalLanguage: String? = null,
  @Json(name = "vote_count") val voteCount: Long? = null,
  val name: String? = null,
  @Json(name = "original_name") val originalName: String? = null): Parcelable {

  /*
      {
      "poster_path": "/vC324sdfcS313vh9QXwijLIHPJp.jpg",
      "popularity": 47.432451,
      "id": 31917,
      "backdrop_path": "/rQGBjWNveVeF8f2PGRtS85w9o9r.jpg",
      "vote_average": 5.04,
      "overview": "Based on the Pretty Little Liars series of young adult novels by Sara Shepard, the series follows the lives of four girls — Spencer, Hanna, Aria, and Emily — whose clique falls apart after the disappearance of their queen bee, Alison. One year later, they begin receiving messages from someone using the name \"A\" who threatens to expose their secrets — including long-hidden ones they thought only Alison knew.",
      "first_air_date": "2010-06-08",
      "origin_country": [
        "US"
      ],
      "genre_ids": [
        18,
        9648
      ],
      "original_language": "en",
      "vote_count": 133,
      "name": "Pretty Little Liars",
      "original_name": "Pretty Little Liars"
    }
   */


  companion object  {
    val EMPTY = Show()
  }
}