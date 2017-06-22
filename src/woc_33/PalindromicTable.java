package woc_33;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;

/**
 * Problem statement:
 *
 * https://www.hackerrank.com/contests/w33/challenges/palindromic-table
 */
public class PalindromicTable {

  private static boolean toManyZeroFailFast = false;

  private static class Square implements Comparable<Square> {

    int row;
    int column;

    Square(int row, int column) {
      this.row = row;
      this.column = column;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (!(o instanceof Square)) {
        return false;
      }

      Square square = (Square) o;

      if (row != square.row) {
        return false;
      }
      return column == square.column;
    }

    @Override
    public int hashCode() {
      int result = row;
      result = 31 * result + column;
      return result;
    }


    @Override
    public int compareTo(Square o) {
      return o.row * o.column - row * column;
    }

    @Override
    public String toString() {
      return row * column + " = " + row + " * " + column;
    }

  }

  private static boolean isPalindrome(int[] a) {
    int count = 0;
    int summ = 0;
    for (int anA : a) {
      if (anA % 2 == 1) {
        count++;
      }
      summ += anA;
    }
    if (summ - a[0] <= 1) {
      toManyZeroFailFast = true;
    }
    return (count == 0 || count == 1) && (summ - a[0] > 1 || a[0] == 1);
  }

  private static boolean checkRectangleFromIJ(int[][] a, int i, int j, int k, int l) {
    int[] count = new int[10];
    for (int p = i; p < k; p++) {
      for (int t = j; t < l; t++) {
        count[a[p][t]] += 1;
      }
    }
    return isPalindrome(count);
  }

  private static int[] checkAllRectanglesWithThisSize(int[][] a, int k, int l, int n, int m) {
    for (int i = 0; i <= n - k; i++) {
      for (int j = 0; j <= m - l; j++) {
        boolean isPalindrome = checkRectangleFromIJ(a, i, j, i + k, l + j);
        if (isPalindrome) {
          return new int[]{i, j, i + k - 1, j + l - 1};
        }
      }
    }
    return null;
  }

  public static void main(String[] args) throws IOException {
    BufferedReader bi = new BufferedReader(new InputStreamReader(System.in));
    String[] line = bi.readLine().split(" ");
    int n = Integer.parseInt(line[0]);
    int m = Integer.parseInt(line[1]);
    int[][] a = new int[n][m];
    for (int i = 0; i < n; i++) {
      line = bi.readLine().split(" ");
      for (int j = 0; j < m; j++) {
        a[i][j] = Integer.parseInt(line[j]);
      }
    }
    PriorityQueue<Square> queue = new PriorityQueue<>();
    queue.add(new Square(n, m));
    while (!queue.isEmpty()) {
      Square square = queue.poll();
      int row = square.row;
      int column = square.column;
      int[] resp = checkAllRectanglesWithThisSize(a, row, column, n, m);
      if (square.row == n && square.column == m && toManyZeroFailFast) {
        System.out.println(1);
        System.out.println("0 0 0 0");
        return;
      }
      if (resp != null) {
        System.out.println(row * column);
        System.out.println(resp[0] + " " + resp[1] + " " + resp[2] + " " + resp[3]);
        return;
      } else {
        if (square.row > 1) {
          queue.add(new Square(square.row - 1, square.column));
        }
        if (square.column > 1) {
          queue.add(new Square(square.row, square.column - 1));
        }
      }
    }
  }
}
