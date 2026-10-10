package linkedlist.assigment_problems.problem1;

/*
 * Single pass with two pointers and a dummy node.
 * Time: O(n)  Space: O(1)
 * Removing the head (k == length) needs no special case: the trailing pointer starts at the dummy node,
 * so when the lead falls off the end, trail is the dummy and trail.next (the head) is unlinked.
 * We then return dummy.next, which is the new head.
 */
public class CancelKthLastOrder {

    static class Node {
        int data;
        Node next;

        Node(int data) {
            this.data = data;
        }
    }

    static Node removeKthFromEnd(Node head, int k) {
        Node dummy = new Node(0);
        dummy.next = head;
        Node lead = dummy;
        Node trail = dummy;
        for (int i = 0; i <= k; i++) { // lead moves k + 1 steps ahead
            lead = lead.next;
        }
        while (lead != null) {
            lead = lead.next;
            trail = trail.next;
        }
        trail.next = trail.next.next;
        return dummy.next;
    }

    static Node build(int... values) {
        Node dummy = new Node(0);
        Node tail = dummy;
        for (int v : values) {
            tail.next = new Node(v);
            tail = tail.next;
        }
        return dummy.next;
    }

    static String format(Node head) {
        StringBuilder sb = new StringBuilder();
        for (Node n = head; n != null; n = n.next) {
            sb.append(n.data);
            if (n.next != null) {
                sb.append(" -> ");
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(format(removeKthFromEnd(build(10, 20, 30, 40, 50), 2))); // 10 -> 20 -> 30 -> 50
        System.out.println(format(removeKthFromEnd(build(10, 20, 30, 40, 50), 5))); // 20 -> 30 -> 40 -> 50
    }
}
