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

package com.codepunk.doofenschmirtz.borrowed.modified.example.github.vo

import android.os.Bundle
import com.codepunk.doofenschmirtz.borrowed.modified.example.github.repository.NetworkBoundResource

/**
 * A sealed class representing the various outputs from a [NetworkBoundResource].
 */
sealed class Resource<out T>(

    /**
     * Optional additional data to send along with the resource.
     */
    val extras: Bundle? = null

) {

    // region Inherited methods

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Resource<*>) return false

        if (extras != other.extras) return false

        return true
    }

    override fun hashCode(): Int {
        return extras?.hashCode() ?: 0
    }

    // endregion Inherited methods

}

abstract class DataResource<T>(

    val data: T?,

    extras: Bundle? = null

) : Resource<T>(extras) {

    // region Inherited methods

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + (data?.hashCode() ?: 0)
        return result
    }

    // endregion Inherited methods

}

class LoadingResource<T>(

    data: T? = null,

    extras: Bundle? = null

) : DataResource<T>(data, extras) {

    // region Inherited methods

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is LoadingResource<*>) return false
        if (!super.equals(other)) return false
        return true
    }

    override fun toString(): String {
        return "LoadingResource(data=$data, extras=$extras)"
    }

    // endregion Inherited methods

}

class SuccessResource<T>(

    data: T? = null,

    extras: Bundle? = null

) : DataResource<T>(data, extras) {

    // region Inherited methods

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is SuccessResource<*>) return false
        if (!super.equals(other)) return false
        return true
    }

    override fun toString(): String {
        return "SuccessResource(data=$data, extras=$extras)"
    }

    // endregion Inherited methods

}

class ErrorResource<T>(

    data: T? = null,

    val error: Throwable? = null,

    extras: Bundle? = null

) : DataResource<T>(data, extras) {

    // region Inherited methods

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ErrorResource<*>) return false
        if (!super.equals(other)) return false

        if (error != other.error) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + (error?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "ErrorResource(data=$data, error=$error, extras=$extras)"
    }

    // endregion Inherited methods

}
