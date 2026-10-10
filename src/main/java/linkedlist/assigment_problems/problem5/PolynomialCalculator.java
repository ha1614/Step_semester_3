package linkedlist.assigment_problems.problem5;

/*
 * Merge of two exponent-descending lists into a NEW list.
 * Time: O(m + n)  Space: O(m + n) for the result list (O(1) extra besides it).
 */
public class PolynomialCalculator {

    static class Node {
        int coeff;
        int exp;
        Node next;

        Node(int coeff, int exp) {
            this.coeff = coeff;
            this.exp = exp;
        }
    }

    static Node addPolynomials(Node p, Node q) {
        Node dummy = new Node(0, 0);
        Node tail = dummy;
        while (p != null || q != null) {
            int coeff;
            int exp;
            if (q == null || (p != null && p.exp > q.exp)) {
                coeff = p.coeff;
                exp = p.exp;
                p = p.next;
            } else if (p == null || q.exp > p.exp) {
                coeff = q.coeff;
                exp = q.exp;
                q = q.next;
            } else {
                coeff = p.coeff + q.coeff;
                exp = p.exp;
                p = p.next;
                q = q.next;
            }
            if (coeff != 0) {
                tail.next = new Node(coeff, exp);
                tail = tail.next;
            }
        }
        return dummy.next;
    }

    // builds from pairs: coeff1, exp1, coeff2, exp2, ...
    static Node build(int... pairs) {
        Node dummy = new Node(0, 0);
        Node tail = dummy;
        for (int i = 0; i < pairs.length; i += 2) {
            tail.next = new Node(pairs[i], pairs[i + 1]);
            tail = tail.next;
        }
        return dummy.next;
    }

    static String format(Node head) {
        if (head == null) {
            return "0";
        }
        StringBuilder sb = new StringBuilder();
        for (Node n = head; n != null; n = n.next) {
            int abs = Math.abs(n.coeff);
            if (n == head) {
                if (n.coeff < 0) {
                    sb.append("-");
                }
            } else {
                sb.append(n.coeff < 0 ? " - " : " + ");
            }
            if (abs != 1 || n.exp == 0) {
                sb.append(abs);
            }
            if (n.exp >= 1) {
                sb.append("x");
            }
            if (n.exp > 1) {
                sb.append("^").append(n.exp);
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        Node p1 = build(5, 3, 4, 2, 2, 0);   // 5x^3 + 4x^2 + 2
        Node q1 = build(5, 2, -3, 1, 1, 0);  // 5x^2 - 3x + 1
        System.out.println(format(addPolynomials(p1, q1))); // 5x^3 + 9x^2 - 3x + 3

        Node p2 = build(3, 2, 1, 0);         // 3x^2 + 1
        Node q2 = build(-3, 2, 4, 1);        // -3x^2 + 4x
        System.out.println(format(addPolynomials(p2, q2))); // 4x + 1
    }
}
