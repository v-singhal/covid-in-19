package com.vbstudio.covid19.core.utils

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View
import android.view.animation.DecelerateInterpolator

class AnimationUtils {

    companion object {
        private const val DEFAULT_DURATION = 300L

        fun revealView(view: View) {
            val startAlpha = 0F
            val endAlpha = 1F
            val startScaleX = 0F
            val endScaleX = 1F
            val startScaleY = 0F
            val endScaleY = 1F
            val animatorSet = AnimatorSet()
            val fadeAnimator = ObjectAnimator.ofFloat(view, View.ALPHA, startAlpha, endAlpha)
            val scaleXAnimator = ObjectAnimator.ofFloat(view, View.SCALE_X, startScaleX, endScaleX)
            val scaleYAnimator = ObjectAnimator.ofFloat(view, View.SCALE_Y, startScaleY, endScaleY)

            animatorSet.playTogether(fadeAnimator, scaleXAnimator, scaleYAnimator)
            animatorSet.interpolator = DecelerateInterpolator()
            animatorSet.duration = DEFAULT_DURATION

            animatorSet.start()
        }

        fun hideView(view: View) {
            val startAlpha = 1F
            val endAlpha = 0F
            val startScaleX = 1F
            val endScaleX = 1F
            val startScaleY = 1F
            val endScaleY = 0F
            val animatorSet = AnimatorSet()
            val fadeAnimator = ObjectAnimator.ofFloat(view, View.ALPHA, startAlpha, endAlpha)
            val scaleXAnimator = ObjectAnimator.ofFloat(view, View.SCALE_X, startScaleX, endScaleX)
            val scaleYAnimator = ObjectAnimator.ofFloat(view, View.SCALE_Y, startScaleY, endScaleY)

            animatorSet.playTogether(fadeAnimator, scaleXAnimator, scaleYAnimator)
            animatorSet.interpolator = DecelerateInterpolator()
            animatorSet.duration = DEFAULT_DURATION

            animatorSet.start()
        }
    }
}