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

package com.codepunk.doofenschmirtz.retrofit.interceptor

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

// region Constants

/**
 * The "RemoteAuthorization" API header name.
 */
const val HEADER_NAME_AUTHORIZATION = "Authorization"

/**
 * A custom header name that supplies an auth token. This will be converted to the "Authorization"
 * header via an interceptor.
 */
const val HEADER_NAME_TEMP_AUTH_TOKEN = "Temp-Auth-Token"

/**
 * A placeholder for an auth token in endpoints that require authentication.
 */
const val HEADER_VALUE_AUTH_TOKEN_PLACEHOLDER = "\$authToken"

/**
 * A placeholder for an auth token in endpoints that require authentication.
 */
const val HEADER_VALUE_AUTHORIZATION = "Bearer $HEADER_VALUE_AUTH_TOKEN_PLACEHOLDER"

// endregion Constants

/**
 * Singleton class that intercepts Retrofit requests and looks for a header with a name
 * of [HEADER_NAME_AUTHORIZATION] ("Authorization"). If found, any instance in the value matching
 * [HEADER_VALUE_AUTH_TOKEN_PLACEHOLDER] will be replaced with the authToken (if any) currently
 * stored in [SessionManager].
 */
@Singleton
class AuthorizationInterceptor @Inject constructor(

    /* TODO
    /**
     * The session manager used for managing a user session. Lazy to avoid circular reference in
     * Dagger.
     */
    private val lazySessionManager: Lazy<SessionManager>
    */

) : Interceptor {

    // region Implemented methods

    /**
     * Implementation of [Interceptor]. Looks for a header with a name of
     * [HEADER_NAME_AUTHORIZATION] ("Authorization") and replaces any instance of
     * [HEADER_VALUE_AUTH_TOKEN_PLACEHOLDER] in the value with the authToken (if any) currently
     * stored in [SessionManager]. Also looks for a header with a name of
     * [HEADER_NAME_TEMP_AUTH_TOKEN] and converts it into a [HEADER_NAME_AUTHORIZATION] header.
     */
    override fun intercept(chain: Interceptor.Chain): Response {
        var request: Request = chain.request()

        // Replace authToken in headers matching HEADER_NAME_AUTHORIZATION
        request.header(HEADER_NAME_AUTHORIZATION)?.run {
            val authToken = "" /* TODO lazySessionManager.get().session?.authToken ?: "" */
            request = request.newBuilder()
                .header(
                    HEADER_NAME_AUTHORIZATION,
                    replace(
                        HEADER_VALUE_AUTH_TOKEN_PLACEHOLDER,
                        authToken,
                        true
                    )
                )
                .build()
        }

        // Convert HEADER_NAME_TEMP_AUTH_TOKEN header to HEADER_NAME_AUTHORIZATION
        request.header(HEADER_NAME_TEMP_AUTH_TOKEN)?.run {
            request = request.newBuilder()
                .header(
                    HEADER_NAME_AUTHORIZATION,
                    HEADER_VALUE_AUTHORIZATION.replace(
                        HEADER_VALUE_AUTH_TOKEN_PLACEHOLDER,
                        this,
                        true
                    )
                )
                .removeHeader(HEADER_NAME_TEMP_AUTH_TOKEN)
                .build()
        }

        return chain.proceed(request)
    }

    // endregion Implemented methods

}
