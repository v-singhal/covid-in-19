package com.vbstudio.mapitout2.ui

import android.content.Context
import android.util.AttributeSet

class LineGraph: BaseBaseGraphView {

    constructor(context: Context?) : super(context) {
        initialize(context)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        initialize(context)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initialize(context)
    }

    private fun initialize(context: Context?) {
        // Do nothing for now
    }
}