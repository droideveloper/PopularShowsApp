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
import kotlinx.android.synthetic.main.view_detail_similar_item.view.*
import org.fs.architecture.mvi.common.BusManager
import org.fs.architecture.mvi.util.EMPTY
import org.fs.architecture.mvi.util.inflate
import org.fs.architecture.mvi.util.plusAssign
import org.fs.pshows.R
import org.fs.pshows.common.db.ShowDaoProxy
import org.fs.pshows.common.glide.GlideRequests
import org.fs.pshows.common.image.ImageBuilder
import org.fs.pshows.model.entity.Extra
import org.fs.pshows.model.entity.Genre
import org.fs.pshows.model.entity.Show
import org.fs.pshows.model.event.BookmarkFeedEvent
import org.fs.pshows.model.event.SelectShowEvent
import org.fs.pshows.util.async
import org.fs.pshows.util.toGenreText
import org.fs.rx.extensions.common.Variable
import org.fs.rx.extensions.util.clicks
import java.util.*

class DetailSimilarViewHolder(view: View,
  private val glide: GlideRequests,
  private val imageBuilder: ImageBuilder,
  private val activeGenres: Variable<List<Genre>>,
  private val proxy: ShowDaoProxy): BaseDetailViewHolder(view) {

  private val viewImagePoster by lazy { itemView.viewImagePoster }
  private val viewTextTitle by lazy { itemView.viewTextTitle }
  private val viewTextOverview by lazy { itemView.viewTextOverview }

  private val viewButtonBookmark by lazy { itemView.viewButtonBookmark }

  private val viewTextGenre by lazy { itemView.viewTextGenre }
  private val viewTextAverage by lazy { itemView.viewTextAverage }

  private val disposeBag by lazy { CompositeDisposable() }
  private var disposable = Disposables.empty()

  private var show = Show.EMPTY

  constructor(parent: ViewGroup, glide: GlideRequests, imageBuilder: ImageBuilder, activeGenres: Variable<List<Genre>>, proxy: ShowDaoProxy): this(parent.inflate(R.layout.view_detail_similar_item), glide, imageBuilder, activeGenres, proxy)

  override fun bind(value: Extra) {
    if (value is Show) {
      this.show = value

      val path = value.posterPath ?: String.EMPTY
      val posterUrl = imageBuilder.createPosterUri(path = path)

      glide.clear(viewImagePoster)
      glide.load(posterUrl)
        .applyCrop()
        .into(viewImagePoster)

      viewTextTitle.text = value.name

      viewTextAverage.text = String.format(Locale.getDefault(), "%.2f", value.voteAverage)

      val genreIds = value.genreIds ?: LongArray(0)
      viewTextGenre.text = activeGenres.value.filter { g -> genreIds.contains(g.id ?: 0L) }.toGenreText()

      viewTextOverview.text = value.overview

      val id = value.id ?: 0L
      disposeBag += checkIfBookmarked(id).subscribe(::renderState)
      disposable = bindBookmarkOrUnbookmarkEvent(value).subscribe(BusManager.Companion::send)
      disposeBag += bindSelectShowEvent(value).subscribe(BusManager.Companion::send)
    }
  }

  private fun bindSelectShowEvent(value: Show): Observable<SelectShowEvent> = itemView.clicks()
    .map { SelectShowEvent(value) }

  private fun bindBookmarkOrUnbookmarkEvent(value: Show): Observable<BookmarkFeedEvent> = viewButtonBookmark.clicks()
    .map { BookmarkFeedEvent(value) }

  private fun checkIfBookmarked(id: Long): Observable<Show> = proxy.shows()
    .async()
    .map { it.firstOrNull { s -> s.id == id } ?: Show.EMPTY }
    .onErrorReturn { Show.EMPTY }

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