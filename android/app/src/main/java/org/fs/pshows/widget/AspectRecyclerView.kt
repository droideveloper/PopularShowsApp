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

package org.fs.pshows.widget

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView
import org.fs.pshows.R
import org.fs.pshows.common.widget.AspectManagerImp

class AspectRecyclerView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, style: Int = 0): RecyclerView(context, attrs, style) {

  private val aspectManager by lazy { AspectManagerImp.shared() }

  private val itemSize: Float
  private val index: Int

  init {
    val a = context.obtainStyledAttributes(attrs, R.styleable.AspectRecyclerView, 0, 0)
    index = if (a.hasValue(R.styleable.AspectRecyclerView_item_ratio)) {
      a.getInt(R.styleable.AspectRecyclerView_item_ratio, -1)
    } else {
      -1
    }
    itemSize = if (a.hasValue(R.styleable.AspectRecyclerView_item_size)) {
      a.getDimension(R.styleable.AspectRecyclerView_item_size, 0f)
    } else {
      0f
    }
    a.recycle()
  }

  override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
    val measurement = aspectManager.apply(index, itemSize, widthMeasureSpec, heightMeasureSpec)
    val (newWidthMeasureSpec, newHeightMeasureSpec) = measurement
    super.onMeasure(newWidthMeasureSpec, newHeightMeasureSpec)
  }
}