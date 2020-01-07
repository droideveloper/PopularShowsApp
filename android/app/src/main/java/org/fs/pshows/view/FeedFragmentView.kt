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

package org.fs.pshows.view

import io.reactivex.Observable
import org.fs.architecture.mvi.common.View
import org.fs.pshows.model.FeedFragmentModel
import org.fs.pshows.model.event.LoadFeedEvent
import org.fs.pshows.model.event.LoadMoreFeedEvent

interface FeedFragmentView: View {
  fun render(model: FeedFragmentModel)
  fun bindLoadFeedEvent(): Observable<LoadFeedEvent>
  fun bindLoadMoreFeedEvent(): Observable<LoadMoreFeedEvent>
}