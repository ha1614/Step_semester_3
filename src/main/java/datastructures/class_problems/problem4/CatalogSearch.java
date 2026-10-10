package datastructures.class_problems.problem4;

import java.util.Arrays;
import java.util.List;

/*
 * Same task as Problem 3, modelled with a BookRecord class and a recursive binary search.
 * Time: O(log n) per query  Space: O(log n) recursion stack (O(1) if written iteratively)
 * Pre-sorting costs O(n log n) once; with rare updates and frequent queries, that one-time cost is
 * amortised across many O(log n) lookups, far better than O(n) linear search per query.
 */
public class CatalogSearch {

    static String findBook(List<BookRecord> catalog, String targetIsbn) {
        return search(catalog, targetIsbn, 0, catalog.size() - 1);
    }

    private static String search(List<BookRecord> catalog, String target, int low, int high) {
        if (low > high) {
            return "Not Found";
        }
        int mid = low + (high - low) / 2;
        int cmp = catalog.get(mid).getIsbn().compareTo(target);
        if (cmp == 0) {
            return catalog.get(mid).getTitle();
        }
        return cmp < 0 ? search(catalog, target, mid + 1, high) : search(catalog, target, low, mid - 1);
    }

    public static void main(String[] args) {
        List<BookRecord> catalog = Arrays.asList(
                new BookRecord("0001112223", "Introduction to Algebra"),
                new BookRecord("0002223334", "Beginning Python"),
                new BookRecord("0003334445", "Classic Mythology"),
                new BookRecord("0004445556", "Data and Society"),
                new BookRecord("0005556667", "European History"));
        System.out.println(findBook(catalog, "0003334445")); // Classic Mythology
        System.out.println(findBook(catalog, "0009998887")); // Not Found
    }
}
