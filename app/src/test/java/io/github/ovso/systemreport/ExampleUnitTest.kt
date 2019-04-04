package io.github.ovso.systemreport

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
        assertEquals(4, 2 + 2)
    }

    fun makeArrayConsecutive2(statues: MutableList<Int>): Int {
        val max = statues.max()!!
        val min = statues.min()!!
        val btween = max - min
        return btween - statues.size + 1
        return 0;
    }

}
