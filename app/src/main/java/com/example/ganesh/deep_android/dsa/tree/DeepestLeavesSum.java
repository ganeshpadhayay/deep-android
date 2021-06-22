package com.example.ganesh.deep_android.dsa.tree;

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode() {
    }

    TreeNode(int val) {
        this.val = val;
    }

    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}

class DeepestLeavesSum {

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);

        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);

        root.right.left = null;
        root.right.right = new TreeNode(6);

        root.left.left.left = new TreeNode(7);
        root.left.left.right = null;

        root.left.right.left = null;
        root.left.right.right = null;

        root.right.right.left = null;
        root.right.right.right = new TreeNode(8);

        System.out.println(new DeepestLeavesSum().deepestLeavesSum(root));

    }

    public int deepestLeavesSum(TreeNode root) {
        int h = height(root);
        return addValuesAtGivenLevel(root, h, 0);
    }

    /* Print nodes at the current level */
    private static int addValuesAtGivenLevel(TreeNode root, int level, int sum) {
        if (root == null)
            return 0;
        if (level == 1)
            return root.val;
        else if (level > 1) {
            return addValuesAtGivenLevel(root.left, level - 1, sum) + addValuesAtGivenLevel(root.right, level-1, sum);
        }
        return 0;
    }

    private static int height(TreeNode root) {
        if (root == null)
            return 0;
        else {
            /* compute  height of each subtree */
            int leftHeight = height(root.left);
            int rightHeight = height(root.right);

            /* use the larger one */
            if (leftHeight > rightHeight)
                return (leftHeight + 1);
            else return (rightHeight + 1);
        }
    }
}