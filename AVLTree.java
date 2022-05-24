public class AVLTree {
    private TreeNode root;

    public AVLTree() {
        root = null;
    }

    private int getHeight(TreeNode node) {
        if (node == null) {
            return 0;
        }
        return node.height;
    }

    private int getBalance(TreeNode node) {
        int b = getHeight(node.left) - getHeight(node.right);
        // if (b > 1 || b < -1) {
        // System.out.println(node.val);
        // }
        return b;
    }

    private TreeNode rightRotation(TreeNode y) {
        TreeNode x = y.left;
        TreeNode T2 = x.right;

        x.right = y;
        y.left = T2;

        y.height = Math.max(getHeight(y.left), getHeight(y.right)) + 1;
        x.height = Math.max(getHeight(x.left), getHeight(x.right)) + 1;

        return x;
    }

    private TreeNode leftRotation(TreeNode x) {
        TreeNode y = x.right;
        TreeNode T2 = y.left;

        y.left = x;
        x.right = T2;

        x.height = Math.max(getHeight(x.left), getHeight(x.right)) + 1;
        y.height = Math.max(getHeight(y.left), getHeight(y.right)) + 1;

        return y;
    }

    private TreeNode balanceNode(TreeNode node) {
        // Left Left case
        if (getBalance(node) > 1 && getBalance(node.left) >= 0) {
            return rightRotation(node);
        } else if (getBalance(node) < -1 && getBalance(node.right) <= 0) {
            return leftRotation(node);
        } else if (getBalance(node) > 1 && getBalance(node.left) < 0) {
            node.left = leftRotation(node.left);
            return rightRotation(node);
        } else if (getBalance(node) < -1 && getBalance(node.right) > 0) {
            node.right = rightRotation(node.right);
            return leftRotation(node);
        } else {
            return node;
        }
    }

    public void insert(int val) {
        root = insertHelper(root, val);
    }

    private TreeNode insertHelper(TreeNode node, int val) {
        if (node == null) {
            return new TreeNode(val);
        }

        if (val > node.val) {
            node.right = insertHelper(node.right, val);
        } else if (val < node.val) {
            node.left = insertHelper(node.left, val);
        } else {
            return node;
        }

        node.height = Math.max(getHeight(node.left), getHeight(node.right)) + 1;

        return balanceNode(node);
    }

    private void delete(int val) {
        root = deleteHelper(root, val);
    }

    private TreeNode deleteHelper(TreeNode node, int val) {
        if (node == null) {
            return node;
        }

        if (val < node.val) {
            node.left = deleteHelper(node.left, val);
        } else if (val > node.val) {
            node.right = deleteHelper(node.right, val);
        } else {
            if (node.left == null || node.right == null) {
                if (node.left == null) {
                    node = node.right;
                } else {
                    node = node.left;
                }
            } else {
                TreeNode successor = node.right;
                while (successor.left != null) {
                    successor = successor.left;
                }
                node.val = successor.val;
                node.right = deleteHelper(node.right, successor.val);
            }
        }
        if (node == null) {
            return node;
        }

        node.height = Math.max(getHeight(node.left), getHeight(node.right)) + 1;
        return balanceNode(node);
    }

    public void printTree() {
        printTreeHelper("", root, false);
    }

    private void printTreeHelper(String prefix, TreeNode n, boolean isLeft) {
        if (n != null) {
            System.out.println(prefix + (isLeft ? "|-- " : "\\-- ") + n.val);
            printTreeHelper(prefix + (isLeft ? "|   " : "    "), n.left, true);
            printTreeHelper(prefix + (isLeft ? "|   " : "    "), n.right, false);
        }
    }

    public static void main(String[] args) {
        AVLTree tree = new AVLTree();
        // tree.insert(10);
        // tree.insert(20);
        // tree.insert(30);
        // tree.insert(40);
        // tree.insert(50);
        // tree.insert(25);
        // tree.printTree();

        tree.insert(9);
        tree.insert(5);
        tree.insert(10);
        tree.insert(0);
        tree.insert(6);
        tree.insert(11);
        tree.insert(-1);
        tree.insert(1);
        tree.printTree();
        tree.insert(2);
        tree.printTree();

        tree.delete(10);
        tree.printTree();

        tree.delete(1);
        tree.printTree();
    }
}

class TreeNode {
    int val;
    int height;
    TreeNode left, right;

    TreeNode(int val) {
        this.val = val;
        height = 1;
    }
}