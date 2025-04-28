package com.iiaannppaann.bigtreehw.core.util

import java.util.Locale

fun Float.toFormattedString(): String = String.format(Locale.US, "%.2f", this)