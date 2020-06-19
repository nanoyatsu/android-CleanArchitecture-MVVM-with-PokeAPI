package com.nanoyatsu.example.clean.poke.core.extension

inline fun <I, O> I.pipe(f: (I) -> O): O = f(this)
