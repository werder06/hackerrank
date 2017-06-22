package hack_50;

import java.util.*;

/**
 * Problem statement:
 *
 * https://www.hackerrank.com/contests/101hack50/challenges/even-and-odd-boxes/
 */
public class EvenOddBoxes {

  private static long minimumChocolateMoves(int n, int[] x) {
    long mustMove = 0;
    long mustAdd = 0;
    long maxToBeAdded = 0;
    for (int i = 0; i < n; i++) {
      int value = x[i];
      if (i % 2 == 0 && x[i] % 2 == 1) {
        if (x[i] == 1) {
          mustAdd++;
        } else {
          mustMove++;
        }
        maxToBeAdded += x[i] > 1 ? value - 2 : 0;
      } else if (i % 2 == 1 && x[i] % 2 == 0) {
        mustMove++;
        maxToBeAdded += value - 1;
      } else if (i % 2 == 1) {
        maxToBeAdded += value - 1;
      } else if (i % 2 == 0) {
        maxToBeAdded += value - 2;
      }
    }
    if ((mustMove + mustAdd) % 2 == 1) {
      return -1;
    } else if (mustAdd > maxToBeAdded) {
      return -1;
    } else if (mustMove >= mustAdd) {
      return mustAdd + (mustMove - mustAdd) / 2;
    } else {
      return mustAdd;
    }
  }

  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    int q = in.nextInt();
    for (int a0 = 0; a0 < q; a0++) {
      int n = in.nextInt();
      int[] X = new int[n];
      for (int X_i = 0; X_i < n; X_i++) {
        X[X_i] = in.nextInt();
      }
      long result = minimumChocolateMoves(n, X);
      System.out.println(result);
    }
  }
}
