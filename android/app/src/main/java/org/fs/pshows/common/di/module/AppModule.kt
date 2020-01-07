/*
 * Open Source Copyright (C) 2019 Fatih, Popular Shows Android Kotlin.
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

package org.fs.pshows.common.di.module

import android.app.Application
import android.content.Context
import com.squareup.moshi.JsonAdapter
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import okhttp3.Interceptor
import org.fs.architecture.mvi.common.ForActivity
import org.fs.pshows.App
import org.fs.pshows.common.db.ShowDaoProxy
import org.fs.pshows.common.db.ShowDaoProxyImp
import org.fs.pshows.common.device.DeviceContext
import org.fs.pshows.common.device.DeviceContextImp
import org.fs.pshows.common.image.ImageBuilder
import org.fs.pshows.common.image.ImageBuilderImp
import org.fs.pshows.common.interceptors.AuthInterceptor
import org.fs.pshows.common.interceptors.LogInterceptor
import org.fs.pshows.common.moshi.adapter.StringToDateAdapter
import org.fs.pshows.common.repo.*
import org.fs.pshows.net.EndpointProxy
import org.fs.pshows.net.EndpointProxyImp
import org.fs.pshows.util.C.Companion.AUTH_INTER
import org.fs.pshows.util.C.Companion.LOG_INTER
import org.fs.pshows.view.DetailActivity
import org.fs.pshows.view.LandingActivity
import org.fs.pshows.view.MainActivity
import java.util.*
import javax.inject.Named
import javax.inject.Singleton

@Module
abstract class AppModule {

  @Singleton @Binds abstract fun bindApplication(app: App): Application
  @Singleton @Binds abstract fun bindContext(app: Application): Context
  @Singleton @Binds abstract fun bindImageBuilder(builder: ImageBuilderImp): ImageBuilder
  @Singleton @Binds abstract fun bindDateAdapter(adapter: StringToDateAdapter): JsonAdapter<Date>
  @Singleton @Binds abstract fun bindEndpointProxy(proxy: EndpointProxyImp): EndpointProxy
  @Singleton @Binds abstract fun bindShowDaoProxy(proxy: ShowDaoProxyImp): ShowDaoProxy

  @Singleton @Binds abstract fun bindFeedRepository(repo: FeedRepositoryImp): FeedRepository
  @Singleton @Binds abstract fun bindDetailRepository(repo: DetailRepositoryImp): DetailRepository
  @Singleton @Binds abstract fun bindLocalRepository(repo: LocalRepositoryImp): LocalRepository
  @Singleton @Binds abstract fun bindUtilRepository(repo: UtilRepositoryImp): UtilRepository

  @Named(value = AUTH_INTER) @Singleton @Binds abstract fun bindAuthInterceptor(inter: AuthInterceptor): Interceptor
  @Named(value = LOG_INTER) @Singleton @Binds abstract fun bindLogInterceptor(inter: LogInterceptor): Interceptor

  @ForActivity @ContributesAndroidInjector(modules = [ActivityModule::class])
  abstract fun contributeLandingActivity(): LandingActivity

  @ForActivity @ContributesAndroidInjector(modules = [ActivityModule::class])
  abstract fun contributeMainActivity(): MainActivity

  @ForActivity @ContributesAndroidInjector(modules = [ActivityModule::class])
  abstract fun contributeDetailActivity(): DetailActivity
}