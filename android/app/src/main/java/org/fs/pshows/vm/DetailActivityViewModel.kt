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
package org.fs.pshows.vm

import org.fs.architecture.mvi.common.Event
import org.fs.architecture.mvi.common.ForActivity
import org.fs.architecture.mvi.common.Idle
import org.fs.architecture.mvi.common.Intent
import org.fs.architecture.mvi.core.AbstractViewModel
import org.fs.architecture.mvi.util.EMPTY
import org.fs.pshows.common.db.ShowDao
import org.fs.pshows.model.DetailActivityModel
import org.fs.pshows.model.event.BookmarkFeedEvent
import org.fs.pshows.model.intent.BookmarkDetailIntent
import org.fs.pshows.model.intent.NothingIntent
import org.fs.pshows.view.DetailActivityView
import javax.inject.Inject

@ForActivity
class DetailActivityViewModel @Inject constructor(
  private val dao: ShowDao,
  view: DetailActivityView) :
  AbstractViewModel<DetailActivityModel, DetailActivityView>(view) {

  override fun initState(): DetailActivityModel = DetailActivityModel(state = Idle, data = String.EMPTY)

  override fun toIntent(event: Event): Intent = when (event) {
    is BookmarkFeedEvent -> BookmarkDetailIntent(event.show, dao)
    else -> NothingIntent<DetailActivityModel>() // if we can not resolve event to intent
  }
} 