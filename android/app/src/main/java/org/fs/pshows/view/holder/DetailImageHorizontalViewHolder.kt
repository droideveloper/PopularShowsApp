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

package org.fs.pshows.view.holder

import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.view_detail_image_horizontal_item.view.*
import kotlinx.android.synthetic.main.view_detail_image_item.view.*
import org.fs.architecture.mvi.core.AbstractRecyclerViewAdapter
import org.fs.architecture.mvi.core.AbstractRecyclerViewHolder
import org.fs.architecture.mvi.util.EMPTY
import org.fs.architecture.mvi.util.ObservableList
import org.fs.architecture.mvi.util.inflate
import org.fs.pshows.R
import org.fs.pshows.common.glide.GlideRequests
import org.fs.pshows.common.image.ImageBuilder
import org.fs.pshows.common.widget.SafeLinearLayoutManager
import org.fs.pshows.model.entity.DetailExtra
import org.fs.pshows.model.entity.Extra
import org.fs.pshows.model.entity.Image
import org.fs.pshows.util.C.Companion.IMAGE_TYPE_LARGE
import org.fs.pshows.util.C.Companion.RECYCLER_CACHE_SIZE
import org.fs.pshows.util.addDecoration

class DetailImageHorizontalViewHolder(view: View,
  private val glide: GlideRequests,
  private val imageBuilder: ImageBuilder): BaseDetailViewHolder(view) {


  private val dataSet by lazy { ObservableList<Extra>() }
  private val imageAdapter by lazy { ImageAdapter(glide, imageBuilder, dataSet) }

  private val viewRecycler by lazy { itemView.viewRecycler }

  private val disposeBag by lazy { CompositeDisposable() }

  private val context by lazy { itemView.context }
  private val resources by lazy { context.resources }

  private val horizontalDrawable by lazy { ResourcesCompat.getDrawable(resources, R.drawable.ic_horizontal_divider,  context.theme) }

  constructor(parent: ViewGroup, glide: GlideRequests, imageBuilder: ImageBuilder): this(parent.inflate(R.layout.view_detail_image_horizontal_item), glide, imageBuilder)

  init {
    viewRecycler.apply {
      setItemViewCacheSize(RECYCLER_CACHE_SIZE)
      layoutManager = SafeLinearLayoutManager(context, LinearLayoutManager.HORIZONTAL)
      adapter = imageAdapter
      addDecoration(horizontalDrawable, DividerItemDecoration.HORIZONTAL)
    }
  }

  override fun bind(value: Extra) {
    if (value is DetailExtra) {
      dataSet.clear()
      val data = value.extra ?: emptyList()
      if (data.isNotEmpty()) {
        if (dataSet.isEmpty())  {
          dataSet.addAll(data)
        }
      }
    }
  }

  override fun unbind() = disposeBag.clear()

  // image adapter
  internal class ImageAdapter(
    private val glide: GlideRequests,
    private val imageBuilder: ImageBuilder,
    dataSet: ObservableList<Extra>): AbstractRecyclerViewAdapter<Extra,  ImageViewHolder>(dataSet) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder = ImageViewHolder(parent, glide, imageBuilder)
  }

  // image view  holder
  internal class ImageViewHolder(view: View,
    private val glide: GlideRequests,
    private val imageBuilder: ImageBuilder): AbstractRecyclerViewHolder<Extra>(view) {

    private val viewImage by lazy { itemView.viewImage }

    constructor(parent: ViewGroup, glide: GlideRequests, imageBuilder: ImageBuilder): this(parent.inflate(R.layout.view_detail_image_item), glide, imageBuilder)

    override fun bind(value: Extra) {
      if (value is Image) {
        val fileUrl = imageBuilder.createPosterUri(imageType = IMAGE_TYPE_LARGE,  path = value.filePath ?: String.EMPTY)

        glide.clear(viewImage)
        glide.load(fileUrl)
          .applyCrop()
          .into(viewImage)
      }
    }

    override fun unbind() =  Unit
  }
}