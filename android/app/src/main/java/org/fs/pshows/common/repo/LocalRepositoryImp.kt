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

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import org.fs.pshows.common.db.ShowDaoProxy
import org.fs.pshows.model.entity.Show
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalRepositoryImp @Inject constructor(private val proxy: ShowDaoProxy): LocalRepository {

  override fun insert(entity: Show): Completable = proxy.insert(entity)
  override fun update(entity: Show): Completable = proxy.update(entity)
  override fun delete(entity: Show): Completable = proxy.delete(entity)

  override fun delete(id: Long): Completable = proxy.delete(id)
  override fun show(id: Long): Observable<Show> = proxy.show(id)

  override fun shows(): Observable<List<Show>> = proxy.shows()
}