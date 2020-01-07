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

package org.fs.pshows.model.entity

import org.fs.pshows.util.C.Companion.DETAIL_TYPE_SPOT

data class DetailExtra(
  val type: Int? = null,
  val extra: List<Extra>? = null): Extra {

  override fun type(): Int = type ?: DETAIL_TYPE_SPOT // we will not use it if we have it that way

  companion object {
    val EMPTY = DetailExtra()
  }
}