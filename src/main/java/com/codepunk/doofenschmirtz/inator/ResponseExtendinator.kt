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

package com.codepunk.doofenschmirtz.inator

import com.codepunk.doofenschmirtz.util.http.HttpStatusException
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response

/**
 * Convenience function that converts a Retrofit [Response] to an [HttpStatusException].
 */
fun Response<*>.toHttpStatusException(): HttpStatusException {
    val msg: String? = errorBody()?.peekString()?.let { s ->
        try {
            JSONObject(s).getString("message")
        } catch (e: JSONException) {
            s
        }
    }
    val errorMsg = if (msg.isNullOrEmpty()) message() else msg
    return HttpStatusException(code(), errorMsg)
}
