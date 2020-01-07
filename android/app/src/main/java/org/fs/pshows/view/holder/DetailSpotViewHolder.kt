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
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposables
import kotlinx.android.synthetic.main.view_detail_spot_item.view.*
import org.fs.architecture.mvi.common.BusManager
import org.fs.architecture.mvi.util.EMPTY
import org.fs.architecture.mvi.util.inflate
import org.fs.architecture.mvi.util.plusAssign
import org.fs.pshows.R
import org.fs.pshows.common.db.ShowDaoProxy
import org.fs.pshows.common.glide.GlideRequests
import org.fs.pshows.common.image.ImageBuilder
import org.fs.pshows.model.entity.Extra
import org.fs.pshows.model.entity.Show
import org.fs.pshows.model.entity.ShowExtra
import org.fs.pshows.model.event.BookmarkFeedEvent
import org.fs.pshows.util.C.Companion.IMAGE_TYPE_LARGE
import org.fs.pshows.util.async
import org.fs.pshows.util.toGenreText
import org.fs.rx.extensions.util.clicks

class DetailSpotViewHolder(view: View,
  private val glide: GlideRequests,
  private val imageBuilder: ImageBuilder,
  private val proxy: ShowDaoProxy): BaseDetailViewHolder(view) {

  private val viewImageBackdrop by lazy { itemView.viewImageBackdrop }
  private val viewImagePoster by lazy { itemView.viewImagePoster }

  private val viewButtonBookmark by lazy { itemView.viewButtonBookmark }

  private val viewTextTitle by lazy { itemView.viewTextTitle }
  private val viewTextGenre by lazy { itemView.viewTextGenre }
  private val viewTextOverview by lazy { itemView.viewTextOverview }

  private var disposable = Disposables.empty()
  private val disposeBag by lazy { CompositeDisposable() }

  private var show = Show.EMPTY

  constructor(parent: ViewGroup, glide: GlideRequests, imageBuilder: ImageBuilder, proxy: ShowDaoProxy): this(parent.inflate(R.layout.view_detail_spot_item), glide, imageBuilder, proxy)

  override fun bind(value: Extra) {
    if (value is ShowExtra) {

      this.show = value.show ?: Show.EMPTY

      val posterUrl = imageBuilder.createPosterUri(path = value.posterPath ?: String.EMPTY)
      glide.clear(viewImagePoster)
      glide.load(posterUrl)
        .applyCrop()
        .into(viewImagePoster)

      val backdropUrl = imageBuilder.createPosterUri(imageType = IMAGE_TYPE_LARGE, path = value.backdropPath ?: String.EMPTY)
      glide.clear(viewImageBackdrop)
      glide.load(backdropUrl)
        .applyCrop()
        .into(viewImageBackdrop)

      viewTextTitle.text = value.name
      viewTextOverview.text = value.overview
      viewTextGenre.text = value.genres?.toGenreText()

      val id = value.id ?: 0L
      disposeBag += checkIfBookmarked(id).subscribe(::renderState)
      disposable = bindBookmarkOrUnbookmarkEvent(show).subscribe(BusManager.Companion::send)
    }
  }

  private fun checkIfBookmarked(id: Long): Observable<Show> = proxy.shows()
    .async()
    .map { it.firstOrNull { s -> s.id == id } ?: Show.EMPTY }
    .onErrorReturn { Show.EMPTY }

  private fun bindBookmarkOrUnbookmarkEvent(value: Show): Observable<BookmarkFeedEvent> = viewButtonBookmark.clicks()
    .map { BookmarkFeedEvent(value) }

  override fun unbind() = disposeBag.clear().also {
    disposable.dispose()
  }

  private fun renderState(value: Show) {
    val isEmpty = value == Show.EMPTY
    viewButtonBookmark.isSelected = !isEmpty
    disposable.dispose()
    val entity = if (isEmpty) show else value
    disposable = bindBookmarkOrUnbookmarkEvent(entity).subscribe(BusManager.Companion::send)
  }
}