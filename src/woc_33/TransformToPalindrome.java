package woc_33;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Problem statement:
 *
 * https://www.hackerrank.com/contests/w33/challenges/transform-to-palindrome
 */
public class TransformToPalindrome {

  private static Map<Integer, Node> graph = new HashMap<>();
  private static Map<Integer, Set<Integer>> connectivityComponent = new HashMap<>();

  private static class Node {

    int number;
    List<Integer> children;

    void collectConnectivityComponent(HashSet<Integer> visited) {
      for (Integer child : children) {
        if (visited.add(child)) {
          Node childNode = graph.get(child);
          if (childNode != null) {
            childNode.collectConnectivityComponent(visited);
          }
        }
      }
    }
  }

  private static void put(int parent, int child) {
    Node node = graph.get(parent);
    if (node == null) {
      node = new Node();
      node.number = parent;
      node.children = new ArrayList<>(1);
      node.children.add(child);
      graph.put(parent, node);
    } else {
      node.children.add(child);
    }
  }

  private static boolean isReachable(int x, int y) {
    if (x == y) {
      return true;
    }
    Set<Integer> conenctedNodes = connectivityComponent.get(x);
    if (conenctedNodes == null) {
      Node n = graph.get(x);
      if (n != null) {
        HashSet<Integer> allConnectedNodes = new HashSet<>();
        allConnectedNodes.add(x);
        n.collectConnectivityComponent(allConnectedNodes);
        for (int i : allConnectedNodes) {
          connectivityComponent.put(i, allConnectedNodes);
        }
        connectivityComponent.put(x, allConnectedNodes);
        conenctedNodes = allConnectedNodes;
      } else {
        conenctedNodes = new HashSet<>();
        connectivityComponent.put(x, conenctedNodes);
      }
    }
    return conenctedNodes.contains(y);
  }

  private static long palindrome(int[] source) {
    int n = source.length;
    long[][] a = new long[n][n];
    for (int i = 0; i < n; i++) {
      a[i][i] = 1;
    }
    for (int i = 1; i < n; i++) {
      for (int l = 0, h = i; h < n; l++, h++) {
        boolean isReachable = isReachable(source[l], source[h]);
        long max = isReachable ? a[l + 1][h - 1] + 2 : 0;
        a[l][h] = Math.max(max, Math.max(a[l][h - 1], a[l + 1][h]));
      }
    }
    return a[0][n - 1];
  }

  public static void main(String[] args) throws IOException {
    BufferedReader bi = new BufferedReader(new InputStreamReader(System.in));
    String[] line = bi.readLine().split(" ");
    int n = Integer.parseInt(line[0]);
    int k = Integer.parseInt(line[1]);
    int m = Integer.parseInt(line[2]);
    for (int a0 = 0; a0 < k; a0++) {
      line = bi.readLine().split(" ");
      int x = Integer.parseInt(line[0]);
      int y = Integer.parseInt(line[1]);
      put(x, y);
      put(y, x);
    }
    String[] array = bi.readLine().split(" ");
    int[] a = new int[m];
    for (int a_i = 0; a_i < m; a_i++) {
      a[a_i] = Integer.parseInt(array[a_i]);
    }
    System.out.println(palindrome(a));
  }

}
