package com.vbstudio.covid19.home.ui

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import androidx.annotation.RequiresApi
import com.vbstudio.covid19.R

class DeceasedStatsView : StatsSectionView {

    constructor(context: Context?)
            : super(context) {
    }

    constructor(
        context: Context?,
        attrs: AttributeSet?
    ) : super(context, attrs) {
    }

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
    }

    init {
        setLabel(R.string.deceased)
        setup()
    }

    fun setupAsHeader() {
        applyStyle(R.style.StatesSectionHomeDeceased)
    }

    fun setup() {
        applyStyle(R.style.StatesSectionCardDeceased)
    }
}