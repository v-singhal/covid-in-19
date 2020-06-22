package com.vbstudio.covid19.core.utils

class NumberUtils {
    companion object {

        fun getPercentage(inputNumber: String?, baseNumber: String?): Float {
            inputNumber?.let {
                baseNumber?.let {
                    return getPercentage(inputNumber.toInt(), baseNumber.toInt())
                }
            }
            return 0F
        }

        fun getPercentage(inputNumber: Int, baseNumber: Int): Float {
            if (baseNumber > 0) {
                return 100 * inputNumber / baseNumber.toFloat()
            }
            return 0F
        }
    }
}