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

package org.fs.pshows.common.db.util

import androidx.room.TypeConverter
import java.util.*

sealed class DbConverters {

  companion object {

    private const val SUFFIX_DIF = "??"

    @JvmStatic @TypeConverter fun longToDate(value: Long?): Date? = value?.let { timestamp -> Date(timestamp) }
    @JvmStatic @TypeConverter fun fromDateToLong(value: Date?): Long? = value?.let { date -> date.time }

    @JvmStatic @TypeConverter fun listToString(value: List<String>?): String? = value?.let { items -> items.joinToString(SUFFIX_DIF) }
    @JvmStatic @TypeConverter fun fromStringToList(value: String?): List<String>? = value?.let { string -> string.split(SUFFIX_DIF) }
  }
}