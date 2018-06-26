package woc_38;

import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;

public class TimeSavingAffair {
  private static Junction[] JUNCTIONS;
  private static int N;
  private static int K;
  private static int TWO_K;

  private static class Junction {
    Map<Integer, Integer> children = new HashMap<>();
  }

  private static long addSpentTime(long currentTime) {
    long f = currentTime % TWO_K;
    if (f < K) {
      return currentTime;
    } else {
      return currentTime + TWO_K - f;
    }
  }

  private static class Node {
    int id;
    long time;

    Node(int id, long time) {
      this.id = id;
      this.time = time;
    }
  }

  private static long leastTimeToInterview(int n, int k, int m) {
    N = n;
    K = k;
    TWO_K = k * 2;
    JUNCTIONS = new Junction[N + 1];
    for (int i = 0; i < m; i++) {
      int a = scanner.nextInt();
      int b = scanner.nextInt();
      int cost = scanner.nextInt();
      putToMap(a, b, cost);
      putToMap(b, a, cost);
    }
    boolean[] visited = new boolean[N + 1];
    PriorityQueue<Node> unsetled = new PriorityQueue<>(Comparator.comparingLong(p -> p.time));
    unsetled.add(new Node(1, 0));
    while (!unsetled.isEmpty()) {
      Node node = unsetled.poll();
      if (visited[node.id]) {
        continue;
      }
      long currentTime = addSpentTime(node.time);
      if (node.id == N) {
        return node.time;
      }
      visited[node.id] = true;
      for (Map.Entry<Integer, Integer> child : JUNCTIONS[node.id].children.entrySet()) {
        if (!visited[child.getKey()]) {
          unsetled.add(new Node(child.getKey(), currentTime + child.getValue()));
        }
      }
    }
    return -1;
  }

  private static void putToMap(int id, int child, int cost) {
    Junction junction = JUNCTIONS[id];
    if (junction == null) {
      junction = new Junction();
      JUNCTIONS[id] = junction;
    }
    junction.children.merge(child, cost, Math::min);
  }

  private static final Scanner scanner = new Scanner(System.in);

  public static void main(String[] args) throws IOException {

    int n = scanner.nextInt();
    scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

    int k = scanner.nextInt();
    scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

    int m = scanner.nextInt();
    scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

    long result = leastTimeToInterview(n, k, m);

    System.out.println(String.valueOf(result));

    scanner.close();
  }
}
