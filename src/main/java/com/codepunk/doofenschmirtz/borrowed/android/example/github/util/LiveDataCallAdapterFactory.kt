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
 *     GithubBrowserSample/app/src/main/java/com/android/example/github/util/LiveDataCallAdapterFactory.kt
 *
 */

package com.codepunk.doofenschmirtz.borrowed.android.example.github.util

import androidx.lifecycle.LiveData
import com.codepunk.doofenschmirtz.borrowed.android.example.github.api.ApiResponse
import com.codepunk.doofenschmirtz.borrowed.modified.example.github.util.LiveDataCallAdapter
import retrofit2.CallAdapter
import retrofit2.CallAdapter.Factory
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class LiveDataCallAdapterFactory : Factory() {
    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        if (Factory.getRawType(returnType) != LiveData::class.java) {
            return null
        }
        val observableType = Factory.getParameterUpperBound(0, returnType as ParameterizedType)
        val rawObservableType = Factory.getRawType(observableType)
        if (rawObservableType != ApiResponse::class.java) {
            throw IllegalArgumentException("type must be a resource")
        }
        if (observableType !is ParameterizedType) {
            throw IllegalArgumentException("resource must be parameterized")
        }
        val bodyType = Factory.getParameterUpperBound(0, observableType)
        return LiveDataCallAdapter<Any>(bodyType)
    }
}
