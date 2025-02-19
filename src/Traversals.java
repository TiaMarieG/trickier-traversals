import java.util.*;

public class Traversals {

  /**
   * Returns the sum of the values of all leaf nodes in the given tree of integers.
   * A leaf node is defined as a node with no children.
   * If node is null, this method returns 0.
   *
   * @param node the node of the tree
   * @return the sum of leaf node values, or 0 if the tree is null
   */
  public static int sumLeafNodes(TreeNode<Integer> node) {

    if (node == null) {
      return 0;
    }

    // Checking the root node
    if (node.left == null && node.right == null) {
      return node.value;
    }

    int leafSum = 0;

    // Checking the left subtree
    if (node.left != null) {
      leafSum += sumLeafNodes(node.left);
    }

    // Checking the right subtree
    if (node.right != null) {
      leafSum += sumLeafNodes(node.right);
    }

    return leafSum;
  }

  /**
   * Counts the number of internal nodes (non-leaf nodes) in the given tree of integers.
   * An internal node has at least one child.
   * If node is null, this method returns 0.
   *
   * @param node the node of the tree
   * @return the count of internal nodes, or 0 if the tree is null
   */
  public static int countInternalNodes(TreeNode<Integer> node) {

    if (node == null) {
      return 0;
    }

    // Checking the root - Returning 0 if it has no children since it won't be an internal node
    if (node.left == null && node.right == null) {
      return 0;
    }

    int internalNodeCount = 1;

    if (node.left != null) {
      internalNodeCount += countInternalNodes(node.left);
    }

    if (node.right != null) {
      internalNodeCount += countInternalNodes(node.right);
    }

    return internalNodeCount;
  }

  /**
   * Creates a string by concatenating the string representation of each node's value
   * in a post-order traversal of the tree. For example, if the post-order visitation
   * encounters values "a", "b", and "c" in that order, the result is "abc".
   * If node is null, returns an empty string.
   *
   * @param node the node of the tree
   * @param <T>  the type of values stored in the tree
   * @return a post-order traversal string, or an empty string if the tree is null
   */
  public static <T> String buildPostOrderString(TreeNode<T> node) {

    if (node == null) {
      return "";
    }

    String concString = "";

    if (node.left != null) {
      concString += buildPostOrderString(node.left);
    }

    if (node.right != null) {
      concString += buildPostOrderString(node.right);
    }

    concString += node.value.toString();

    return concString;
  }

  /**
   * Collects the values of all nodes in the tree level by level, from top to bottom.
   * If node is null, returns an empty list.
   *
   * @param node the node of the tree
   * @param <T>  the type of values stored in the tree
   * @return a list of node values in a top-to-bottom order, or an empty list if the tree is null
   */
  public static <T> List<T> collectLevelOrderValues(TreeNode<T> node) {

    if (node == null) {
      return new ArrayList<>();
    }

    Queue<TreeNode<T>> queue = new LinkedList<>();
    List<T> levelOrder = new ArrayList<>();

    queue.add(node);

    while (!queue.isEmpty()) {
      TreeNode<T> current = queue.poll();
      levelOrder.add(current.value);

      if (current.left != null) {
        queue.add(current.left);
      }

      if (current.right != null) {
        queue.add(current.right);
      }
    }

    return levelOrder;
  }

  /**
   * Counts the distinct values in the given tree.
   * If node is null, returns 0.
   *
   * @param node the node of the tree
   * @return the number of unique values in the tree, or 0 if the tree is null
   */
  public static int countDistinctValues(TreeNode<Integer> node) {
        // 'Unique values' = HashSet/HashMap
        Set<Integer> uniValues = new HashSet<>();

        // Pass the nodes and set to the helper method
        cDVHelper(node, uniValues);

        // Return size of the set
        return uniValues.size();
  }

