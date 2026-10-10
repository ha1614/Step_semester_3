package linkedlist.assigment_problems.problem2;

/*
 * Doubly linked list with a 'current' pointer.
 * visit: O(1)  back/forward: O(steps)  Space: O(number of pages kept in history)
 * visit links a new node right after current and sets its next to null, cutting off every forward node.
 * Those nodes are no longer reachable from the list, so Java's garbage collector reclaims them.
 */
public class BrowserHistory {

    private static class Node {
        String url;
        Node prev;
        Node next;

        Node(String url) {
            this.url = url;
        }
    }

    private Node current;

    public BrowserHistory(String homepage) {
        current = new Node(homepage);
    }

    public void visit(String url) {
        Node node = new Node(url);
        node.prev = current;
        current.next = node; // drops all forward history
        current = node;
    }

    public String back(int steps) {
        while (steps-- > 0 && current.prev != null) {
            current = current.prev;
        }
        return current.url;
    }

    public String forward(int steps) {
        while (steps-- > 0 && current.next != null) {
            current = current.next;
        }
        return current.url;
    }

    public static void main(String[] args) {
        BrowserHistory h = new BrowserHistory("srm.edu");
        h.visit("a.com");
        h.visit("b.com");
        h.visit("c.com");
        System.out.println(h.back(1));    // b.com
        System.out.println(h.back(1));    // a.com
        System.out.println(h.forward(1)); // b.com
        h.visit("d.com");
        System.out.println(h.forward(2)); // d.com
        System.out.println(h.back(2));    // a.com
        System.out.println(h.back(7));    // srm.edu
    }
}
