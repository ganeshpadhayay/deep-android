package com.example.ganesh.deep_android.dsa.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ganesh Padhayay on 22/06/21.
 */
public class BinaryTreeInorderTraversal {

    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> ans = new ArrayList<Integer>();
        if (root == null) return ans;
        if (root.left == null && root.right == null) {
            ans.add(root.val);
            return ans;
        }

        helperInorderTraversal(root, ans);
        return ans;
    }

    static void helperInorderTraversal(TreeNode node, List<Integer> ans) {
        if (node == null)
            return;

        /* first recur on left child */
        helperInorderTraversal(node.left, ans);

        /* then print the data of node */
        ans.add(node.val);

        /* now recur on right child */
        helperInorderTraversal(node.right, ans);
    }
}
