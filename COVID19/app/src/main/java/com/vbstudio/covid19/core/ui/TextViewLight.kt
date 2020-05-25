package com.vbstudio.covid19.core.ui

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class TextViewLight : AppCompatTextView {

    constructor(context: Context?) : super(context) {
        setCustomFont(context)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(
        context,
        attrs
    ) {
        setCustomFont(context)
    }

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        setCustomFont(context)
    }

    private fun setCustomFont(context: Context?) {
        context?.let {
            typeface = Typeface.createFromAsset(context.assets, "fonts/Montserrat-Light.ttf")
        }
    }
}