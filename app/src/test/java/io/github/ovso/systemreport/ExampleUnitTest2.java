package io.github.ovso.systemreport;

import java.util.Arrays;
import org.junit.Assert.*;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ExampleUnitTest2 {

  @Test public void test() {
    assertFalse(almostIncreasingSequence(new int[] { 0, -2, 5, 6 }));
  }

  boolean almostIncreasingSequence(int[] sequence) {
    Arrays.sort(sequence);

    for (int i = 0; i < sequence.length - 1; i++) {
      if ((sequence[i] + 1 != sequence[i + 1])) {
        return false;
      }
    }

    return true;
  }
}
