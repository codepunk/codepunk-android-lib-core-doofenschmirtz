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

import android.content.Context
import android.content.Intent

/**
 * Extension method on [Context] that starts the launch activity. Depending on the flags passed,
 * this can clear all other activities on the back stack when (re)starting the launch activity.
 */
fun Context.startLaunchActivity(flags: Int = Intent.FLAG_ACTIVITY_CLEAR_TOP) {
    packageManager.getLaunchIntentForPackage(packageName)?.run {
        this.flags = flags
        startActivity(this)
    }
}
