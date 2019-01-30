package com.stepstone.apprating

import android.content.res.Resources
import androidx.annotation.StringRes
import android.text.TextUtils
import java.io.Serializable

/**
 * This class in a container for string value which can
 * be represented by java String class or resources id.
 * It provides text based on last given value.
 */
class StringValue : Serializable {

    var text: String? = null
        set(value) {
            field = value
            value?.let {
                textResId = 0
            }
        }

    @StringRes
    var textResId: Int = 0
        set(value) {
            field = value
            if (value != 0) {
                text = null
            }
        }

    fun resolveText(resources: Resources): String? {
        return if (TextUtils.isEmpty(text)) {
            if (textResId == 0) {
                return null
            }
            resources.getString(textResId)
        } else text
    }
}
