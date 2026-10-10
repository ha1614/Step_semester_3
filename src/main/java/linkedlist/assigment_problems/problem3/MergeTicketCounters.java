package linkedlist.assigment_problems.problem3;

/*
 * Merge by relinking existing nodes (one dummy node only).
 * Time: O(m + n)  Space: O(1) extra
 * Copying into an array and sorting would cost O((m + n) log(m + n)) time and O(m + n) extra space,
 * and it ignores the fact that both lists are already sorted.
 */
public class MergeTicketCounters {

    static class Node {
        int data;
        Node next;

        Node(int data) {
            this.data = data;
        }
    }

    static Node mergeSorted(Node a, Node b) {
        Node dummy = new Node(0);
        Node tail = dummy;
        while (a != null && b != null) {
            if (a.data <= b.data) {
                tail.next = a;
                a = a.next;
            } else {
                tail.next = b;
                b = b.next;
            }
            tail = tail.next;
        }
        tail.next = (a != null) ? a : b;
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
        if (head == null) {
            return "(empty)";
        }
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
        System.out.println(format(mergeSorted(build(3, 8, 15), build(5, 8, 12, 20)))); // 3 -> 5 -> 8 -> 8 -> 12 -> 15 -> 20
        System.out.println(format(mergeSorted(build(), build(4))));                   // 4
    }
}
