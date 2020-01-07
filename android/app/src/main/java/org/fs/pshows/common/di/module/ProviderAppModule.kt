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

package org.fs.pshows.common.di.module

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import org.fs.pshows.common.db.LocalStorage
import org.fs.pshows.common.db.ShowDao
import org.fs.pshows.common.device.DeviceContextImp
import org.fs.pshows.common.navigation.HandsetNavigator
import org.fs.pshows.common.navigation.Navigator
import org.fs.pshows.common.navigation.TabletNavigator
import org.fs.pshows.model.entity.Genre
import org.fs.pshows.model.entity.Show
import org.fs.pshows.util.C.Companion.DB_NAME
import org.fs.rx.extensions.common.Variable
import javax.inject.Singleton

@Module
class ProviderAppModule {

  @Singleton @Provides fun provideLocalStorage(context: Context): LocalStorage = Room.databaseBuilder(context, LocalStorage::class.java, DB_NAME)
    .build()

  @Singleton @Provides fun provideShowDao(storage: LocalStorage): ShowDao = storage.showDao()

  @Singleton @Provides fun provideGenre(): Variable<List<Genre>> = Variable(emptyList())

  @Singleton @Provides fun provideNavigator(context: Context): Navigator<Show> {
    val deviceContext = DeviceContextImp.shared(context)
    return when {
      deviceContext.isTabletDevice -> TabletNavigator()
      else -> HandsetNavigator()
    }
  }
}