package woc_37;

import java.util.Scanner;

/**
 * Problem statement:
 * <p>
 * https://www.hackerrank.com/contests/w37/challenges/dynamic-line-intersection
 */
public class DynamicLineIntersection {
  private static int size = (int) Math.pow(10, 5);
  private static int[] values = new int[size + 1];
  private static int all = 0;

  private static void dynamicLineIntersection(int n) {
    for (int i = 0; i < n; i++) {
      String line = scanner.nextLine();
      String[] val = line.split(" ");
      if (val.length == 2) {
        System.out.println(all + values[Integer.parseInt(val[1])]);
      } else {
        fillArray(Integer.parseInt(val[1]), Integer.parseInt(val[2]),
            "+".equals(val[0]) ? 1 : -1);
      }
    }
  }

  private static void fillArray(int k, int b, int toAdd) {
    if (k == 1) {
      all += toAdd;
      return;
    }
    int value = b;
    while (value <= size) {
      values[value] += toAdd;
      value += k;
    }
    value = b - k;
    while (value >= 0) {
      values[value] += toAdd;
      value -= k;
    }
  }

  private static final Scanner scanner = new Scanner(System.in);

  public static void main(String[] args) {
    int n = scanner.nextInt();
    scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])*");

    dynamicLineIntersection(n);

    scanner.close();
  }
}
