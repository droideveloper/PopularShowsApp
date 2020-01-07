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
import org.fs.pshows.common.repo.UtilRepository
import org.fs.pshows.model.LandingActivityModel
import org.fs.pshows.model.event.LoadGenreEvent
import org.fs.pshows.model.intent.LoadGenreIntent
import org.fs.pshows.model.intent.NothingIntent
import org.fs.pshows.view.LandingActivityView
import javax.inject.Inject

@ForActivity
class LandingActivityViewModel @Inject constructor(
  view: LandingActivityView,
  private val utilRepository: UtilRepository): AbstractViewModel<LandingActivityModel, LandingActivityView>(view) {

  override fun initState(): LandingActivityModel = LandingActivityModel(state = Idle,  data = emptyList())

  override fun toIntent(event: Event): Intent = when (event) {
    is LoadGenreEvent -> LoadGenreIntent(utilRepository)
    else -> NothingIntent<LandingActivityModel>() // if we can not resolve event to intent
  }
} 