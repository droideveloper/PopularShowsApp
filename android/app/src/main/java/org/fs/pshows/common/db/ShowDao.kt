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

import androidx.room.*
import io.reactivex.Flowable
import io.reactivex.Observable
import org.fs.pshows.model.entity.Show

@Dao
interface ShowDao {

  @Insert(onConflict = OnConflictStrategy.ABORT) fun insert(entity: Show)
  @Update(onConflict = OnConflictStrategy.REPLACE) fun update(entity: Show)
  @Delete fun delete(entity: Show)

  @Query("SELECT * FROM shows") fun shows(): Observable<List<Show>>
  @Query("SELECT * FROM shows WHERE id = :id") fun show(id: Long): Observable<Show>
  @Query("DELETE FROM Shows WHERE id = :id") fun delete(id: Long)
}