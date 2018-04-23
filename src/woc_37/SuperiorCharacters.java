package woc_37;

import java.util.Scanner;

/**
 * Problem statement:
 * <p>
 * https://www.hackerrank.com/contests/w37/challenges/superior-characters
 */
public class SuperiorCharacters {

  private static long maximumSuperiorCharacters(int[] freq) {
    long actualCount = 0;
    long length = 0;
    for (int i : freq) {
      int currentValue = i;
      long toAdd = 0;
      long freeElements = length - actualCount * 2 - 1;
      if (freeElements > 0) {
        long min = Math.min(currentValue, freeElements);
        currentValue -= min;
        toAdd += min;
      }
      if (currentValue > 0) {
        long maxToAdd = actualCount * 2;
        long min = Math.min(maxToAdd, currentValue);
        toAdd += min / 2;
      }
      actualCount += toAdd;
      length += i;
    }

    return actualCount;

  }

  private static final Scanner scanner = new Scanner(System.in);

  public static void main(String[] args) {
    int t = scanner.nextInt();
    scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

    for (int tItr = 0; tItr < t; tItr++) {
      int[] freq = new int[26];

      String[] freqItems = scanner.nextLine().split(" ");
      scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

      for (int freqItr = 0; freqItr < 26; freqItr++) {
        int freqItem = Integer.parseInt(freqItems[freqItr]);
        freq[freqItr] = freqItem;
      }
      System.out.println(maximumSuperiorCharacters(freq));
    }
    scanner.close();
  }
}
