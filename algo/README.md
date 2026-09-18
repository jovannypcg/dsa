# Algo

A personal Java project for studying Data Structures & Algorithms in preparation for software engineering interviews. Each problem is self-contained, test-driven, and reviewed with complexity analysis.

This is the DSA half of the parent repository; see `../system-design/` for the system design counterpart and `../docs/` for shared reference guides.

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

This project lives at `algo/` inside the parent repository:

```
.                                   (repo root)
├── docs/                           # shared reference guides (see below)
├── algo/                           # ← this project
│   ├── CLAUDE.md
│   ├── README.md                   # you are here
│   ├── pom.xml
│   └── src/
│       ├── main/java/mx/jovannypcg/algo/
│       │   └── p<NNN>_<problem>/
│       │       ├── Solution.java    # Method stub with Javadoc — you write the logic
│       │       ├── README.md        # Problem description, examples, constraints, hints
│       │       ├── REVIEW.md        # Post-solve review (generated on "Done")
│       │       └── SOLUTION.md      # Full solution reveal (generated on "Give up")
│       └── test/java/mx/jovannypcg/algo/
│           └── p<NNN>_<problem>/
│               └── SolutionTest.java
└── system-design/                  # system design counterpart, see its own CLAUDE.md
```

### Problem package naming

Problems are numbered in the order they were attempted, using a zero-padded prefix and a `p` to keep the package name a valid Java identifier:

