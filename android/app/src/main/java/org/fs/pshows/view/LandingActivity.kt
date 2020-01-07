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

import android.os.Bundle
import kotlinx.android.synthetic.main.view_landing_activity.*
import org.fs.architecture.mvi.common.Failure
import org.fs.architecture.mvi.common.Idle
import org.fs.architecture.mvi.common.Operation
import org.fs.architecture.mvi.core.AbstractActivity
import org.fs.architecture.mvi.util.plusAssign
import org.fs.pshows.R
import org.fs.pshows.common.device.DeviceContextImp
import org.fs.pshows.model.LandingActivityModel
import org.fs.pshows.model.entity.Genre
import org.fs.pshows.model.event.LoadGenreEvent
import org.fs.pshows.util.Operations.Companion.LOAD
import org.fs.pshows.util.bindProgress
import org.fs.pshows.util.resolveActivity
import org.fs.pshows.vm.LandingActivityViewModel
import org.fs.rx.extensions.common.Variable
import javax.inject.Inject

class LandingActivity : AbstractActivity<LandingActivityModel, LandingActivityViewModel>(),
  LandingActivityView {

  @Inject lateinit var activeGenre: Variable<List<Genre>>

  private val deviceContext by lazy { DeviceContextImp.shared(this) }

  override val layoutRes: Int get() = R.layout.view_landing_activity

  override fun onCreate(savedInstanceState: Bundle?) {
    deviceContext.requestOrientation(this)
    super.onCreate(savedInstanceState)
  }

  override fun setUp(state: Bundle?) = Unit

  override fun attach() {
    super.attach()

    disposeBag += viewModel.state()
      .map { state ->
        if (state is Operation) {
          return@map state.type == LOAD
        }
        return@map false
      }
      .subscribe(viewProgress::bindProgress)

    disposeBag += viewModel.storage()
      .subscribe(::render)

    checkIfInitialLoadNeeded()
  }

  override fun onBackPressed() = finish()

  override fun render(model: LandingActivityModel) = when(model.state) {
    is Idle -> Unit
    is Failure -> Unit
    is Operation -> when(model.state.type) {
      LOAD -> render(model.data)
      else -> Unit
    }
  }

  private fun checkIfInitialLoadNeeded() {
    val genres = activeGenre.value
    if (genres.isEmpty()) {
      accept(LoadGenreEvent())
    }
  }

  private fun render(data: List<Genre>) {
    if (data.isNotEmpty()) {
      activeGenre.value = data
      resolveActivity(MainActivity::class.java).also {
        finish()
      }
    }
  }
} 