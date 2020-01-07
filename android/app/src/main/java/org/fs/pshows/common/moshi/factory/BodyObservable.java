package org.fs.pshows.common.moshi.factory;
/*
 * Copyright (C) 2016 Jake Wharton
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

import com.squareup.moshi.JsonAdapter;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.plugins.RxJavaPlugins;
import okhttp3.ResponseBody;
import org.fs.pshows.common.moshi.factory.moshi.MoshiResponseBodyConverter;
import retrofit2.Converter;
import retrofit2.Response;

final class BodyObservable<T> extends Observable<T> {

  private final Observable<Response<T>> upstream;
  private final JsonAdapter<T> adapter;

  BodyObservable(Observable<Response<T>> upstream, JsonAdapter<T> adapter) {
    this.upstream = upstream;
    this.adapter = adapter;
  }

  @Override protected void subscribeActual(Observer<? super T> observer) {
    upstream.subscribe(new BodyObserver<>(observer, new MoshiResponseBodyConverter<>(adapter)));
  }

  private static class BodyObserver<R> implements Observer<Response<R>> {

    private final Observer<? super R> observer;
    private final Converter<ResponseBody, R> converter;
    private boolean terminated;

    BodyObserver(Observer<? super R> observer, Converter<ResponseBody, R> converter) {
      this.observer = observer;
      this.converter = converter;
    }

    @Override public void onSubscribe(Disposable disposable) {
      observer.onSubscribe(disposable);
    }

    @Override public void onNext(Response<R> response) {
      if (response.isSuccessful()) {
        observer.onNext(response.body());
      } else {
        try {
          final ResponseBody body = response.errorBody();
          if (body != null) {
            final R errorResponse = converter.convert(body);
            observer.onNext(errorResponse);
          } else {
            throw new IllegalArgumentException("no body exist for error or success");
          }
        } catch (Throwable error) {
          try {
            observer.onError(error);
          } catch (Throwable inner) {
            Exceptions.throwIfFatal(inner);
            RxJavaPlugins.onError(new CompositeException(error, inner));
          }
        }
      }
    }

    @Override public void onComplete() {
      if (!terminated) {
        observer.onComplete();
      }
    }

    @Override public void onError(Throwable throwable) {
      if (!terminated) {
        observer.onError(throwable);
      } else {
        // This should never happen! onNext handles and forwards errors automatically.
        Throwable broken = new AssertionError(
            "This should never happen! Report as a bug with the full stacktrace.");
        //noinspection UnnecessaryInitCause Two-arg AssertionError constructor is 1.7+ only.
        broken.initCause(throwable);
        RxJavaPlugins.onError(broken);
      }
    }
  }
}