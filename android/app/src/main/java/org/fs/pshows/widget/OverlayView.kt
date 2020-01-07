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
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import androidx.core.content.res.ResourcesCompat
import org.fs.pshows.R
import org.fs.pshows.common.widget.AspectManagerImp

class OverlayView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, style: Int = 0): View(context, attrs, style) {

  private val aspectManager by lazy { AspectManagerImp.shared() }

  private var colorOverlay = Color.WHITE // have to set default for good

  private val paint by lazy { Paint(Paint.ANTI_ALIAS_FLAG).apply {
      color = colorOverlay
    }
  }

  private val path by lazy { Path().apply {
      moveTo(0f, height * 2 / 3f)
      lineTo(width * 1f, height * 31 / 32f)
      lineTo(width * 1f, height * 1f)
      lineTo(0f, height * 1f)
      lineTo(0f, height * 2 / 3f)
      close()
    }
  }

  private val index: Int

  init {
    val a = context.obtainStyledAttributes(attrs, R.styleable.OverlayView, 0, 0)
    index = if (a.hasValue(R.styleable.OverlayView_overlay_ratio)) {
      a.getInt(R.styleable.OverlayView_overlay_ratio, -1)
    } else {
      -1
    }
    a.recycle()

    colorOverlay = ResourcesCompat.getColor(resources, R.color.colorOverlay, context.theme)
  }

  override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
    val measurement = aspectManager.apply(index, widthMeasureSpec, heightMeasureSpec)
    val (newWidthMeasureSpec, newHeightMeasureSpec) = measurement
    super.onMeasure(newWidthMeasureSpec, newHeightMeasureSpec)
  }

  override fun draw(canvas: Canvas?) {
    super.draw(canvas)
    canvas?.drawPath(path, paint)
  }
}