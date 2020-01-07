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

import android.util.SparseArray
import android.view.View.*
import org.fs.pshows.common.widget.AspectManager.Companion.DEFAULT_RATIO
import org.fs.pshows.common.widget.AspectManager.Companion.RATIO_16_9
import org.fs.pshows.common.widget.AspectManager.Companion.RATIO_1_1
import org.fs.pshows.common.widget.AspectManager.Companion.RATIO_2_3
import org.fs.pshows.common.widget.AspectManager.Companion.RATIO_4_3
import org.fs.pshows.common.widget.AspectManager.Companion.RATIO_5_7
import kotlin.math.roundToInt

class AspectManagerImp: AspectManager {

  companion object {

    private var instance: AspectManager? = null

    @JvmStatic fun shared(): AspectManager = instance ?: synchronized(this) {
      instance ?: AspectManagerImp().also { instance = it }
    }
  }

  private val ratios = SparseArray<Float>().apply {
    put(RATIO_4_3, 0.75f)
    put(RATIO_2_3, 1.5f)
    put(RATIO_1_1, 1f)
    put(RATIO_16_9, 0.5625f)
    put(RATIO_5_7, 1.4f)
  }

  override fun apply(index: Int, widthMeasureSpec: Int, heightMeasureSpec: Int): Pair<Int, Int> {
    val width = MeasureSpec.getSize(widthMeasureSpec)
    val ratio = ratios[index, DEFAULT_RATIO] // we look up for ratios and if we can not find it we do ask for default value
    val height = when {
      ratio != DEFAULT_RATIO -> (width * ratio).roundToInt()
      else -> MeasureSpec.getSize(heightMeasureSpec)
    }
    return Pair(widthMeasureSpec, MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY))
  }

  override fun apply(index: Int, itemSize: Float, widthMeasureSpec: Int, heightMeasureSpec: Int): Pair<Int, Int> {
    val ratio = ratios[index, DEFAULT_RATIO] // we look up for ratios and if we can not find it we do ask for default value
    val height = when {
      ratio != DEFAULT_RATIO -> (itemSize * ratio).roundToInt()
      else -> MeasureSpec.getSize(heightMeasureSpec)
    }
    return Pair(widthMeasureSpec, MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY))
  }
}