  public static void cDVHelper (TreeNode<Integer> node, Set<Integer> uniValues) {
    if (node == null) {
      return;
    }

    // Add root node to the set
    uniValues.add(node.value);

    // Move down left and right subtrees checking for unique values
    cDVHelper(node.left, uniValues);
    cDVHelper(node.right, uniValues);
  }

  /**
   * Determines whether there is at least one root-to-leaf path in the tree
   * where each successive node's value is strictly greater than the previous node's value.
   * If node is null, returns false.
   *
   * @param node the node of the tree
   * @return true if there exists a strictly increasing root-to-leaf path, false otherwise
   */
  public static boolean hasStrictlyIncreasingPath(TreeNode<Integer> node) {

    if (node == null) {
      return false;
    }

    return strictIncPathHelper(node, 0);
  }

  public static boolean strictIncPathHelper(TreeNode<Integer> node, int currentNodeValue) {
    if (node == null) {
      return false;
    }
    
    if (node.value <= currentNodeValue) {
      return false;
    }

    if (node.left == null && node.right == null) {
      return true;
    }

    return strictIncPathHelper(node.left, node.value) || strictIncPathHelper(node.right, node.value);
  }

  // OPTIONAL CHALLENGE
  /**
   * Checks if two trees have the same shape. Two trees have the same shape
   * if they have exactly the same arrangement of nodes, irrespective of the node values.
   * If both trees are null, returns true. If one is null and the other is not, returns false.
   *
   * @param nodeA the node of the first tree
   * @param nodeB the node of the second tree
   * @param <T>   the type of values stored in the trees
   * @return true if the trees have the same shape, false otherwise
   */
  public static <T> boolean haveSameShape(TreeNode<T> nodeA, TreeNode<T> nodeB) {
    if (nodeA == null && nodeB == null) {
      return true;
    }

    if (nodeA == null || nodeB == null) {
      return false;
    }

    return haveSameShape(nodeA.left, nodeB.left) && haveSameShape(nodeA.right, nodeB.right);
  }


  // OPTIONAL CHALLENGE
  // Very challenging!
  // Hints:
  // List.copyOf may be helpful
  // Consider adding a helper method
  // Consider keeping the current path in a separate variable
  // Consider removing the current node from the current path after the node's subtrees have been traversed.
  /**
   * Finds all paths from the root to every leaf in the given tree.
   * Each path is represented as a list of node values from root to leaf.
   * The paths should be added pre-order.
   * If node is null, returns an empty list.
   * 
   * Example:
   *
   *         1
   *        / \
   *       2   3
   *      / \    \
   *     4   5    6
   * 
   * Expected output:
   *   [[1, 2, 4], [1, 2, 5], [1, 3, 6]]
   * 
   * @param node the root node of the tree
   * @return a list of lists, where each inner list represents a root-to-leaf path in pre-order
   */
  public static <T> List<List<T>> findAllRootToLeafPaths(TreeNode<T> node) {

    if (node == null) {
      return new ArrayList<>();
    }

    List<List<T>> rToLPaths = new ArrayList<>();
    List<T> currentTrackedPath = new ArrayList<>();

    rToLPathHelper(node, rToLPaths, currentTrackedPath);
    
    return rToLPaths;
  }

  public static <T> void rToLPathHelper (TreeNode<T> node, List<List<T>> rToLPaths, List<T> currentTrackedPath) {
    
    if (node == null) {
      return;
    }

    currentTrackedPath.add(node.value);

    // This is where I started getting a little stuck
    if (node.left == null && node.right == null) {
      // ^ If both nodes are null, it's a leaf node
      rToLPaths.add(List.copyOf(currentTrackedPath));
      // ^ Make a copy of cTP and add it to rTLP
    } else {
      // If it's not a leaf node, recurse as normal down the path
      rToLPathHelper(node.left, rToLPaths, currentTrackedPath);
      rToLPathHelper(node.right, rToLPaths, currentTrackedPath);
    }

    // When the end of a path is reached, it removes the last value in cTP as it returns up the path
    currentTrackedPath.remove(currentTrackedPath.size() - 1);
  }

}
