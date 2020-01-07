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
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.view_detail_activity.*
import org.fs.architecture.mvi.common.BusManager
import org.fs.architecture.mvi.core.AbstractActivity
import org.fs.architecture.mvi.util.plusAssign
import org.fs.pshows.R
import org.fs.pshows.common.device.DeviceContextImp
import org.fs.pshows.model.DetailActivityModel
import org.fs.pshows.model.entity.Show
import org.fs.pshows.model.event.BookmarkFeedEvent
import org.fs.pshows.util.C.Companion.BUNDLE_EXTRA_SHOW
import org.fs.pshows.vm.DetailActivityViewModel
import org.fs.rx.extensions.v7.util.navigationClicks

class DetailActivity : AbstractActivity<DetailActivityModel, DetailActivityViewModel>(),
  DetailActivityView {

  private val deviceContext by lazy { DeviceContextImp.shared(this) }

  override val layoutRes: Int get() = R.layout.view_detail_activity

  private var show = Show.EMPTY

  override fun onCreate(savedInstanceState: Bundle?) {
    deviceContext.requestOrientation(this)
    overridePendingTransition(R.anim.translate_right_in, R.anim.scale_out)
    super.onCreate(savedInstanceState)
  }

  override fun setUp(state: Bundle?) {
    show = state?.getParcelable(BUNDLE_EXTRA_SHOW) ?: Show.EMPTY

    val fragment = DetailFragment.newInstance(show)

    supportFragmentManager.beginTransaction()
      .replace(R.id.viewContentLayout, fragment)
      .commit()
  }

  override fun attach() {
    super.attach()

    disposeBag += BusManager.add(Consumer { event -> when(event) {
        is BookmarkFeedEvent -> accept(event)
      }
    })

    disposeBag += viewToolbar.navigationClicks()
      .subscribe { finish() }

    disposeBag += viewModel.storage()
      .subscribe() // do not want to consume result
  }

  override fun onBackPressed() = finish()

  override fun finish() {
    super.finish()
    overridePendingTransition(R.anim.scale_in, R.anim.translate_right_out)
  }
} 