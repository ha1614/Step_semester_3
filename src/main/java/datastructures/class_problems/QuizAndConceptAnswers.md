# Week 9 – Practice: Quiz & Concept Answers

## Part B — Quiz

| Q | Answer |
|---|--------|
| 1 | **B** – O(n²) → O(n) time with O(1) extra space |
| 2 | **C** – Traversal |
| 3 | **C** – O(1) (`prefix[r+1] - prefix[l]`) |
| 4 | **C** – Users expect immediate responses for a frequently used feature |
| 5 | **C** – Arbitrary insert/delete shifts elements: O(n) each |
| 6 | **C** – Hash-based structure |
| 7 | **C** – The data must be sorted |
| 8 | **A, C, E** |
| 9 | **B, C, D** (fixed size is only one variant; windows may also be variable) |
| 10 | **C** – O(n) |

## Part C — Concept Questions

**1.** Big-O describes how cost grows as n → ∞, because the large inputs are the ones where performance matters. In 3n + 10 the linear term dominates, so it is O(n). In n² + 2 the quadratic term dominates, so it is O(n²). Constants (3, 10, 2) and lower-order terms are dropped because they shrink in importance as n grows. B may win for tiny n (e.g. n = 2: 6 vs 16 steps), but past a small crossover (n ≈ 5), n² grows much faster than 3n. So A is the asymptotically more efficient algorithm.

**2.** Primitive types (int, double, char, boolean) hold a single value directly, usually in a fixed-size slot on the stack or inline. Non-primitive structures (arrays, classes, lists, maps) group many values and model relationships between them. They usually live on the heap and are reached through references. Example: a student record with name, roll number, marks[] and address. Keeping these as loose parallel primitive variables is error-prone. A `Student` object (or an array/list of them) keeps related values together, enforces consistency, and lets you pass, sort and search records as a unit.

**3.** Binary search throws away half of the remaining range on each comparison. That only works if everything left of `mid` is ≤ it and everything right is ≥ it, i.e. the data is **sorted**. On unsorted data the discarded half might contain the target, so the result is wrong. Sorting first costs O(n log n), which is more than a single O(n) linear search. It only pays off when many searches are run on the same data, each then costing O(log n).
