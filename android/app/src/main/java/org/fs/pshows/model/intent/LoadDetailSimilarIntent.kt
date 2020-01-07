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

package org.fs.pshows.model.intent

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import org.fs.architecture.mvi.common.*
import org.fs.pshows.common.repo.DetailRepository
import org.fs.pshows.model.DetailFragmentModel
import org.fs.pshows.model.entity.Show
import org.fs.pshows.model.net.Resource
import org.fs.pshows.util.Operations.Companion.LOAD_DETAIL_SIMILARS
import java.io.IOException

class LoadDetailSimilarIntent(
  private val showId: Long,
  private val detailRepository: DetailRepository): ObservableIntent<DetailFragmentModel>() {

  override fun invoke(): Observable<Reducer<DetailFragmentModel>> = detailRepository.similars(showId)
    .concatMap(::success)
    .onErrorResumeNext(::failure)
    .startWith(initial())
    .subscribeOn(Schedulers.io())

  private fun success(resource: Resource<List<Show>>): Observable<Reducer<DetailFragmentModel>> = when(resource) {
    is Resource.Success<List<Show>> -> Observable.just(
      { o -> o.copy(state = Operation(LOAD_DETAIL_SIMILARS, false), data = resource.data ?: emptyList()) },
      { o -> o.copy(state = Idle, data = emptyList()) })
    is Resource.Failure<List<Show>> -> Observable.just(
      { o -> o.copy(state = Failure(IOException(resource.message))) },
      { o -> o.copy(state = Idle) })
  }

  private fun failure(error: Throwable): Observable<Reducer<DetailFragmentModel>> = Observable.just(
    { o -> o.copy(state = Failure(error)) },
    { o -> o.copy(state = Idle) })

  private fun initial(): Reducer<DetailFragmentModel> = { o -> o.copy(state = Operation(LOAD_DETAIL_SIMILARS, true), data = emptyList()) }
}