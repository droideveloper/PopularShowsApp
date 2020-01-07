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

package org.fs.pshows.common.db

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import org.fs.pshows.model.entity.Show
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ShowDaoProxyImp @Inject constructor(private val dao: ShowDao): ShowDaoProxy {

  override fun insert(entity: Show): Completable = Completable.fromAction { dao.insert(entity) }
  override fun update(entity: Show): Completable = Completable.fromAction { dao.update(entity) }
  override fun delete(entity: Show): Completable = Completable.fromAction { dao.delete(entity) }
  override fun delete(id: Long): Completable = Completable.fromAction { dao.delete(id) }

  override fun show(id: Long): Observable<Show> = dao.show(id)
  override fun shows(): Observable<List<Show>> = dao.shows()
}