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

package org.fs.pshows.common.glide

import com.bumptech.glide.annotation.GlideExtension
import com.bumptech.glide.annotation.GlideOption
import com.bumptech.glide.request.BaseRequestOptions
import org.fs.pshows.R
import org.fs.pshows.util.applyBase

@GlideExtension sealed class PopularShowsGlideExtensions {

  companion object {

    @JvmStatic @GlideOption fun applyCrop(options: BaseRequestOptions<*>): BaseRequestOptions<*> = options.centerCrop()
      .applyBase()

    @JvmStatic @GlideOption fun applyCircularCrop(options: BaseRequestOptions<*>): BaseRequestOptions<*> = options.circleCrop()
      .applyBase(R.drawable.ic_placeholder_oval, R.drawable.ic_error_placeholder_oval)
  }
}