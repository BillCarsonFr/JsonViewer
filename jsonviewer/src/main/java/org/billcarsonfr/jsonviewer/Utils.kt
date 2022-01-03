package org.billcarsonfr.jsonviewer

import android.content.Context
import android.util.TypedValue

internal object Utils {
    fun dpToPx(dp: Int, context: Context): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp.toFloat(),
            context.resources.displayMetrics
        ).toInt()
    }
}