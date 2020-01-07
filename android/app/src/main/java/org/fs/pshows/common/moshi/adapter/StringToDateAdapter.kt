/*
 * Popular Shows Android Kotlin Copyright (C) 2019 Fatih, Open Soruce.
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

package org.fs.pshows.common.moshi.adapter

import com.squareup.moshi.*
import org.fs.pshows.BuildConfig
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StringToDateAdapter @Inject constructor(): JsonAdapter<Date>() {

  companion object {
    private const val DATE_FORMAT = "yyyy-MM-dd"
  }

  private val dateFormat by lazy { SimpleDateFormat(DATE_FORMAT, Locale.getDefault()).apply {
      timeZone = TimeZone.getDefault()
    }
  }

  @FromJson override fun fromJson(reader: JsonReader): Date? {
    try {
      if (reader.peek() == JsonReader.Token.NULL) {
        return reader.nextNull()
      }
      val string = reader.nextString()
      return dateFormat.parse(string)
    } catch (error: Exception) {
      if (BuildConfig.DEBUG) {
        error.printStackTrace()
      }
    }
    return null
  }

  @ToJson override fun toJson(writer: JsonWriter, value: Date?) {
    value?.let { date ->
      val string = dateFormat.format(date)
      writer.value(string)
    }
  }
}