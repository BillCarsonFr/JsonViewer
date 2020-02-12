package org.billcarsonfr.jsonviewer

import android.content.Context
import android.os.Parcelable
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import kotlinx.android.parcel.Parcelize

@Parcelize
data class JSonViewerStyleProvider(
    @ColorInt val keyColor: Int,
    @ColorInt val stringColor: Int,
    @ColorInt val booleanColor: Int,
    @ColorInt val numberColor: Int,
    @ColorInt val baseColor: Int,
    @ColorInt val secondaryColor: Int
) : Parcelable {

    companion object {
        fun default(context: Context) = JSonViewerStyleProvider(
            keyColor = ContextCompat.getColor(context, R.color.key_color),
            stringColor = ContextCompat.getColor(context, R.color.string_color),
            booleanColor = ContextCompat.getColor(context, R.color.bool_color),
            numberColor = ContextCompat.getColor(context, R.color.number_color),
            baseColor = ContextCompat.getColor(context, R.color.base_color),
            secondaryColor = ContextCompat.getColor(context, R.color.secondary_color)
        )
    }
}

