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

package org.fs.pshows.util

import androidx.annotation.IntDef
import androidx.annotation.StringDef
import org.fs.pshows.util.C.Companion.DETAIL_TYPE_CREDIT
import org.fs.pshows.util.C.Companion.DETAIL_TYPE_IMAGE
import org.fs.pshows.util.C.Companion.DETAIL_TYPE_SEASON
import org.fs.pshows.util.C.Companion.DETAIL_TYPE_SIMILAR
import org.fs.pshows.util.C.Companion.DETAIL_TYPE_SPOT
import org.fs.pshows.util.C.Companion.DETAIL_TYPE_TITLE
import org.fs.pshows.util.C.Companion.DETAIL_TYPE_VIDEO
import org.fs.pshows.util.C.Companion.IMAGE_TYPE_LARGE
import org.fs.pshows.util.C.Companion.IMAGE_TYPE_ORIGINAL
import org.fs.pshows.util.C.Companion.IMAGE_TYPE_SMALL

@Retention(AnnotationRetention.RUNTIME)
@StringDef(value = [IMAGE_TYPE_SMALL, IMAGE_TYPE_LARGE, IMAGE_TYPE_ORIGINAL])
annotation class ImageType

@Retention(AnnotationRetention.RUNTIME)
@IntDef(value = [DETAIL_TYPE_SPOT, DETAIL_TYPE_CREDIT, DETAIL_TYPE_SEASON, DETAIL_TYPE_SIMILAR, DETAIL_TYPE_IMAGE, DETAIL_TYPE_VIDEO, DETAIL_TYPE_TITLE])
annotation class DetailViewType
