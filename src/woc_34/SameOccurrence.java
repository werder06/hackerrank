package woc_34;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Stack;
import java.util.StringTokenizer;

/**
 * Problem statement:
 *
 * https://www.hackerrank.com/contests/w34/challenges/same-occurrence
 */
public class SameOccurrence {

  private static HashMap<String, Integer> cache = new HashMap<>();
  private static HashMap<Integer, Integer> onlyOneValueCache = new HashMap<>();
  private static Set<Integer> values = new HashSet<>();
  private static int n;

  private static HashMap<Integer, int[]> positions = new HashMap<>();

  private static int[] EMPTY = new int[0];

  private static class Iterator {
    Iterator(int[] x, int[] y) {
      this.x = x;
      this.y = y;
    }

    int[] x;
    int[] y;
    int xi = 0;
    int yi = 0;

    boolean hasNext() {
      return x.length > xi || y.length > yi;
    }

    int next() {
      if (xi < x.length && yi < y.length) {
        if (x[xi] <= y[yi]) {
          int ret = x[xi];
          xi++;
          return ret;
        } else {
          int ret = y[yi];
          yi++;
          return ret;
        }
      }
      if (xi < x.length) {
        int ret = x[xi];
        xi++;
        return ret;
      }
      if (yi < y.length) {
        int ret = y[yi];
        yi++;
        return ret;
      }
      throw new RuntimeException("III");
    }
  }

  private static int calculateSubArraysDiff(int x, int y, int[] array) {
    boolean hasX = values.contains(x);
    boolean hasY = values.contains(y);
    Integer value = null;
    if (!hasX && !hasY) {
      return ((n + 1) * n) / 2;
    } else if (hasX && !hasY) {
      value = onlyOneValueCache.get(x);
      if (value != null) {
        return value;
      }
    } else if (hasY && !hasX) {
      value = onlyOneValueCache.get(y);
      if (value != null) {
        return value;
      }
    }

    int max = Math.max(x, y);
    int min = Math.min(x, y);
    value = cache.get(max + " " + min);
    if (value != null) {
      return value;
    }

    int[] posX = positions.get(x) != null ? positions.get(x) : EMPTY;
    int[] posY = positions.get(y) != null ? positions.get(y) : EMPTY;
    int summ;
    if ((hasX && !hasY) || (hasY && !hasX)) {
      summ = hasXOnly(posX.length == 0 ? posY : posX);
    } else {
      summ = hasBoth(posX, posY, array, x);
    }

    if (hasX && !hasY) {
      onlyOneValueCache.put(x, summ);
    } else if (hasY && !hasX) {
      onlyOneValueCache.put(y, summ);
    } else {
      cache.put(max + " " + min, summ);
    }
    return summ;
  }

  private static int calculateSubArrays(int x, int y, int[] array) {
    if (x == y) {
      return ((n + 1) * n) / 2;
    } else {
      return calculateSubArraysDiff(x, y, array);
    }
  }

  private static int hasBoth(int[] posX, int[] posY, int[] array, int x) {
    Iterator iterator = new Iterator(posX, posY);
    Stack<Integer> yNeededStack = new Stack<>();
    Stack<Integer> xNeededStack = new Stack<>();
    int summ = 0;
    int okValue = 0;
    int xNeeded = 0;
    int yNeeded = 0;
    int posx = posX.length > 0 ? posX[0] : Integer.MAX_VALUE;
    int posy = posY.length > 0 ? posY[0] : Integer.MAX_VALUE;
    int lastPosition = Math.min(posx, posy);
    if (lastPosition > 0) {
      summ = summ + okValue * lastPosition + ((lastPosition + 1) * lastPosition) / 2;
      okValue = okValue + lastPosition;
    }
    while (iterator.hasNext()) {
      int i = iterator.next();
      int dif = i - lastPosition - 1;
      if (dif > 0) {
        summ = summ + okValue * dif + ((dif + 1) * dif) / 2;
        okValue = okValue + dif;
      }
      lastPosition = i;
      int v = array[i];
      if (v == x) {
        summ += xNeeded;
        yNeededStack.push(yNeeded);
        yNeeded = okValue + 1;
        okValue = xNeeded;
        if (xNeededStack.isEmpty()) {
          xNeeded = 0;
        } else {
          xNeeded = xNeededStack.pop();
        }
      } else {
        summ += yNeeded;
        xNeededStack.push(xNeeded);
        xNeeded = okValue + 1;
        okValue = yNeeded;
        if (yNeededStack.isEmpty()) {
          yNeeded = 0;
        } else {
          yNeeded = yNeededStack.pop();
        }
      }
    }
    int dif = n - 1 - lastPosition;
    if (dif > 0) {
      summ = summ + okValue * dif + ((dif + 1) * dif) / 2;
    }
    return summ;
  }

  private static int hasXOnly(int[] pos) {
    int sum = 0;
    int prev = pos[0];
    if (prev > 0) {
      sum = sum + ((prev + 1) * prev) / 2;
    }
    for (int i : pos) {
      int dif = i - prev - 1;
      if (dif > 0) {
        sum = sum + ((dif + 1) * dif) / 2;
      }
      prev = i;
    }
    int dif = n - 1 - pos[pos.length - 1];
    if (dif > 0) {
      sum = sum + ((dif + 1) * dif) / 2;
    }
    return sum;
  }


  public static void main(String[] args) {
    FastReader in = new FastReader();
    n = in.nextInt();
    int q = in.nextInt();
    int[] arr = new int[n];
    HashMap<Integer, List<Integer>> positionsPrev = new HashMap<>();
    for (int arr_i = 0; arr_i < n; arr_i++) {
      arr[arr_i] = in.nextInt();
      values.add(arr[arr_i]);
      List<Integer> pos = positionsPrev.get(arr[arr_i]);
      if (pos == null) {
        pos = new ArrayList<>();
        positionsPrev.put(arr[arr_i], pos);
      }
      pos.add(arr_i);
    }
    for (Entry<Integer, List<Integer>> integerListEntry : positionsPrev.entrySet()) {
      int[] pos = new int[integerListEntry.getValue().size()];
      int count = 0;
      for (Integer integer : integerListEntry.getValue()) {
        pos[count] = integer;
        count++;
      }
      positions.put(integerListEntry.getKey(), pos);
    }
    for (int a0 = 0; a0 < q; a0++) {
      int x = in.nextInt();
      int y = in.nextInt();
      System.out.println(calculateSubArrays(x, y, arr));
    }
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
