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
import org.fs.pshows.model.net.PagedResponse
import org.fs.pshows.util.C.Companion.POPULAR_SHOWS
import org.fs.pshows.util.C.Companion.SHOW_CREDITS
import org.fs.pshows.util.C.Companion.SHOW_EXTRA
import org.fs.pshows.util.C.Companion.SHOW_IMAGES
import org.fs.pshows.util.C.Companion.SHOW_VIDEOS
import org.fs.pshows.util.C.Companion.SIMILAR_SHOWS
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface Endpoint {

  @GET(POPULAR_SHOWS) fun shows(): Observable<PagedResponse<List<Show>>>
  @GET(SHOW_EXTRA) fun shows(@Path("showId") showId: Long): Observable<Response<ShowExtra>>
  @GET(SIMILAR_SHOWS) fun similars(@Path("showId") showId: Long): Observable<PagedResponse<List<Show>>>

  @GET(SHOW_IMAGES) fun images(@Path("showId") showId: Long): Observable<PagedResponse<List<Image>>>
  @GET(SHOW_VIDEOS) fun videos(@Path("showId") showId: Long): Observable<PagedResponse<List<Video>>>
  @GET(SHOW_CREDITS) fun credits(@Path("showId") showId: Long): Observable<PagedResponse<List<Credit>>>
}