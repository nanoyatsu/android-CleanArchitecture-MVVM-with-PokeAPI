package com.nanoyatsu.example.clean.poke.core.extension

import java.util.*

// excelにPROPERって関数があるらしいので・・・（わかってない）
fun String.proper(): String {
    return when (this.length) {
        0 -> ""
        1 -> this.toUpperCase(Locale.getDefault())
        else -> this[0].toUpperCase() + this.substring(1 until this.length)
    }
}