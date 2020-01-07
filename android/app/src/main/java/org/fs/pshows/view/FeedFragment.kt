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
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.Observable
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.view_feed_fragment.*
import org.fs.architecture.mvi.common.BusManager
import org.fs.architecture.mvi.common.Failure
import org.fs.architecture.mvi.common.Idle
import org.fs.architecture.mvi.common.Operation
import org.fs.architecture.mvi.core.AbstractFragment
import org.fs.architecture.mvi.util.ObservableList
import org.fs.architecture.mvi.util.plusAssign
import org.fs.pshows.R
import org.fs.pshows.common.device.DeviceContext
import org.fs.pshows.common.device.DeviceContextImp
import org.fs.pshows.common.widget.SafeLinearLayoutManager
import org.fs.pshows.model.FeedFragmentModel
import org.fs.pshows.model.entity.Show
import org.fs.pshows.model.event.BookmarkFeedEvent
import org.fs.pshows.model.event.LoadFeedEvent
import org.fs.pshows.model.event.LoadMoreFeedEvent
import org.fs.pshows.model.event.SelectShowEvent
import org.fs.pshows.util.C.Companion.RECYCLER_CACHE_SIZE
import org.fs.pshows.util.Operations.Companion.CHANGE_CHECK
import org.fs.pshows.util.Operations.Companion.LOAD
import org.fs.pshows.util.Operations.Companion.LOAD_MORE
import org.fs.pshows.util.addDecoration
import org.fs.pshows.util.bindProgress
import org.fs.pshows.view.adapter.FeedAdapter
import org.fs.pshows.vm.FeedFragmentViewModel
import org.fs.rx.extensions.v4.util.refreshes
import org.fs.rx.extensions.v7.util.loadMore
import javax.inject.Inject

class FeedFragment: AbstractFragment<FeedFragmentModel, FeedFragmentViewModel>(), FeedFragmentView {

  @Inject lateinit var feedAdapter: FeedAdapter
  @Inject lateinit var dataSet: ObservableList<Show>
  @Inject lateinit var appContext: Context

   private val deviceContext by lazy { DeviceContextImp.shared(appContext) }

  override val layoutRes: Int get() = R.layout.view_feed_fragment

  private var page = 1
  private var totalPage = 1

  private val verticalDrawable by lazy { ResourcesCompat.getDrawable(resources, R.drawable.ic_vertical_divider, context?.theme) }

  override fun setUp(state: Bundle?) {
    viewRecycler.apply {
      setItemViewCacheSize(RECYCLER_CACHE_SIZE)
      layoutManager = SafeLinearLayoutManager(context, LinearLayoutManager.VERTICAL)
      adapter = feedAdapter
      addDecoration(verticalDrawable, DividerItemDecoration.VERTICAL)
    }
    viewSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent)
  }

  override fun attach() {
    super.attach()

    disposeBag += BusManager.add(Consumer { event -> when(event) {
        is BookmarkFeedEvent -> accept(event)
      }
    })

    // load more case
    disposeBag += viewModel.state()
      .map { state ->
        if (state is Operation) {
          return@map state.type == LOAD_MORE
        }
        return@map false
      }
      .subscribe(::loadMore)

    // swipe refresh case
    disposeBag += viewModel.state()
      .map { state ->
        if (state is Operation) {
          return@map state.type == LOAD
        }
        return@map false
      }
      .subscribe(viewSwipeRefreshLayout::bindProgress)

    disposeBag += bindLoadFeedEvent().subscribe(::accept)
    disposeBag += bindLoadMoreFeedEvent().subscribe(::accept)

    // render model
    disposeBag += viewModel.storage()
      .subscribe(::render)

    // check if load needs
    checkIfInitialLoadNeeded()
  }

  override fun render(model: FeedFragmentModel) = when(model.state) {
    is Idle -> Unit
    is Failure -> Unit
    is Operation -> when(model.state.type) {
      LOAD, LOAD_MORE -> render(model.data).also {
        page = model.page
        totalPage = model.totalPage
      }
      CHANGE_CHECK -> Unit // TODO implement this
      else -> Unit
    }
  }

  override fun bindLoadFeedEvent(): Observable<LoadFeedEvent> = viewSwipeRefreshLayout.refreshes()
    .doOnNext { clear() }
    .map { LoadFeedEvent() }

  override fun bindLoadMoreFeedEvent(): Observable<LoadMoreFeedEvent> = viewRecycler.loadMore(visibleThreshold = 2)
    .filter { page < totalPage && dataSet.isNotEmpty() }
    .doOnNext { page += 1 }
    .map { LoadMoreFeedEvent(page) }

  private fun loadMore(showLoadMore: Boolean) {
    if (showLoadMore) {
      val index = dataSet.indexOfFirst { s -> s == Show.EMPTY }
      if (index == -1) {
        dataSet.add(Show.EMPTY)
      }
    } else {
      dataSet.remove(Show.EMPTY)
    }
  }

  private fun render(data: List<Show>) {
    if (data.isNotEmpty()) {
      if (deviceContext.isTabletDevice && dataSet.isEmpty()) {
        BusManager.send(SelectShowEvent(data.firstOrNull() ?: Show.EMPTY))
      }
      dataSet.addAll(data)
    }
  }

  private fun clear() {
    dataSet.clear()
    page = 0
    totalPage = 0
  }

  private fun checkIfInitialLoadNeeded() {
    if (dataSet.isEmpty()) {
      accept(LoadFeedEvent())
    }
  }
}