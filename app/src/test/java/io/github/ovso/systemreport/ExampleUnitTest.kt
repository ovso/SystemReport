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
  fun test() { // 4,5,6 = 15
    val sequence = mutableListOf(10, 1, 2, 3, 4, 5)
    assertFalse(almostIncreasingSequence(sequence))
  }

  fun almostIncreasingSequence(sequence: MutableList<Int>): Boolean {
    sequence.sort()
    for (i in 0..(sequence.lastIndex - 1)) {
      if (sequence[i] + 1 != sequence[i + 1]) {
        return false;
      }
    }
    return true
  }

  fun almostIncreasingSequence2(sequence: MutableList<Int>): Boolean {
    sequence.sort()

    return true
  }
}