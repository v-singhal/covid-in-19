package com.vbstudio.covid19.home.ui

import android.content.Context
import android.content.res.TypedArray
import android.os.Build
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.annotation.StyleRes
import com.vbstudio.covid19.R
import kotlinx.android.synthetic.main.stats_section_view.view.*

open class StatsSectionView : LinearLayout {

    private val DEFAULT_TEXT_SIZE = 14

    constructor(context: Context?)
            : super(context) {
        initialize()
    }

    constructor(
        context: Context?,
        attrs: AttributeSet?
    ) : super(context, attrs) {
        initialize()
    }

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        initialize()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        initialize()
    }

    private fun initialize() {
        addView(LayoutInflater.from(context).inflate(R.layout.stats_section_view, null, false))
    }


    protected fun applyStyle(@StyleRes styleRedId: Int) {
        val attributes: TypedArray =
            context.obtainStyledAttributes(styleRedId, R.styleable.StatsSectionView)

        val textColor = attributes.getColor(
            R.styleable.StatsSectionView_textColor,
            resources.getColor(R.color.baseGrey)
        )
        tv_counter.setTextColor(textColor)
        view_separator.setBackgroundColor(textColor)
        tv_label_counter.setTextColor(textColor)

        val counterTextSize = attributes.getDimensionPixelSize(
            R.styleable.StatsSectionView_counterTextSize,
            DEFAULT_TEXT_SIZE
        )
        tv_counter.setTextSize(TypedValue.COMPLEX_UNIT_PX, counterTextSize.toFloat())

        val labelCounterTextSize = attributes.getDimensionPixelSize(
            R.styleable.StatsSectionView_labelCounterTextSize,
            DEFAULT_TEXT_SIZE
        )
        tv_label_counter.setTextSize(TypedValue.COMPLEX_UNIT_PX, labelCounterTextSize.toFloat())

        val separatorWidth = attributes.getDimensionPixelSize(
            R.styleable.StatsSectionView_separatorWidth,
            DEFAULT_TEXT_SIZE
        )
        val separatorThickness = attributes.getDimensionPixelSize(
            R.styleable.StatsSectionView_separatorThickness,
            DEFAULT_TEXT_SIZE
        )
        val separatorLayoutParams: LayoutParams = view_separator.layoutParams as LayoutParams
        separatorLayoutParams.width = separatorWidth
        separatorLayoutParams.height = separatorThickness

        val separatorMargin = attributes.getDimensionPixelSize(
            R.styleable.StatsSectionView_separatorMargin,
            DEFAULT_TEXT_SIZE
        )
        separatorLayoutParams.topMargin = separatorMargin
        separatorLayoutParams.bottomMargin = separatorMargin

        val separatorColor = attributes.getColor(
            R.styleable.StatsSectionView_separatorColor,
            resources.getColor(R.color.baseGrey)
        )
        view_separator.setBackgroundColor(separatorColor)

        attributes.recycle()
    }

    protected fun setLabel(@StringRes stringRes: Int) {
        tv_label_counter.setText(stringRes)
    }

    fun setCounter(counter: String?) {
        tv_counter.text = counter ?: "---"
    }
}
