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

package org.fs.pshows.common.image

import android.net.Uri
import org.fs.architecture.mvi.util.EMPTY
import org.fs.pshows.util.C.Companion.IMAGE_TYPE_LARGE
import org.fs.pshows.util.C.Companion.IMAGE_TYPE_ORIGINAL
import org.fs.pshows.util.C.Companion.IMAGE_TYPE_SMALL
import org.fs.pshows.util.C.Companion.IMAGE_URL
import org.fs.pshows.util.C.Companion.YOUTUBE_IMAGE_URL
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImageBuilderImp @Inject constructor(): ImageBuilder {

  override fun createPosterUri(imageType: String, path: String): Uri = when(imageType) {
    IMAGE_TYPE_SMALL, IMAGE_TYPE_LARGE, IMAGE_TYPE_ORIGINAL -> Uri.parse("$IMAGE_URL$imageType$path") ?: Uri.EMPTY
    else -> Uri.EMPTY
  }

  override fun createYoutubeUri(path: String): Uri = when {
    path != String.EMPTY ->  Uri.parse("$YOUTUBE_IMAGE_URL$path/hqdefault.jpg") ?: Uri.EMPTY
    else -> Uri.EMPTY
  }
}