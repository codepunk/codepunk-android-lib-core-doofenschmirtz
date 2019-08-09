/*
 * Copyright (C) 2019 Codepunk, LLC
 * Author(s): Scott Slater
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.codepunk.doofenschmirtz.data

/**
 * A class that allows either a "valid" [T] instance or an "invalid" [Throwable] to be wrapped
 * into the same class.
 */
sealed class ResultWrapper<T> {

    // region Methods

    /**
     * Returns the
     */
    abstract fun get(): T?

    // endregion Methods

    // region Companion object

    companion object {

        // region Methods

        /** TODO Documentation */
        fun <T> wrapData(data: T?): ResultWrapper<T> = DataWrapper(data)

        /** TODO Documentation */
        fun <T> wrapThrowable(throwable: Throwable): ResultWrapper<T> = ThrowableWrapper(throwable)

        // endregion Methods

    }

    // endregion Companion object

}

/**
 * An implementation of [ResultWrapper] that wraps a successful result.
 */
class DataWrapper<T>(private val data: T? = null) : ResultWrapper<T>() {

    override fun get(): T? = data

}

/**
 * An implementation of [ResultWrapper] that wraps a [Throwable]. Calling [get] will throw this
 * throwable.
 */
class ThrowableWrapper<T>(private val throwable: Throwable) : ResultWrapper<T>() {

    override fun get(): T? = throw throwable

}
