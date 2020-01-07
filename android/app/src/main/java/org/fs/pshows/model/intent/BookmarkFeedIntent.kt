/*
 * Popular Shows Android Kotlin Copyright (C) 2019 Fatih, Open Source.
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

package org.fs.pshows.model.intent

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import org.fs.architecture.mvi.common.*
import org.fs.pshows.common.db.ShowDao
import org.fs.pshows.model.FeedFragmentModel
import org.fs.pshows.model.entity.Show
import org.fs.pshows.util.Operations.Companion.ADD_BOOKMARK
import org.fs.pshows.util.Operations.Companion.REMOVE_BOOKMARK

class BookmarkFeedIntent(
  private val show: Show,
  private val dao: ShowDao): ObservableIntent<FeedFragmentModel>() {

  private val state by lazy { if (show.showId != null) REMOVE_BOOKMARK else ADD_BOOKMARK }

  override fun invoke(): Observable<Reducer<FeedFragmentModel>> = Observable.just(show)
    .doOnNext {
      if (state == REMOVE_BOOKMARK) {
        dao.delete(show)
      } else {
        dao.insert(show)
      }
    }
    .concatMap(::success)
    .onErrorResumeNext(::failure)
    .startWith(initial())
    .subscribeOn(Schedulers.io())

  private fun success(show: Show): Observable<Reducer<FeedFragmentModel>> = Observable.just(
    { o -> o.copy(state = Operation(state, false), bookmarkState = show) },
    { o -> o.copy(state = Idle, bookmarkState = Show.EMPTY) })

  private fun failure(error: Throwable): Observable<Reducer<FeedFragmentModel>> = Observable.just(
    { o -> o.copy(state = Failure(error)) },
    { o -> o.copy(state = Idle) })

  private fun initial(): Reducer<FeedFragmentModel> = { o -> o.copy(state = Operation(state, true)) }
}