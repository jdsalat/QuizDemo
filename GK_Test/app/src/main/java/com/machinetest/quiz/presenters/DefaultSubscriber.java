/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.machinetest.quiz.presenters;

/**
 * Default subscriber class to be used for Error handling
 * Created by karun.chennuri on 19-06-2015.
 */
public class DefaultSubscriber<T> extends rx.Subscriber<T> {
    /**
     * Notifies the Observer that the {@link rx.Observable} has finished sending push-based notifications.
     * <p/>
     * The {@link rx.Observable} will not call this method if it calls {@link #onError}.
     */
    @Override
    public void onCompleted() {

    }

    /**
     * Notifies the Observer that the {@link rx.Observable} has experienced an error condition.
     * <p/>
     * If the {@link rx.Observable} calls this method, it will not thereafter call {@link #onNext} or
     * {@link #onCompleted}.
     *
     * @param e the exception encountered by the Observable
     */
    @Override
    public void onError(Throwable e) {

    }

    /**
     * Provides the Observer with a new image_item to observe.
     * <p/>
     * The {@link rx.Observable} may call this method 0 or more times.
     * <p/>
     * The {@code Observable} will not call this method again after it calls either {@link #onCompleted} or
     * {@link #onError}.
     *
     * @param t the image_item emitted by the Observable
     */
    @Override
    public void onNext(T t) {

    }
}
