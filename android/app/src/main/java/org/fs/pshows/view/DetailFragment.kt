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

import android.content.Context
import android.os.Bundle
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.view_detail_fragment.*
import org.fs.architecture.mvi.common.*
import org.fs.architecture.mvi.core.AbstractFragment
import org.fs.architecture.mvi.util.ObservableList
import org.fs.architecture.mvi.util.plusAssign
import org.fs.pshows.R
import org.fs.pshows.common.device.DeviceContext
import org.fs.pshows.common.device.DeviceContextImp
import org.fs.pshows.common.navigation.Navigator
import org.fs.pshows.common.widget.SafeLinearLayoutManager
import org.fs.pshows.model.DetailFragmentModel
import org.fs.pshows.model.entity.*
import org.fs.pshows.model.event.*
import org.fs.pshows.util.*
import org.fs.pshows.util.C.Companion.BUNDLE_EXTRA_SHOW
import org.fs.pshows.util.C.Companion.DETAIL_TYPE_SIMILAR
import org.fs.pshows.util.C.Companion.RECYCLER_CACHE_SIZE
import org.fs.pshows.util.Operations.Companion.LOAD_DETAIL_CREDIT
import org.fs.pshows.util.Operations.Companion.LOAD_DETAIL_IMAGE
import org.fs.pshows.util.Operations.Companion.LOAD_DETAIL_SIMILARS
import org.fs.pshows.util.Operations.Companion.LOAD_DETAIL_SPOT
import org.fs.pshows.util.Operations.Companion.LOAD_DETAIL_VIDEO
import org.fs.pshows.view.adapter.DetailAdapter
import org.fs.pshows.vm.DetailFragmentViewModel
import javax.inject.Inject

class DetailFragment: AbstractFragment<DetailFragmentModel, DetailFragmentViewModel>(), DetailFragmentView {

  companion object {

    @JvmStatic fun newInstance(show: Show): DetailFragment = DetailFragment().apply {
      arguments = Bundle().apply {
        putParcelable(BUNDLE_EXTRA_SHOW, show)
      }
    }
  }

  @Inject lateinit var navigator: Navigator<Show>
  @Inject lateinit var appContext: Context
  @Inject lateinit var detailAdapter: DetailAdapter
  @Inject lateinit var dataSet: ObservableList<Extra>

  private val deviceContext by lazy { DeviceContextImp.shared(appContext) }

  private val verticalDrawable by lazy { ResourcesCompat.getDrawable(resources, R.drawable.ic_vertical_divider, context?.theme) }

  override val layoutRes: Int get() = R.layout.view_detail_fragment

  private var show = Show.EMPTY

  override fun setUp(state: Bundle?) {
    show = state?.getParcelable(BUNDLE_EXTRA_SHOW) ?: Show.EMPTY

    viewRecycler.apply {
      setItemViewCacheSize(RECYCLER_CACHE_SIZE)
      layoutManager = SafeLinearLayoutManager(context,  LinearLayoutManager.VERTICAL)
      adapter = detailAdapter
      addDecoration(verticalDrawable)
    }
    viewSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent)
    viewSwipeRefreshLayout.isEnabled = false
  }

  override fun attach() {
    super.attach()

    disposeBag += BusManager.add(Consumer { event -> when(event) {
        is SelectShowEvent -> selectShow(event.show)
      }
    })

    disposeBag += viewModel.state()
      .map { state ->
        if (state is Operation) {
          return@map state.type == LOAD_DETAIL_SPOT // we only show indicator on this state
        }
        return@map false
      }
      .subscribe(viewSwipeRefreshLayout::bindProgress)

    disposeBag += viewModel.storage()
      .subscribe(::render)

    checkIfInitialLoadNeeded()
  }

  override fun render(model: DetailFragmentModel) = when(model.state) {
    is Idle -> Unit
    is Failure -> log(model.state.error)
    is Operation -> when(model.state.type) {
      LOAD_DETAIL_SPOT -> renderSpot(model.data)
      LOAD_DETAIL_CREDIT -> renderExtra(R.string.str_title_credit, LoadDetailImageEvent(show.id), model.data, !model.state.initialState)
      LOAD_DETAIL_IMAGE -> renderExtra(R.string.str_title_images, LoadDetailVideoEvent(show.id), model.data, !model.state.initialState)
      LOAD_DETAIL_VIDEO -> renderExtra(R.string.str_title_videos, LoadDetailSimilarEvent(show.id), model.data, !model.state.initialState)
      LOAD_DETAIL_SIMILARS -> renderExtra(R.string.str_title_similar_series, data = model.data, checkNextIfNotInitialState = !model.state.initialState)
      else -> Unit
    }
  }

  private fun checkIfInitialLoadNeeded() {
    if (show != Show.EMPTY && dataSet.isEmpty()) {
      accept(LoadDetailSpotEvent(show.id))
    }
  }

  private fun renderSpot(data: List<Extra>) {
    val spot = data.firstOrNull() ?: ShowExtra.EMPTY
    if (spot != ShowExtra.EMPTY) {
      val title = Title(getString(R.string.str_title_seasons))
      if (spot is ShowExtra) {
        // spot
        dataSet.add(spot.copy(show = show))
        // title and season
        dataSet.add(title)
        dataSet.addAll(spot.seasons ?: emptyList())
      }
      accept(LoadDetailCreditEvent(show.id))
    }
  }

  private fun renderExtra(stringRes: Int, event: Event = NothingEvent(), data: List<Extra>, checkNextIfNotInitialState: Boolean) {
    if (data.isNotEmpty()) {
      val title = Title(getString(stringRes))
      dataSet.add(title)
      if (data.toTypeOrDefault() != DETAIL_TYPE_SIMILAR) {
        val item = DetailExtra(type = data.toTypeOrDefault(), extra = data)
        dataSet.add(item)
      } else {
        dataSet.addAll(data)
      }
      // load next if it is not empty
      accept(event)
    } else {
      if (checkNextIfNotInitialState) {
        accept(event)
      }
    }
  }

  private fun selectShow(show: Show) {
    if (deviceContext.isTabletDevice) {
      if (this.show != show) {
        dataSet.clear()
        this.show = show
        checkIfInitialLoadNeeded()
      }
    } else {
      resolveActivity(DetailActivity::class.java, Bundle().apply {
        putParcelable(BUNDLE_EXTRA_SHOW, show)
      })
    }
  }
}