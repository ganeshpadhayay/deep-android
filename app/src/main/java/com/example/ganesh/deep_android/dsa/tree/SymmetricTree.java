package com.example.ganesh.deep_android.dsa.tree;

/**
 * Created by Ganesh Padhayay on 22/06/21.
 */
public class SymmetricTree {
    public boolean isSymmetric(TreeNode root) {
        if (root == null)
            return true;
        return checkSym(root.left, root.right);
    }

    public static boolean checkSym(TreeNode left, TreeNode right) {
        if ((left == null) && (right == null))
            return true;
        if ((left == null) || (right == null))
            return false;
        if (left.val != right.val)
            return false;
        return ((checkSym(left.left, right.right)) & (checkSym(left.right, right.left)));
    }
}
