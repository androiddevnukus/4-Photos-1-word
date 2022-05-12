package uz.project.a4photos1word.core

import android.content.res.Resources

val Int.dp: Float
    get() = (this * Resources.getSystem().displayMetrics.density + 0.5f)
