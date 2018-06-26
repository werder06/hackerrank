package woc_38;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Problem statement:
 * <p>
 * https://www.hackerrank.com/contests/w38/challenges/minute-to-win-it
 */
public class MinuteToWinIt {

  private static int minuteToWinIt(int[] a, int k) {
    long kk = (long) k;
    Map<Long, Integer> map = new HashMap<>();
    int result = 0;
    for (int i = 0; i < a.length; i++) {
      long startingPoint = a[i] - kk * i;
      map.merge(startingPoint, 1, Integer::sum);
      result = Math.max(result, map.get(startingPoint));
    }
    return a.length - result;
  }

  private static final Scanner scanner = new Scanner(System.in);

  public static void main(String[] args) {

    String[] nk = scanner.nextLine().split(" ");

    int n = Integer.parseInt(nk[0]);

    int k = Integer.parseInt(nk[1]);

    int[] a = new int[n];

    String[] aItems = scanner.nextLine().split(" ");
    scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

    for (int i = 0; i < n; i++) {
      int aItem = Integer.parseInt(aItems[i]);
      a[i] = aItem;
    }

    int result = minuteToWinIt(a, k);

    System.out.println(String.valueOf(result));

    scanner.close();
  }
}
