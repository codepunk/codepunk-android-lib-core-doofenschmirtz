/*
 * Copyright (C) 2017 The Android Open Source Project
 * Modifications copyright (C) 2019 Codepunk, LLC
 *               Author(s): Scott Slater
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
 *
 *
 * The original work can be found at The Android Open Source Project at
 *
 *     https://github.com/googlesamples/android-architecture-components
 *
 * In the following location:
 *
 *     GithubBrowserSample/app/src/main/java/com/android/example/github/vo/Resource.kt
 *
 * Modifications:
 * August 2019: Added error Throwable to Resource
 *              Added secondary constructor and additional versions of error() method
 */

package com.codepunk.doofenschmirtz.borrowed.android.example.github.vo

import com.codepunk.doofenschmirtz.borrowed.android.example.github.vo.Status.*

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
</T> */
data class Resource<out T>(val status: Status, val data: T?, val message: String?, val error: Throwable?) {

    constructor(status: Status, data: T?, error: Throwable? = null):
        this(status, data, error?.message, error)

    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(SUCCESS, data, null, null)
        }

        fun <T> error(msg: String, data: T?): Resource<T> {
            return Resource(ERROR, data, msg, null)
        }

        fun <T> error(error: Throwable, data: T?): Resource<T> {
            return Resource(ERROR, data, error)
        }

        fun <T> error(msg: String, error: Throwable, data: T?): Resource<T> {
            return Resource(ERROR, data, msg, error)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(LOADING, data, null, null)
        }
    }
}
