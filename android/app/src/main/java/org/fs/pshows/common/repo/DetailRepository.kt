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

interface DetailRepository {

  fun shows(showId: Long): Observable<Resource<ShowExtra>>
  fun similars(showId: Long): Observable<Resource<List<Show>>>

  fun images(showId: Long): Observable<Resource<List<Image>>>
  fun videos(showId: Long): Observable<Resource<List<Video>>>
  fun credits(showId: Long): Observable<Resource<List<Credit>>>
}