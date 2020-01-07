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
import org.fs.pshows.model.entity.Genre
import org.fs.pshows.model.entity.Show
import org.fs.pshows.util.C.Companion.VIEW_TYPE_PROGRESS
import org.fs.pshows.util.C.Companion.VIEW_TYPE_SIMPLE
import org.fs.pshows.view.holder.BaseFeedViewHolder
import org.fs.pshows.view.holder.FeedProgressViewHolder
import org.fs.pshows.view.holder.FeedSimpleViewHolder
import org.fs.rx.extensions.common.Variable
import java.lang.IllegalArgumentException
import javax.inject.Inject

@ForFragment
class FeedAdapter @Inject constructor(
  context: Context,
  dataSet: ObservableList<Show>,
  private val imageBuilder: ImageBuilder,
  private val activeGenres: Variable<List<Genre>>,
  private val proxy: ShowDaoProxy): AbstractRecyclerViewAdapter<Show, BaseFeedViewHolder>(dataSet)  {

  private val glide by lazy { GlideApp.with(context) }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseFeedViewHolder = when(viewType) {
    VIEW_TYPE_PROGRESS -> FeedProgressViewHolder(parent)
    VIEW_TYPE_SIMPLE -> FeedSimpleViewHolder(parent, glide, imageBuilder, activeGenres, proxy)
    else -> throw IllegalArgumentException("can not recognize viewType for $viewType")
  }

  override fun getItemViewType(position: Int): Int {
    val item = dataSet[position]
    return when (item) {
      Show.EMPTY -> VIEW_TYPE_PROGRESS
      else -> VIEW_TYPE_SIMPLE
    }
  }
}