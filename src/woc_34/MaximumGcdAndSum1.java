package woc_34;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * Problem statement:
 *
 * https://www.hackerrank.com/contests/w34/challenges/maximum-gcd-and-sum/
 *
 * O(number_of_values * sqrt(max_value))
 */
public class MaximumGcdAndSum1 {

  static int maximumGcdAndSum(int[] A, int[] B) {
    int n = A.length;
    int maxValue = (int) Math.pow(10, 6) + 1;
    boolean[] maxA = new boolean[maxValue];
    for (int i = 0; i < n; i++) {
      for (int j = 1; j * j <= A[i]; j++) {
        if (A[i] % j == 0) {
          maxA[j] = true;
          maxA[A[i] / j] = true;
        }
      }
    }
    int max = 1;
    for (int i = 0; i < n; i++) {
      if (B[i] > max) {
        for (int j = 1; j * j <= B[i]; j++) {
          if (B[i] % j == 0) {
            if (maxA[j] && j > max) {
              max = j;
            }
            int d = B[i] / j;
            if (maxA[d] && d > max) {
              max = d;
            }
          }
        }
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

  public static void main(String[] args) {
    FastReader in = new FastReader();
    int n = in.nextInt();
    int[] A = new int[n];
    for (int A_i = 0; A_i < n; A_i++) {
      A[A_i] = in.nextInt();
    }
    int[] B = new int[n];
    for (int B_i = 0; B_i < n; B_i++) {
      B[B_i] = in.nextInt();
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
