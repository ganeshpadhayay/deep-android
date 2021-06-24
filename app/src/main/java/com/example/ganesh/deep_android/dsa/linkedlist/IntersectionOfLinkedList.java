package com.example.ganesh.deep_android.dsa.linkedlist;

/**
 * Created by Ganesh Padhayay on 24/06/21.
 */

class ListNode {
    int val;
    ListNode next;

    ListNode(int x) {
        val = x;
        next = null;
    }
}

public class IntersectionOfLinkedList {

    public static void main(String[] args) {

        ListNode listNode1= new ListNode(1);
        listNode1.next = new ListNode(9);
        listNode1.next.next = new ListNode(1);
        listNode1.next.next.next = new ListNode(2);
        listNode1.next.next.next.next = new ListNode(4);

        ListNode listNode2= new ListNode(3);
        listNode2.next = listNode1.next.next.next;

        System.out.println(new IntersectionOfLinkedList().getIntersectionNode(listNode1, listNode2));
    }

    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) return null;
        int size1 = 0, size2 = 0;
        ListNode tempA = headA, tempB = headB;
        while (tempA != null) {
            size1++;
            tempA = tempA.next;
        }
        while (tempB != null) {
            size2++;
            tempB = tempB.next;
        }
        int diff;
        if (size1 > size2) {
            diff = size1 - size2;
            for (int i = 0; i < diff; i++) {
                if (headA.next != null)
                    headA = headA.next;
            }
        } else {
            diff = size2 - size1;
            for (int i = 0; i < diff; i++) {
                if (headB.next != null)
                    headB = headB.next;
            }
        }

        while (headA != null && headB != null) {
            if (headA == headB) {
                return headA;
            }
            headA = headA.next;
            headB = headB.next;
        }
        return null;
    }
}
