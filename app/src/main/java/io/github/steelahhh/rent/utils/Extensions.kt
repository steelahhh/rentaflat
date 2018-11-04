package io.github.steelahhh.rent.utils

import android.graphics.Typeface
import android.widget.TextView
import androidx.appcompat.widget.Toolbar

/*
 * Created by Alexander Efimenko on 4/11/18.
 */

fun Toolbar.changeToolbarFont(){
    for (i in 0 until childCount) {
        val view = getChildAt(i)
        if (view is TextView && view.text == title) {
            view.typeface = Typeface.createFromAsset(view.context.assets, "manrope_bold.otf")
            break
        }
    }
}