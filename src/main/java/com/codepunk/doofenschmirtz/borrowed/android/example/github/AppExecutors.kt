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
 *     GithubBrowserSample/app/src/main/java/com/android/example/github/AppExecutors.kt
 *
 * Modifications:
 * July 2019: Removed inject-related logic (should be established in app-level dependency
 *            injection).
 */

package com.codepunk.doofenschmirtz.borrowed.android.example.github

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
/* import java.util.concurrent.Executors */

/**
 * Global executor pools for the whole application.
 *
 * Grouping tasks like this avoids the effects of task starvation (e.g. disk reads don't wait behind
 * webservice requests).
 */
/* @Singleton */
@Suppress("UNUSED")
open class AppExecutors(
    private val diskIO: Executor,
    private val networkIO: Executor,
    private val mainThread: Executor
) {

    /*
    @Inject
    constructor() : this(
        Executors.newSingleThreadExecutor(),
        Executors.newFixedThreadPool(3),
        MainThreadExecutor()
    )
    */

    /** */
    fun diskIO(): Executor {
        return diskIO
    }

    /** */
    fun networkIO(): Executor {
        return networkIO
    }

    /** */
    fun mainThread(): Executor {
        return mainThread
    }

    private class MainThreadExecutor : Executor {
        private val mainThreadHandler = Handler(Looper.getMainLooper())
        override fun execute(command: Runnable) {
            mainThreadHandler.post(command)
        }
    }
}
