# DSA Study Repository

A personal Java repository for studying Data Structures & Algorithms in preparation for software engineering interviews. Each problem is self-contained, test-driven, and reviewed with complexity analysis.

## Stack

- **Language**: Java 17
- **Build**: Maven 3.9.16
- **Tests**: JUnit 5 + AssertJ

## Prerequisites

Install [SDKMAN](https://sdkman.io/):

```bash
curl -s "https://get.sdkman.io" | bash
source "$HOME/.sdkman/bin/sdkman-init.sh"
```

Install Java 17 and Maven 3.9.16 via SDKMAN:

```bash
sdk install java 17.0.19-tem
sdk install maven 3.9.16
```

## Repository Structure

```
.
├── docs/
│   ├── coding-patterns/          # DSA pattern reference guides
│   │   ├── arrays-and-hashing.md
│   │   ├── backtracking.md
│   │   ├── binary-search.md
│   │   ├── breadth-first-search.md
│   │   ├── depth-first-search.md
│   │   ├── graphs.md
│   │   ├── greedy.md
│   │   ├── heap-priority-queue.md
│   │   ├── intervals.md
│   │   ├── linked-list.md
│   │   ├── queue.md
│   │   ├── sliding-window.md
│   │   ├── stack.md
│   │   ├── topological-sort.md
│   │   ├── trees.md
│   │   ├── tries.md
│   │   ├── two-pointers.md
│   │   ├── 1d-dynamic-programming.md
│   │   └── 2d-dynamic-programming.md
│   └── design-patterns/          # Design pattern reference guides
│       ├── creational/
│       ├── structural/
│       └── behavioral/
│
└── src/
    ├── main/java/mx/jovannypcg/base/
    │   └── p<NN>_<problem>/
    │       ├── Solution.java    # Method stub with Javadoc — you write the logic
    │       ├── README.md        # Problem description, examples, constraints, hints
    │       ├── REVIEW.md        # Post-solve review (generated on "Done")
    │       └── SOLUTION.md      # Full solution reveal (generated on "Give up")
    └── test/java/mx/jovannypcg/base/
        └── p<NN>_<problem>/
            └── SolutionTest.java
```

### Problem package naming

Problems are numbered in the order they were attempted, using a zero-padded prefix and a `p` to keep the package name a valid Java identifier:

```
mx.jovannypcg.base.p01_twosum
mx.jovannypcg.base.p02_validanagram
...
```

### Files per problem

| File | Purpose |
|------|---------|
| `Solution.java` | Stub with Javadoc. You implement the method body. |
| `README.md` | Problem statement, I/O examples, constraints, and progressive hints. |
| `SolutionTest.java` | Exhaustive JUnit 5 tests covering happy path, edge cases, and constraint boundaries. |
| `REVIEW.md` | Generated when you type **"Done"** (after `SolutionTest` passes). Contains complexity analysis of your solution, the optimal approach, alternative approaches, and an algorithm trace for each. |
| `SOLUTION.md` | Generated when you type **"Give up"**. Contains all approaches ordered from most to least optimal, each with explanation, complexity, Java code, and an algorithm trace. Claude also implements the optimal solution in `Solution.java` and confirms tests pass. |

## Learning Guides (`./docs`)

- `docs/coding-patterns/` — one Markdown file per DSA pattern category, each with:
  - How to recognize the pattern
  - Step-by-step approach
  - Java code template
  - Up to 20 curated practice problems (LeetCode / NeetCode links)
- `docs/design-patterns/` — creational, structural, and behavioral design patterns, one Markdown file per pattern.

Read the relevant guide before starting a problem to prime your thinking on the pattern.

## Running Tests

Run all tests:

```bash
mvn test
```

Run tests for a specific problem:

```bash
mvn test -Dtest="SolutionTest" -pl .
```

## Solved Problems

| # | Problem | Category | Result |
|---|---------|----------|--------|
| 01 | [Contains Duplicate](src/main/java/mx/jovannypcg/base/p01_containsduplicate) | Arrays & Hashing | ✅ |
| 02 | [Valid Anagram](src/main/java/mx/jovannypcg/base/p02_validanagram) | Arrays & Hashing | ✅ |
| 03 | [Group Anagrams](src/main/java/mx/jovannypcg/base/p03_groupanagrams) | Arrays & Hashing | ✅ |
| 04 | [Top K Frequent Elements](src/main/java/mx/jovannypcg/base/p04_topkfrequent) | Arrays & Hashing | ✅ |
| 05 | [Valid Palindrome](src/main/java/mx/jovannypcg/base/p05_validpalindrome) | Two Pointers | ✅ |
| 06 | [Two Sum II](src/main/java/mx/jovannypcg/base/p06_twosum2) | Two Pointers | ✅ |
| 07 | [3Sum](src/main/java/mx/jovannypcg/base/p07_3sum) | Two Pointers | 💡 |
| 08 | [Number of Islands](src/main/java/mx/jovannypcg/base/p08_numberofislands) | Graphs | ✅ |
| 09 | [Path Sum](src/main/java/mx/jovannypcg/base/p09_pathsum) | Trees | ✅ |
| 10 | [Course Schedule](src/main/java/mx/jovannypcg/base/p10_courseschedule) | Topological Sort | ✅ |
| 11 | [Course Schedule II](src/main/java/mx/jovannypcg/base/p11_courseschedule2) | Topological Sort | ✅ |
| 12 | [Merge Intervals](src/main/java/mx/jovannypcg/base/p12_mergeintervals) | Intervals | ✅ |
| 13 | [Meeting Rooms](src/main/java/mx/jovannypcg/base/p13_meetingrooms) | Intervals | ✅ |
| 14 | [Binary Tree Level Order Traversal](src/main/java/mx/jovannypcg/base/p14_binarytreelevelorder) | Trees | ✅ |
| 15 | [Remove Duplicates from Sorted Array](src/main/java/mx/jovannypcg/base/p15_removeduplicates) | Two Pointers | ✅ |
| 16 | [Middle of the Linked List](src/main/java/mx/jovannypcg/base/p16_middleoflinkedlist) | Linked List | ✅ |
| 17 | [Move Zeros](src/main/java/mx/jovannypcg/base/p17_movezeros) | Two Pointers | ✅ |
| 18 | [Remove Nth Node From End of List](src/main/java/mx/jovannypcg/base/p18_removenthnode) | Linked List | ✅ |
| 19 | [Container With Most Water](src/main/java/mx/jovannypcg/base/p19_containerwithmostwater) | Two Pointers | ✅ |
| 20 | [Valid Palindrome II](src/main/java/mx/jovannypcg/base/p20_validpalindrome2) | Two Pointers | ✅ |
| 21 | [Valid Word Abbreviation](src/main/java/mx/jovannypcg/base/p21_validwordabbreviation) | Two Pointers | ✅ |
| 22 | [Merge Strings Alternately](src/main/java/mx/jovannypcg/base/p22_mergestringsalternately) | Two Pointers | ✅ |
| 23 | [Merge Sorted Array](src/main/java/mx/jovannypcg/base/p23_mergesortedarray) | Two Pointers | ✅ |
| 24 | [Merge Two 2D Arrays by Summing Values](src/main/java/mx/jovannypcg/base/p24_mergetwo2darrays) | Two Pointers | ✅ |
| 25 | [Squares of a Sorted Array](src/main/java/mx/jovannypcg/base/p25_squaresofsortedarray) | Two Pointers | ✅ |
| 26 | [Assign Cookies](src/main/java/mx/jovannypcg/base/p26_assigncookies) | Greedy | ✅ |
| 27 | [Find First Palindromic String in the Array](src/main/java/mx/jovannypcg/base/p27_firstpalindrome) | Two Pointers | ✅ |
| 28 | [Sort Array by Parity](src/main/java/mx/jovannypcg/base/p28_sortarraybyparity) | Two Pointers | ✅ |
| 29 | [Reverse Words in a String III](src/main/java/mx/jovannypcg/base/p29_reversewordsinstring3) | Two Pointers | ✅ |
| 30 | [Backspace String Compare](src/main/java/mx/jovannypcg/base/p30_backspacestringcompare) | Two Pointers | ✅ |

✅ = solved independently · 💡 = viewed solution

## Adding a New Exercise with Claude

Paste the problem description (or a link to it) and tell Claude to set it up:

```
Set up the next exercise: <problem name or description>
Source: <URL>  ← optional but recommended
```

Claude will automatically:
1. Determine the next index by inspecting existing packages.
2. Create `Solution.java` with the method stub and Javadoc.
3. Create `README.md` with the problem description, examples, constraints, and hints.
4. Create `SolutionTest.java` with an exhaustive test suite aligned to the constraints.

When you finish implementing `Solution.java`, type:

- **`Done`** → Claude runs `SolutionTest` (must pass), then writes `REVIEW.md` with complexity analysis, the optimal approach, alternatives, and an algorithm trace for each.
- **`Give up`** → Claude writes `SOLUTION.md` with all approaches explained (ordered from most to least optimal, each with an algorithm trace), implements the optimal solution in `Solution.java`, and confirms tests pass.
