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

package org.fs.pshows.common.repo

import io.reactivex.Observable
import org.fs.pshows.model.entity.*
import org.fs.pshows.model.net.Resource
import org.fs.pshows.net.EndpointProxy
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DetailRepositoryImp @Inject constructor(private val proxy: EndpointProxy): DetailRepository {

  override fun shows(showId: Long): Observable<Resource<ShowExtra>> = proxy.shows(showId)

  override fun similars(showId: Long): Observable<Resource<List<Show>>> = proxy.similars(showId)
  override fun images(showId: Long): Observable<Resource<List<Image>>> = proxy.images(showId)
  override fun videos(showId: Long): Observable<Resource<List<Video>>> = proxy.videos(showId)
  override fun credits(showId: Long): Observable<Resource<List<Credit>>> = proxy.credits(showId)
}