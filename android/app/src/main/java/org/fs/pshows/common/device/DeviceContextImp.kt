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

package org.fs.pshows.common.device

import android.app.Activity
import android.content.Context
import android.content.pm.ActivityInfo
import org.fs.pshows.R

class DeviceContextImp constructor(context: Context): DeviceContext {

  companion object {

    private var instance: DeviceContext? = null

    @JvmStatic fun shared(context: Context): DeviceContext = instance ?: synchronized(this) {
      instance ?: DeviceContextImp(context).also { instance = it }
    }
  }

  private val resources by lazy { context.resources }

  override val isTabletDevice: Boolean get() = resources.getBoolean(R.bool.isTabletDevice)

  override fun requestOrientation(activity: Activity) {
    if (isTabletDevice) {
      activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
    } else {
      activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }
  }
}