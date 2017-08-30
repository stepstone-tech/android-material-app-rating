/*
Copyright 2017 StepStone Services

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

package com.stepstone.apprating

/**
 * This interface contains all constants values used in library.
 */
interface C {

    interface InitialValues {

        companion object {

            val MAX_RATING = 6

            val DEFAULT_RATING = 4
        }
    }

    interface ExtraKeys {

        companion object {

            val DATA = "data"

            val CURRENT_RATE_NUMBER = "currentRateNumber"
        }
    }

    interface Animation {

        companion object {

            val VISIBLE = 1.0f

            val INVISIBLE = 0.0f

            val CHECK_STAR_DURATION: Long = 200
        }
    }
}
