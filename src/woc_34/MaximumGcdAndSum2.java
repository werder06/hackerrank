package woc_34;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * Problem statement:
 *
 * https://www.hackerrank.com/contests/w34/challenges/maximum-gcd-and-sum/
 *
 * O(max_value * ln(max_value))
 */
public class MaximumGcdAndSum2 {

  static int maximumGcdAndSum(int[] A, int[] B) {
    Set<Integer> setA = new HashSet<Integer>();
    for (int i : A) {
      setA.add(i);
    }
    Set<Integer> setB = new HashSet<Integer>();
    for (int i : B) {
      setB.add(i);
    }
    int max = 0;
    for (int i = 1000000; i >= 0; i--) {
      if (i < max) {
        i = 0;
        continue;
      }
      boolean first = false;
      boolean second = false;
      for (int j = 1; j <= 1000000 / i; j++) {
        if (setA.contains(i * j)) {
          first = true;
        }
        if (setB.contains(i * j)) {
          second = true;
        }
        if (first && second) {
          j = 1000000;
        }
      }
      if (first && second) {
        max = Math.max(max, i);
      }
    }
    int maxAValue = 0;
    for (int i : A) {
      if (i % max == 0 && i > maxAValue) {
        maxAValue = i;
      }
    }
    int maxBValue = 0;
    for (int i : B) {
      if (i % max == 0 && i > maxBValue) {
        maxBValue = i;
      }
    }
    return maxBValue + maxAValue;

  }

  public static void main(String[] args) throws IOException {
    FastReader fr = new FastReader();
    int n = fr.nextInt();
    int[] A = new int[n];
    for (int A_i = 0; A_i < n; A_i++) {
      A[A_i] = fr.nextInt();
    }
    int[] B = new int[n];
    for (int B_i = 0; B_i < n; B_i++) {
      B[B_i] = fr.nextInt();
    }
    int res = maximumGcdAndSum(A, B);
    System.out.println(res);
  }

  static class FastReader {

    BufferedReader br;
    StringTokenizer st;

    public FastReader() {
      br = new BufferedReader(new
          InputStreamReader(System.in));
    }

    String next() {
      while (st == null || !st.hasMoreElements()) {
        try {
          st = new StringTokenizer(br.readLine());
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      return st.nextToken();
    }

    int nextInt() {
      return Integer.parseInt(next());
    }

    long nextLong() {
      return Long.parseLong(next());
    }

    double nextDouble() {
      return Double.parseDouble(next());
    }

    String nextLine() {
      String str = "";
      try {
        str = br.readLine();
      } catch (IOException e) {
        e.printStackTrace();
      }
      return str;
    }
  }
  
}
