package com.nanoyatsu.example.clean.poke.core.extension

import org.junit.Assert
import org.junit.Test

class StringsKtTest {

    @Test
    fun proper() {
        Assert.assertEquals("`empty`.proper()", "", "".proper())
        Assert.assertEquals("a.proper()", "A", "a".proper())
        Assert.assertEquals("aa.proper()", "Aa", "aa".proper())
        Assert.assertEquals("aaa.proper()", "Aaa", "aaa".proper())
        Assert.assertEquals("あああ.proper()", "あああ", "あああ".proper())
    }
}