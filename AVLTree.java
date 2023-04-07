/**
 * This class modifies the BinarySearchTree class to add AVL insertion and deletion to it. The only additions in this
 * class are the
 *
 * @author Mihir Phadke
 * @version 1
 */
public class AVLTree<T extends Comparable<T>> {

    private AVLNode<T> root;

    /**
     * This class represents the node of the tree. Each node contains data (of type 'T') and references to its left and
     * right nodes, and keeps track of the height of the tree at each node.
     * @param <T> arbitrary type which implements the Comparable interface
     */
    private static class AVLNode<T> {
        T data;
        int height;
        AVLNode<T> left;
        AVLNode<T> right;

        AVLNode(T data) {
            this.data = data;
            this.height = 1;
        }
    }

    /**
     * This constructor initialises the root to null.
     */
    public AVLTree() {
        root = null;
    }

    /**
     * The insert method adds a new element to the tree. It uses the recursive helper method below to traverse the tree
     * and find the appropriate position to insert the new element. If the element is already in the tree, it is not
     * inserted again.
     * @param element to be added to the tree
     */
    public void insert(T element) {
        root = insert(root, element);
    }

    /**
     * The insert method recursively inserts the element into the AVL tree based on the comparison with the current
     * node's data. The insertion process is the same as that in the BinarySearchTree class.
     *
     * After the insertion is complete, we update the height of the current node based on the heights of its left and
     * right subtrees. We then check if the node is unbalanced and perform appropriate rotations to balance the tree.
     * There are four cases we need to handle to balance the tree: LL, LR, RR, and RL. Each case involves one or two
     * rotations to adjust the structure of the tree. The getBalance method is used to calculate the balance factor of a
     * node, and the rotateLeft and rotateRight methods are used to perform the rotations.
     *
     * @param node root node
     * @param element element to be inserted
     * @return current node
     */
    private AVLNode<T> insert(AVLNode<T> node, T element) {
        if (node == null) {
            return new AVLNode<>(element);
        }
        if (element.compareTo(node.data) < 0) {
            node.left = insert(node.left, element);
        }
        else if (element.compareTo(node.data) > 0) {
            node.right = insert(node.right, element);
        }
        else {
            // element is already in the tree
            return node;
        }
        // update height of the current node
        node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));

        // check if the current node is balanced
        int balance = getBalance(node);

        // if the node is unbalanced, there are four cases to consider
        // left-left case
        if (balance > 1 && element.compareTo(node.left.data) < 0) {
            return rotateRight(node);
        }
        // left-right case
        if (balance > 1 && element.compareTo(node.left.data) > 0) {
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }
        // right-right case
        if (balance < -1 && element.compareTo(node.right.data) > 0) {
            return rotateLeft(node);
        }
        // right-left case
        if (balance < -1 && element.compareTo(node.right.data) < 0) {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }

        // return the current node
        return node;
    }

    /**
     * The delete method uses the recursive helper method below to traverse the tree and find the node that contains the
     * element to be deleted and delete it.
     * @param element element to be deleted
     */
    public void delete(T element) {
        root = delete(root, element);
    }

    /**
     * The delete method recursively deleted the element from the AVL tree based on the comparison with the current
     * node's data. The deletion process is the same as that in the BinarySearchTree class.
     *
     * After the deletion is complete, we update the height of the current node based on the heights of its left and
     * eight subtrees. We then check if the node is unbalanced and perform appropriate rotations to balance the tree.
     * rotateLeft and rotateRight are used to make the rotations.
     *
     * If the node has a balance factor greater than 1, it is left-heavy, and we check whether its left child has a
     * balance factor of 0 or 1 (L0 or L1), or -1 (L-1).
     * If the node has a balance factor less than 1, it is right-heavy, and we check whether its right child has a
     * balance factor of 0 or -1 (R0 or R-1), or 1 (R1).
     *
     * By checking the balance factors of the node and its children, we can handle all six possible cases that can arise
     * during AVL deletion. This ensures that the tree stays balanced and maintains the performance of binary search
     * operations.
     * @param node root node
     * @param element element to be deleted
     * @return current node
     */
    private AVLNode<T> delete(AVLNode<T> node, T element) {
        if (node == null) {
            return null;
        }
        if (element.compareTo(node.data) < 0) {
            node.left = delete(node.left, element);
        }
        else if (element.compareTo(node.data) > 0) {
            node.right = delete(node.right, element);
        }
        else {
            if (node.left == null && node.right == null) {
                node = null;
            }
            else if (node.left == null) {
                node = node.right;
            }
            else if (node.right == null) {
                node = node.left;
            }
            else {
                AVLNode<T> minNode = findMin(node.right);
                node.data = minNode.data;
                node.right = delete(node.right, minNode.data);
            }
        }

        if (node == null) {
            return null;
        }

        // update height of the current node
        node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));

        // check if the current node is balanced
        int balance = getBalance(node);

        // if the node is unbalanced, there are six cases to consider
        if (balance > 1) { // L0 or L1 case
            if (getBalance(node.left) == -1) { // L-1 case
                node.left = rotateLeft(node.left);
            }
            return rotateRight(node);
        } else if (balance < -1) { // R0 or R1 case
            if (getBalance(node.right) == 1) { // R1 case
                node.right = rotateRight(node.right);
            }
            return rotateLeft(node);
        }
        // return the current node
        return node;
    }

    /**
     * This method is used to find the minimum element in a subtree.
     * @param node root node
     * @return minimum child element of the root
     */
    private AVLNode<T> findMin(AVLNode<T> node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    /**
     * This method checks if an element is in the tree. It uses the recursive helper method below to traverse and find
     * the node that contains the element.
     * @param element element to be looked for
     * @return true or false
     */
    public boolean contains(T element) {
        return contains(root, element);
    }

    /**
     * This method recursively checks if the element to be found is in the right or left subtree of the root, and
     * traverses down till the element is found or not found.
     * @param node root node
     * @param element element being searched
     * @return current node
     */
    private boolean contains(AVLNode<T> node, T element) {
        if (node == null) {
            return false;
        }
        if (element.compareTo(node.data) < 0) {
            return contains(node.left, element);
        }
        else if (element.compareTo(node.data) > 0) {
            return contains(node.right, element);
        }
        else {
            return true;
        }
    }

    /**
     * This method uses the recursive helper method below to traverse through the tree and append the StringBuilder
     * instance to print the tree in pre-order.
     * @return String of the tree in pre-order traversal
     */
    public String toString() {
        StringBuilder builder = new StringBuilder();
        toString(builder, root);
        return builder.toString();
    }

    /**
     * This method recursively traverses through the tree and appends the StringBuilder instance in pre-order
     * traversal. (Used Dr. Casperson's notes to figure out how to print this recursively lol)
     * @param builder StringBuilder instance that is being appended
     * @param node current node
     */
    private void toString(StringBuilder builder, AVLNode<T> node) {
        if (node == null) {
            builder.append("null").append(" ");
            return;
        }
        builder.append(node.data).append(" ");
        toString(builder, node.left);
        toString(builder, node.right);
    }

    /**
     * This method is used to get the height of a node.
     * @param node desired node
     * @return height of the node
     */
    private int getHeight(AVLNode<T> node) {
        if (node == null) {
            return 0;
        }
        return node.height;
    }

    /**
     * This method is used to get the balance factor of a node.
     * @param node desired node
     * @return balance factor of a node
     */
    private int getBalance(AVLNode<T> node) {
        if (node == null) {
            return 0;
        }
        return getHeight(node.left) - getHeight(node.right);
    }

    /**
     * This method is used to perform a left rotation on a node to move its right child up to the parent position.
     * We first create a temporary node to hold the right child of the current node. We then update the right child of
     * the current node to be the left child of the temporary node. We update the left child of the temporary node to be
     * the current node. Finally, we update the heights of the nodes.
     * @param node location for left rotation
     * @return temporary node, which is the new root of the subtree
     */
    private AVLNode<T> rotateLeft(AVLNode<T> node) {
        AVLNode<T> temp = node.right;
        node.right = temp.left;
        temp.left = node;

        // update the heights of the nodes
        node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));
        temp.height = 1 + Math.max(getHeight(temp.left), getHeight(temp.right));

        return temp;
    }

    /**
     * This method is used to perform a right rotation on a node to move its left child up to the parent position.
     * We create a temporary node to hold the left child of the current node. We then update the left child of the
     * current node to be the right child of the temporary node. We update the right child of the temporary node to be
     * the current node. Finally, we update the heights of the nodes.
     * @param node location for right rotation
     * @return temporary node, which is the new root of the subtree
     */
    private AVLNode<T> rotateRight(AVLNode<T> node) {
        AVLNode<T> temp = node.left;
        node.left = temp.right;
        temp.right = node;

        // update the heights of the nodes
        node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));
        temp.height = 1 + Math.max(getHeight(temp.left), getHeight(temp.right));

        return temp;
    }

    // This section is to print the tree out in a top-down fashion. I had enough time to mess around with this.
    // This is obviously not my own algorithm for printing out a tree. I combined two algorithms I found online.
    // Just thought it would help the marker visualise the tree in a better way.
    public void drawTree() {
        drawTree("", root, false);
    }

    private void drawTree(String prefix, AVLNode<T> node, boolean isLeftChild) {
        if (node != null) {
            drawTree(prefix + (isLeftChild ? "│  " : "   "), node.right, false);
            System.out.println(prefix + (isLeftChild ? "└──" : "┌──") + node.data);
            drawTree(prefix + (isLeftChild ? "   " : "│  "), node.left, true);
        }
    }
}
