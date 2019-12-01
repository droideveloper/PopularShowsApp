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

import android.os.Build
import dagger.Module
import dagger.Provides
import okhttp3.HttpUrl
import org.fs.pshows.BuildConfig
import java.lang.IllegalArgumentException
import javax.inject.Singleton

@Module
class NetworkModule {

  @Singleton @Provides fun provideHttpUrl(): HttpUrl = HttpUrl.parse(BuildConfig.BASE_URL) ?: throw IllegalArgumentException("could not parse url string ${BuildConfig.BASE_URL}")
}