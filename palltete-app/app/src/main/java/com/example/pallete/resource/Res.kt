package com.example.pallete.resource

import android.content.res.Resources
import android.graphics.Color
import android.os.Build

class Res(original: Resources, selectedColor: Int): Resources(original.assets, original.displayMetrics, original.configuration) {
    val selectedColor = selectedColor
    @Throws(NotFoundException::class)
    override fun getColor(id: Int): Int {
        return getColor(id, null)
    }

    @Throws(NotFoundException::class)
    override fun getColor(id: Int, theme: Theme?): Int {
        return when (getResourceEntryName(id)) {
            "text_green" -> {
                selectedColor
            }
            else -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    super.getColor(id, theme)
                } else {
                    super.getColor(id)
                }
            }
        }
    }
}