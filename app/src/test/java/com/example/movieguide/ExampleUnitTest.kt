package com.example.movieguide

import androidx.lifecycle.viewmodel.CreationExtras
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        val t = 1
        val r = 2

        val result = listOf(t,r)

        assertEquals(2, result.size)
    }
}