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
import org.fs.architecture.mvi.common.ForFragment
import org.fs.architecture.mvi.common.Idle
import org.fs.architecture.mvi.common.Intent
import org.fs.architecture.mvi.core.AbstractViewModel
import org.fs.pshows.common.db.ShowDao
import org.fs.pshows.common.repo.FeedRepository
import org.fs.pshows.model.FeedFragmentModel
import org.fs.pshows.model.event.BookmarkFeedEvent
import org.fs.pshows.model.event.CheckFeedChangeEvent
import org.fs.pshows.model.event.LoadFeedEvent
import org.fs.pshows.model.event.LoadMoreFeedEvent
import org.fs.pshows.model.intent.*
import org.fs.pshows.view.FeedFragmentView
import javax.inject.Inject

@ForFragment
class FeedFragmentViewModel @Inject constructor(
  view: FeedFragmentView,
  private val dao: ShowDao,
  private val feedRepository: FeedRepository): AbstractViewModel<FeedFragmentModel, FeedFragmentView>(view) {

  override fun initState(): FeedFragmentModel = FeedFragmentModel(state = Idle, data = emptyList(), page = 0, totalPage = 0)

  override fun toIntent(event: Event): Intent = when (event) {
    is BookmarkFeedEvent -> BookmarkFeedIntent(event.show, dao)
    is LoadFeedEvent -> LoadFeedIntent(feedRepository)
    is LoadMoreFeedEvent -> LoadMoreFeedIntent(event.page, feedRepository)
    is CheckFeedChangeEvent -> CheckFeedChangeIntent(feedRepository)
    else -> NothingIntent<FeedFragmentModel>() // if we can not resolve event to intent
  }
} 