```
mx.jovannypcg.algo.p001_twosum
mx.jovannypcg.algo.p002_validanagram
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

## Learning Guides (`../docs`)

- `../docs/coding-patterns/` — one Markdown file per DSA pattern category, each with:
  - How to recognize the pattern
  - Step-by-step approach
  - Java code template
  - Up to 20 curated practice problems (LeetCode / NeetCode links)
- `../docs/design-patterns/` — creational, structural, and behavioral design patterns, one Markdown file per pattern.

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
| 001 | [Contains Duplicate](src/main/java/mx/jovannypcg/algo/p001_containsduplicate) | Arrays & Hashing | ✅ |
| 002 | [Valid Anagram](src/main/java/mx/jovannypcg/algo/p002_validanagram) | Arrays & Hashing | ✅ |
| 003 | [Group Anagrams](src/main/java/mx/jovannypcg/algo/p003_groupanagrams) | Arrays & Hashing | ✅ |
| 004 | [Top K Frequent Elements](src/main/java/mx/jovannypcg/algo/p004_topkfrequent) | Arrays & Hashing | ✅ |
| 005 | [Valid Palindrome](src/main/java/mx/jovannypcg/algo/p005_validpalindrome) | Two Pointers | ✅ |
| 006 | [Two Sum II](src/main/java/mx/jovannypcg/algo/p006_twosum2) | Two Pointers | ✅ |
| 007 | [3Sum](src/main/java/mx/jovannypcg/algo/p007_3sum) | Two Pointers | ✅ |
| 008 | [Number of Islands](src/main/java/mx/jovannypcg/algo/p008_numberofislands) | Graphs | ✅ |
| 009 | [Path Sum](src/main/java/mx/jovannypcg/algo/p009_pathsum) | Trees | ✅ |
| 010 | [Course Schedule](src/main/java/mx/jovannypcg/algo/p010_courseschedule) | Topological Sort | ✅ |
| 011 | [Course Schedule II](src/main/java/mx/jovannypcg/algo/p011_courseschedule2) | Topological Sort | ✅ |
| 012 | [Merge Intervals](src/main/java/mx/jovannypcg/algo/p012_mergeintervals) | Intervals | ✅ |
| 013 | [Meeting Rooms](src/main/java/mx/jovannypcg/algo/p013_meetingrooms) | Intervals | ✅ |
| 014 | [Binary Tree Level Order Traversal](src/main/java/mx/jovannypcg/algo/p014_binarytreelevelorder) | Trees | ✅ |
| 015 | [Remove Duplicates from Sorted Array](src/main/java/mx/jovannypcg/algo/p015_removeduplicates) | Two Pointers | ✅ |
| 016 | [Middle of the Linked List](src/main/java/mx/jovannypcg/algo/p016_middleoflinkedlist) | Linked List | ✅ |
| 017 | [Move Zeros](src/main/java/mx/jovannypcg/algo/p017_movezeros) | Two Pointers | ✅ |
| 018 | [Remove Nth Node From End of List](src/main/java/mx/jovannypcg/algo/p018_removenthnode) | Linked List | ✅ |
| 019 | [Container With Most Water](src/main/java/mx/jovannypcg/algo/p019_containerwithmostwater) | Two Pointers | ✅ |
| 020 | [Valid Palindrome II](src/main/java/mx/jovannypcg/algo/p020_validpalindrome2) | Two Pointers | ✅ |
| 021 | [Valid Word Abbreviation](src/main/java/mx/jovannypcg/algo/p021_validwordabbreviation) | Two Pointers | ✅ |
| 022 | [Merge Strings Alternately](src/main/java/mx/jovannypcg/algo/p022_mergestringsalternately) | Two Pointers | ✅ |
| 023 | [Merge Sorted Array](src/main/java/mx/jovannypcg/algo/p023_mergesortedarray) | Two Pointers | ✅ |
| 024 | [Merge Two 2D Arrays by Summing Values](src/main/java/mx/jovannypcg/algo/p024_mergetwo2darrays) | Two Pointers | ✅ |
| 025 | [Squares of a Sorted Array](src/main/java/mx/jovannypcg/algo/p025_squaresofsortedarray) | Two Pointers | ✅ |
| 026 | [Assign Cookies](src/main/java/mx/jovannypcg/algo/p026_assigncookies) | Greedy | ✅ |
| 027 | [Find First Palindromic String in the Array](src/main/java/mx/jovannypcg/algo/p027_firstpalindrome) | Two Pointers | ✅ |
| 028 | [Sort Array by Parity](src/main/java/mx/jovannypcg/algo/p028_sortarraybyparity) | Two Pointers | ✅ |
| 029 | [Reverse Words in a String III](src/main/java/mx/jovannypcg/algo/p029_reversewordsinstring3) | Two Pointers | ✅ |
| 030 | [Backspace String Compare](src/main/java/mx/jovannypcg/algo/p030_backspacestringcompare) | Two Pointers | ✅ |
| 031 | [Check if Two String Arrays are Equivalent](src/main/java/mx/jovannypcg/algo/p031_arraystringsareequal) | Two Pointers | ✅ |
| 032 | [Apply Operations to an Array](src/main/java/mx/jovannypcg/algo/p032_applyoperationstoarray) | Arrays & Hashing | ✅ |
| 033 | [Two Sum Less Than K](src/main/java/mx/jovannypcg/algo/p033_twosumlessthank) | Two Pointers | ✅ |
| 034 | [Product of Array Except Self](src/main/java/mx/jovannypcg/algo/p034_productofarrayexceptself) | Arrays & Hashing | ✅ |
| 035 | [Valid Sudoku](src/main/java/mx/jovannypcg/algo/p035_validsudoku) | Arrays & Hashing | ✅ |
| 036 | [Longest Consecutive Sequence](src/main/java/mx/jovannypcg/algo/p036_longestconsecutivesequence) | Arrays & Hashing | ✅ |
| 037 | [Reverse Linked List](src/main/java/mx/jovannypcg/algo/p037_reverselinkedlist) | Linked List | ✅ |
| 038 | [Merge Two Sorted Lists](src/main/java/mx/jovannypcg/algo/p038_mergetwosortedlists) | Linked List | ✅ |
| 039 | [Min Stack](src/main/java/mx/jovannypcg/algo/p039_minstack) | Stack | ✅ |
| 040 | [Evaluate Reverse Polish Notation](src/main/java/mx/jovannypcg/algo/p040_evaluatereversepolishnotation) | Stack | ✅ |
| 041 | [Search a 2D Matrix](src/main/java/mx/jovannypcg/algo/p041_searcha2dmatrix) | Binary Search | ✅ |
| 042 | [Invert Binary Tree](src/main/java/mx/jovannypcg/algo/p042_inverttree) | Trees | ✅ |
| 043 | [Maximum Depth of Binary Tree](src/main/java/mx/jovannypcg/algo/p043_maxdepthbinarytree) | Trees | ✅ |
| 044 | [Diameter of Binary Tree](src/main/java/mx/jovannypcg/algo/p044_diameterofbinarytree) | Trees | ✅ |
| 045 | [Balanced Binary Tree](src/main/java/mx/jovannypcg/algo/p045_balancedbinarytree) | Trees | ✅ |
| 046 | [Same Tree](src/main/java/mx/jovannypcg/algo/p046_sametree) | Trees | ✅ |
| 047 | [Subtree of Another Tree](src/main/java/mx/jovannypcg/algo/p047_subtreeofanothertree) | Trees | ✅ |
| 048 | [Lowest Common Ancestor of a Binary Search Tree](src/main/java/mx/jovannypcg/algo/p048_lowestcommonancestorbst) | Trees | ✅ |
| 049 | [Binary Tree Right Side View](src/main/java/mx/jovannypcg/algo/p049_binarytreerightsideview) | Trees | ✅ |
| 050 | [Generate All Binary Strings (A/B)](src/main/java/mx/jovannypcg/algo/p050_generateallbinarystrings) | Backtracking | ✅ |
| 051 | [Subsets](src/main/java/mx/jovannypcg/algo/p051_subsets) | Backtracking | ✅ |
| 052 | [Combination Sum](src/main/java/mx/jovannypcg/algo/p052_combinationsum) | Backtracking | ✅ |
| 053 | [Combination Sum II](src/main/java/mx/jovannypcg/algo/p053_combinationsum2) | Backtracking | ✅ |
| 054 | [Permutations](src/main/java/mx/jovannypcg/algo/p054_permutations) | Backtracking | ✅ |
| 055 | [Subsets II](src/main/java/mx/jovannypcg/algo/p055_subsets2) | Backtracking | ✅ |
| 056 | [Generate Parentheses](src/main/java/mx/jovannypcg/algo/p056_generateparentheses) | Backtracking | ✅ |
| 057 | [Word Search](src/main/java/mx/jovannypcg/algo/p057_wordsearch) | Backtracking | ✅ |
| 058 | [LRU Cache](src/main/java/mx/jovannypcg/algo/p058_lrucache) | Linked List | ✅ |
| 059 | [Kth Largest Element in a Stream](src/main/java/mx/jovannypcg/algo/p059_kthlargestelementinastream) | Heap / Priority Queue | ✅ |
| 060 | [Kth Smallest Element in a Stream](src/main/java/mx/jovannypcg/algo/p060_kthsmallestelementinastream) | Heap / Priority Queue | ✅ |
| 061 | [Implement Trie (Prefix Tree)](src/main/java/mx/jovannypcg/algo/p061_implementtrie) | Tries | ✅ |
| 062 | [Last Stone Weight](src/main/java/mx/jovannypcg/algo/p062_laststoneweight) | Heap / Priority Queue | ✅ |
| 063 | [Count Good Nodes in Binary Tree](src/main/java/mx/jovannypcg/algo/p063_countgoodnodesinbinarytree) | Trees | ✅ |
| 064 | [Validate Binary Search Tree](src/main/java/mx/jovannypcg/algo/p064_validatebinarysearchtree) | Trees | ✅ |
| 065 | [Kth Smallest Element in a BST](src/main/java/mx/jovannypcg/algo/p065_kthsmallestelementinabst) | Trees | ✅ |
| 066 | [Shortest Path in Binary Matrix](src/main/java/mx/jovannypcg/algo/p066_shortestpathinbinarymatrix) | Graphs | ✅ |
| 067 | [Max Area of Island](src/main/java/mx/jovannypcg/algo/p067_maxareaofisland) | Graphs | ✅ |
| 068 | [Clone Graph](src/main/java/mx/jovannypcg/algo/p068_clonegraph) | Graphs | ✅ |
| 069 | [Islands and Treasures](src/main/java/mx/jovannypcg/algo/p069_islandsandtreasures) | Graphs | ✅ |
| 070 | [Rotting Oranges](src/main/java/mx/jovannypcg/algo/p070_rottingoranges) | Graphs | 💡 |
| 071 | [Fibonacci Number](src/main/java/mx/jovannypcg/algo/p071_fibonaccinumber) | 1-D Dynamic Programming | ✅ |
| 072 | [Climbing Stairs](src/main/java/mx/jovannypcg/algo/p072_climbingstairs) | 1-D Dynamic Programming | ✅ |
| 073 | [Min Cost Climbing Stairs](src/main/java/mx/jovannypcg/algo/p073_mincostclimbingstairs) | 1-D Dynamic Programming | ✅ |
| 074 | [House Robber](src/main/java/mx/jovannypcg/algo/p074_houserobber) | 1-D Dynamic Programming | ✅ |
| 075 | [Leaf-Similar Trees](src/main/java/mx/jovannypcg/algo/p075_leafsimilartrees) | Trees | ✅ |
| 076 | [Leaf-Concatenated Tree Equality](src/main/java/mx/jovannypcg/algo/p076_leafconcatenatedtreeequality) | Trees | ✅ |
| 077 | [Network Delay Time](src/main/java/mx/jovannypcg/algo/p077_networkdelaytime) | Graphs | 💡 |
| 078 | [Rotate Image](src/main/java/mx/jovannypcg/algo/p078_rotateimage) | Arrays & Hashing | ✅ |
| 079 | [Spiral Matrix](src/main/java/mx/jovannypcg/algo/p079_spiralmatrix) | Arrays & Hashing | ✅ |
| 080 | [Spiral Matrix II](src/main/java/mx/jovannypcg/algo/p080_spiralmatrixii) | Arrays & Hashing | ✅ |
| 081 | [Topological Sort (Kahn's Algorithm)](src/main/java/mx/jovannypcg/algo/p081_topologicalsort) | Topological Sort | ✅ |
| 082 | [Alien Dictionary](src/main/java/mx/jovannypcg/algo/p082_aliendictionary) | Graphs | ✅ |
| 083 | [Meeting Rooms II](src/main/java/mx/jovannypcg/algo/p083_meetingrooms2) | Heap / Priority Queue | ✅ |
| 084 | [Insert Interval](src/main/java/mx/jovannypcg/algo/p084_insertinterval) | Intervals | 💡 |

✅ = solved independently · 💡 = viewed solution · 🔄 = in progress

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
