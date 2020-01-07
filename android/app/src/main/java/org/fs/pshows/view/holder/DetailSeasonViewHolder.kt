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

package org.fs.pshows.view.holder

import android.view.View
import android.view.ViewGroup
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.view_detail_season_item.view.*
import org.fs.architecture.mvi.util.EMPTY
import org.fs.architecture.mvi.util.inflate
import org.fs.pshows.R
import org.fs.pshows.common.glide.GlideRequests
import org.fs.pshows.common.image.ImageBuilder
import org.fs.pshows.model.entity.Extra
import org.fs.pshows.model.entity.Season

class DetailSeasonViewHolder(view: View,
  private val glide: GlideRequests,
  private val imageBuilder: ImageBuilder): BaseDetailViewHolder(view) {

  private val disposeBag by lazy { CompositeDisposable() }

  private val viewImagePoster by lazy { itemView.viewImagePoster }
  private val viewTextTitle by lazy { itemView.viewTextTitle }
  private val viewTextOverview by lazy { itemView.viewTextOverview }

  constructor(parent: ViewGroup, glide: GlideRequests, imageBuilder: ImageBuilder): this(parent.inflate(R.layout.view_detail_season_item), glide, imageBuilder)

  override fun bind(value: Extra) {
    if (value is Season) {
      val posterUrl = imageBuilder.createPosterUri(path = value.posterPath ?: String.EMPTY)

      glide.clear(viewImagePoster)
      glide.load(posterUrl)
        .applyCrop()
        .into(viewImagePoster)

      viewTextTitle.text = value.name
      viewTextOverview.text = value.overview
    }
  }

  override fun unbind() = disposeBag.clear()
}