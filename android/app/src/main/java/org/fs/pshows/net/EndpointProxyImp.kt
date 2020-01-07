/*
 * Open Source Copyright (C) 2019 Fatih, Popular Shows Android Kotlin.
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

package org.fs.pshows.net

import io.reactivex.Observable
import org.fs.pshows.model.entity.*
import org.fs.pshows.model.net.Resource
import org.fs.pshows.util.applyResource
import org.fs.pshows.util.toResource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EndpointProxyImp @Inject constructor(private val endpoint: Endpoint): EndpointProxy {

  override fun shows(page: Int): Observable<Resource<List<Show>>> = endpoint.shows(page)
    .toResource()

  override fun shows(showId: Long): Observable<Resource<ShowExtra>> = endpoint.shows(showId)
    .applyResource()

  override fun similars(showId: Long): Observable<Resource<List<Show>>> = endpoint.similars(showId)
    .toResource()

  override fun images(showId: Long): Observable<Resource<List<Image>>> = endpoint.images(showId)
    .toResource()

  override fun videos(showId: Long): Observable<Resource<List<Video>>> = endpoint.videos(showId)
    .toResource()

  override fun credits(showId: Long): Observable<Resource<List<Credit>>> = endpoint.credits(showId)
    .toResource()

  override fun genres(): Observable<Resource<List<Genre>>> = endpoint.genres()
    .toResource()
}