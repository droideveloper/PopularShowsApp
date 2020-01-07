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

package org.fs.pshows.common.widget

interface AspectManager {

  companion object {
    const val DEFAULT_RATIO = -1f

    const val RATIO_4_3   = 0
    const val RATIO_2_3   = 1
    const val RATIO_1_1   = 2
    const val RATIO_16_9  = 3
    const val RATIO_5_7   = 4
  }

  fun apply(index: Int, widthMeasureSpec: Int, heightMeasureSpec: Int): Pair<Int, Int>
  fun apply(index: Int, itemSize: Float, widthMeasureSpec: Int, heightMeasureSpec: Int): Pair<Int, Int>
}