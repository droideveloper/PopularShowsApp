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

package org.fs.pshows.view.adapter

import android.content.Context
import android.view.ViewGroup
import org.fs.architecture.mvi.common.ForFragment
import org.fs.architecture.mvi.core.AbstractRecyclerViewAdapter
import org.fs.architecture.mvi.util.ObservableList
import org.fs.pshows.common.db.ShowDaoProxy
import org.fs.pshows.common.glide.GlideApp
import org.fs.pshows.common.image.ImageBuilder
import org.fs.pshows.model.entity.Extra
import org.fs.pshows.model.entity.Genre
import org.fs.pshows.util.C.Companion.DETAIL_TYPE_CREDIT
import org.fs.pshows.util.C.Companion.DETAIL_TYPE_IMAGE
import org.fs.pshows.util.C.Companion.DETAIL_TYPE_SEASON
import org.fs.pshows.util.C.Companion.DETAIL_TYPE_SIMILAR
import org.fs.pshows.util.C.Companion.DETAIL_TYPE_SPOT
import org.fs.pshows.util.C.Companion.DETAIL_TYPE_TITLE
import org.fs.pshows.util.C.Companion.DETAIL_TYPE_VIDEO
import org.fs.pshows.view.holder.*
import org.fs.rx.extensions.common.Variable
import java.lang.IllegalArgumentException
import javax.inject.Inject

@ForFragment
class DetailAdapter @Inject constructor(
  context: Context,
  dataSet: ObservableList<Extra>,
  private val imageBuilder: ImageBuilder,
  private val activeGenres: Variable<List<Genre>>,
  private val proxy: ShowDaoProxy): AbstractRecyclerViewAdapter<Extra, BaseDetailViewHolder>(dataSet) {

  private val glide by lazy { GlideApp.with(context) }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseDetailViewHolder = when(viewType) {
    DETAIL_TYPE_SPOT -> DetailSpotViewHolder(parent, glide, imageBuilder, proxy)
    DETAIL_TYPE_TITLE -> DetailTitleViewHolder(parent)
    DETAIL_TYPE_SEASON -> DetailSeasonViewHolder(parent, glide, imageBuilder)
    DETAIL_TYPE_CREDIT -> DetailCreditHorizontalViewHolder(parent, glide, imageBuilder)
    DETAIL_TYPE_IMAGE -> DetailImageHorizontalViewHolder(parent, glide, imageBuilder)
    DETAIL_TYPE_VIDEO -> DetailVideoHorizontalViewHolder(parent, glide, imageBuilder)
    DETAIL_TYPE_SIMILAR -> DetailSimilarViewHolder(parent, glide, imageBuilder, activeGenres, proxy)
    else -> throw IllegalArgumentException("could not recognize viewType for $viewType")
  }

  override fun getItemViewType(position: Int): Int {
    val item = dataSet[position]
    return item.type()
  }
}