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

package com.codepunk.doofenschmirtz.net

import android.os.Bundle

/**
 * Class that stores information required to authenticate and interact with remote APIs via clients
 * such as Retrofit.
 */
@Suppress("UNUSED")
class AuthManager private constructor() {

    // region Properties

    /**
     * Returns an array [Environment]s defined for this AuthManager.
     */
    val environments: Array<Environment>
        get() = environmentsInternal.values.toTypedArray()

    /**
     * A collection of [Environment]s, mapped by their unique identifiers.
     */
    private val environmentsInternal = HashMap<String, Environment>()

    // endregion Properties

    // region Methods

    /**
     * Returns the [Environment] with the supplied [id], or null if no such environment is found.
     */
    fun getEnvironment(id: String): Environment? = environmentsInternal[id]

    // endregion Methods

    // region Nested/inner classes

    /**
     * A class that represents an environment for authenticating and interacting with remote APIs.
     */
    class Environment private constructor(
        /**
         * A unique identifier for the environment.
         */
        val id: String,

        /**
         * A user-friendly name for the environment.
         */
        val name: String,

        /**
         * The public identifier used to authenticate and interact with remote APIs.
         */
        val clientId: String?,

        /**
         * A string (known only to the application and the authorization server) used to
         * authenticate and interact with remote APIs.
         */
        val clientSecret: String?
    ) {

        // region Properties

        /**
         * A [Bundle] for storing any extra parameters needed to authenticate and interact
         * with a remote API.
         */
        private val extras = Bundle()

        // endregion Properties

        // region Methods

        /**
         * Returns the [extras] bundle. A copy is returned in order to keep extras immutable.
         */
        fun getExtras(): Bundle = Bundle(extras)

        // endregion Methods

        // region Nested/inner classes

        /**
         * Builder for an [Environment].
         */
        class Builder(
            /**
             * A unique identifier for the environment.
             */
            private val id: String,

            /**
             * A user-friendly name for the environment.
             */
            private val name: String
        ) {

            // region Properties

            /**
             * The public identifier used to authenticate and interact with remote APIs.
             */
            private var clientId: String? = null

            /**
             * A string (known only to the application and the authorization server) used to
             * authenticate and interact with remote APIs.
             */
            private var clientSecret: String? = null

            /**
             * A [Bundle] for storing any extra parameters needed to authenticate and interact
             * with a remote API.
             */
            private val extras = Bundle()

            // endregion Properties

            // region Methods

            /**
             * Builds an immutable [Environment] from the current state.
             */
            fun build(): Environment = Environment(id, name, clientId, clientSecret).apply {
                this.extras.putAll(this@Builder.extras)
            }

            /**
             * Sets the [clientId] for this Builder.
             */
            fun clientId(clientId: String?): Builder {
                this.clientId = clientId
                return this
            }

            /**
             * Sets the [clientSecret] for this Builder.
             */
            fun clientSecret(clientSecret: String?): Builder {
                this.clientSecret = clientSecret
                return this
            }

            /**
             * Adds all of the values defined in [extras] to this Builder.
             */
            fun putExtras(extras: Bundle): Builder {
                this.extras.putAll(extras)
                return this
            }

            // endregion Methods

        }

        // endregion Nested/inner classes

    }

    /**
     * Builder for an [AuthManager].
     */
    class Builder {

        // region Properties

        /**
         * A collection of [Environment]s, mapped by their unique identifiers.
         */
        private val environments = HashMap<String, Environment>()

        // endregion Properties

        // region Methods

        /**
         * Builds an immutable [AuthManager] from the current state.
         */
        fun build(): AuthManager = AuthManager().apply {
            this.environmentsInternal.putAll(this@Builder.environments)
        }

        /**
         * Puts the supplied [environment] into the [environmentsInternal] map.
         */
        fun environment(environment: Environment): Builder {
            environments[environment.id] = environment
            return this
        }

        // endregion Methods

    }

    // endregion Nested/inner classes

}
