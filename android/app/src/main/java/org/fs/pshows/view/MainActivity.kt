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
import androidx.core.view.ViewCompat
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.view_main_activity.*
import org.fs.architecture.mvi.common.BusManager
import org.fs.architecture.mvi.common.Navigation
import org.fs.architecture.mvi.core.AbstractActivity
import org.fs.architecture.mvi.util.plusAssign
import org.fs.pshows.R
import org.fs.pshows.common.device.DeviceContext
import org.fs.pshows.common.device.DeviceContextImp
import org.fs.pshows.common.navigation.Navigator
import org.fs.pshows.model.MainActivityModel
import org.fs.pshows.model.entity.Show
import org.fs.pshows.model.event.SelectShowEvent
import org.fs.pshows.vm.MainActivityViewModel
import javax.inject.Inject

class MainActivity : AbstractActivity<MainActivityModel, MainActivityViewModel>(),
  MainActivityView {

  @Inject lateinit var navigator: Navigator<Show>

  private val deviceContext by lazy { DeviceContextImp.shared(this) }

  override val layoutRes: Int get() = R.layout.view_main_activity

  override fun onCreate(savedInstanceState: Bundle?) {
    deviceContext.requestOrientation(this)
    overridePendingTransition(R.anim.translate_bottom_in, 0)
    super.onCreate(savedInstanceState)
    if (deviceContext.isTabletDevice) {
      ViewCompat.setElevation(viewToolbar, 4f)
    }
  }

  override fun attach() {
    super.attach()

    disposeBag += BusManager.add(Consumer { event -> when(event) {
        is SelectShowEvent -> navigator.navigate(this, event.show)
      }
    })
  }

  override fun setUp(state: Bundle?) = Unit

  override fun onBackPressed() = finish()

  override fun finish() {
    super.finish()
    overridePendingTransition(R.anim.translate_bottom_out, 0)
  }
} 