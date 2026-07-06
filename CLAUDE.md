# CLAUDE.md

This repository is a Java DSA (Data Structures & Algorithms) study project used to prepare for a software engineering interviews.

## Project Structure

- **Language**: Java 17
- **Build tool**: Maven (use `mvn`)
- **Test framework**: JUnit 5 + AssertJ
- **Base package**: `mx.jovannypcg.base`
- Source: `src/main/java/mx/jovannypcg/base/`
- Tests: `src/test/java/mx/jovannypcg/base/`

## Adding a New Coding Problem

Each coding problem lives in its own subpackage under `mx.jovannypcg.base`, prefixed with a zero-padded index to preserve resolution order.

### Naming convention

```
mx.jovannypcg.base.p<NN>_<problemname>
```

- `NN` is a two-digit zero-padded integer (e.g., `01`, `02`, `10`)
- `p` prefix makes the package name a valid Java identifier
- Example: `mx.jovannypcg.base.p01_twosum`

### Steps to add a problem

1. **Determine the next index** by listing existing subpackages under `src/main/java/mx/jovannypcg/base/` and incrementing the highest prefix.
2. **Create the solution class** at:
   `src/main/java/mx/jovannypcg/base/p<NN>_<name>/Solution.java`
   - Package declaration must match the directory: `package mx.jovannypcg.base.p<NN>_<name>;`
   - Include the method signature for the problem (stub body only — the user fills in the logic).
   - Add a well-formatted Javadoc comment on the class describing the problem. If a source URL is provided, include it with `@see <a href="...">Problem Source</a>`.
3. **Create `README.md`** in the same directory as `Solution.java`:
   `src/main/java/mx/jovannypcg/base/p<NN>_<name>/README.md`
   - Include the **date** the problem was added to the repository (use today's date).
   - Include the full **problem description** in readable prose (complement to the Javadoc).
   - Include **up to 5 input/output examples** with an explanation for each, in this format:
     ```
     Input: nums = [2,7,11,15], target = 9
     Output: [0,1]
     Explanation: Because nums[0] + nums[1] == 9, we return [0, 1].
     ```
   - Include a **Constraints** section listing all constraints from the problem description (e.g., input size bounds, value ranges, guarantees). The unit tests in `SolutionTest.java` must cover and align with these constraints — boundary values from the constraints should appear as test cases.
   - Include **up to 5 hints** to guide the user toward a solution without giving it away. Hints should be progressive — each one reveals a bit more than the last.
4. **Create the test class** at:
   `src/test/java/mx/jovannypcg/base/p<NN>_<name>/SolutionTest.java`
   - Same package as the solution class.
   - Use JUnit 5 (`@Test`) and AssertJ (`assertThat(...)`).
   - Every `@Test` method must have a `@DisplayName` annotation with a human-readable description of what the test checks (e.g., `@DisplayName("two courses, direct cycle → empty array")`).
   - Write one `@Test` method per case — do not use `@ParameterizedTest`. Individual test functions are easier to debug and follow.
   - Cover: happy path, edge cases (empty input, single element, duplicates, negatives, large values), and boundary values derived from the constraints in `README.md`.
5. **After generating all files**, end with the message:
   > Type **"Done"** to let me know you've finished, or **"Give up"** if you'd like to see the solution.

### Example layout

```
src/
  main/java/mx/jovannypcg/base/
    p01_twosum/
      Solution.java
      README.md
  test/java/mx/jovannypcg/base/
    p01_twosum/
      SolutionTest.java
```

### Example Solution.java

```java
package mx.jovannypcg.base.p01_twosum;

/**
 * Given an array of integers {@code nums} and an integer {@code target}, return the indices
 * of the two numbers that add up to {@code target}.
 *
 * <p>Each input has exactly one solution, and the same element may not be used twice.
 * The answer can be returned in any order.
 *
 * @see <a href="https://leetcode.com/problems/two-sum/">Two Sum - LeetCode</a>
 */
public class Solution {
    public int[] twoSum(int[] nums, int target) {
        throw new UnsupportedOperationException();
    }
}
```

### Example README.md

```markdown
# Two Sum

**Date added:** 2026-06-09

## Problem Description

Given an array of integers `nums` and an integer `target`, return the indices of the two
numbers that add up to `target`. Each input has exactly one solution, and the same element
may not be used twice. The answer can be returned in any order.

**Source:** https://leetcode.com/problems/two-sum/

## Examples

**Example 1**
```
Input: nums = [2,7,11,15], target = 9
Output: [0,1]
Explanation: Because nums[0] + nums[1] == 9, we return [0, 1].
```

**Example 2**
```
Input: nums = [3,2,4], target = 6
Output: [1,2]
Explanation: Because nums[1] + nums[2] == 6, we return [1, 2].
```

**Example 3**
```
Input: nums = [3,3], target = 6
Output: [0,1]
Explanation: The two 3s at indices 0 and 1 add up to 6.
```

## Constraints

- `2 <= nums.length <= 10^4`
- `-10^9 <= nums[i] <= 10^9`
- `-10^9 <= target <= 10^9`
- Only one valid answer exists.

## Hints

1. A brute-force approach would check every pair — what is its time complexity?
2. What data structure lets you look up a value in O(1)?
3. For each number, think about what value you *need* to find elsewhere in the array.
4. You can compute that needed value from the current number and the target.
5. A single pass through the array is enough if you store what you've seen so far.
```

## Running Tests

```bash
mvn test
```

To run tests for a single problem:

```bash
mvn test -Dtest="SolutionTest" -pl .
```

## "Done" Signal — Post-Exercise Review

When the user sends the message **"Done"**, read their completed `Solution.java` for the current problem and generate a review file at:

```
src/main/java/mx/jovannypcg/base/p<NN>_<name>/REVIEW.md
```

Before writing `REVIEW.md`, run the tests for the current problem:

```bash
mvn test -Dtest="SolutionTest" -pl .
```

If any tests fail, report which ones and why, and ask the user to fix them. Do not write `REVIEW.md` until all tests pass.

Even when all tests pass, carefully read `Solution.java` for logical bugs before writing `REVIEW.md`. If a bug is found:
1. Do **not** write `REVIEW.md`.
2. Describe the bug clearly and provide a concrete counterexample (input → expected output → actual output).
3. Add a failing test case for that counterexample to `SolutionTest.java`.
4. Ask the user to fix the bug. Resume the "Done" flow (re-run tests, then write `REVIEW.md`) only after all tests pass.

Once `REVIEW.md` is written, sync the **Solved Problems** table in `README.md` (see Syncing README below).

Open `REVIEW.md` with a summary table in this exact format:

```markdown
| | |
|---|---|
| **Solved on** | YYYY-MM-DD |
| **DSA Category** | <category> |
```

Use today's date for **Solved on**. For **DSA Category**, use only categories from this list:
Arrays & Hashing, Two Pointers, Sliding Window, Stack, Binary Search, Linked List, Trees, Heap / Priority Queue, Backtracking, Tries, Graphs, 1-D Dynamic Programming, 2-D Dynamic Programming, Greedy, Intervals.

The review must then continue with the following sections:

### 1. Your Solution Assessment
Evaluate the user's implementation honestly and clearly:
- Correctness: does it handle all cases (edge cases, constraints)?
- Code quality: clarity, naming, structure.
- Time complexity with explanation.
- Space complexity with explanation.
- **Algorithm trace**: include a trace of the user's solution on one representative example. Choose the format from the Algorithm Traces section below based on the algorithm type.

### 2. Optimal Approach
Describe the optimal solution (even if the user already found it):
- Explain the approach in plain language.
- Provide time and space complexity with explanation.
- Include a clean Java code example.
- **Algorithm trace**: a trace on one representative example (see Algorithm Traces section).

### 3. Alternative Approaches
List every other meaningful approach (brute force, suboptimal, or lateral), each with:
- A short description of the approach.
- Time and space complexity with explanation.
- A note on when it might be acceptable (e.g., small input, interview time pressure).
- **Algorithm trace**: a trace on one representative example (see Algorithm Traces section).

---

## "Give Up" Signal — Solution Reveal

When the user sends the message **"Give up"**, generate a solution file at:

```
src/main/java/mx/jovannypcg/base/p<NN>_<name>/SOLUTION.md
```

After writing `SOLUTION.md`, implement the optimal solution in `Solution.java` and run the tests to confirm they pass:

```bash
mvn test -Dtest="SolutionTest" -pl .
```

If any tests fail, fix the implementation before finishing. Report the test results to the user.

Even when all tests pass, carefully read the implemented `Solution.java` for logical bugs. If a bug is found:
1. Describe the bug clearly and provide a concrete counterexample (input → expected output → actual output).
2. Add a failing test case for that counterexample to `SolutionTest.java`.
3. Fix the bug in `Solution.java` and re-run the tests. Do not proceed until all tests pass.

Once `SOLUTION.md` is written and tests pass, sync the **Solved Problems** table in `README.md` (see Syncing README below).

Open `SOLUTION.md` with a summary table in this exact format:

```markdown
| | |
|---|---|
| **Created on** | YYYY-MM-DD |
| **DSA Category** | <category> |
```

Use today's date for **Created on**. For **DSA Category**, use only categories from this list:
Arrays & Hashing, Two Pointers, Sliding Window, Stack, Binary Search, Linked List, Trees, Heap / Priority Queue, Backtracking, Tries, Graphs, 1-D Dynamic Programming, 2-D Dynamic Programming, Greedy, Intervals.

`SOLUTION.md` must include all meaningful approaches to solve the problem. Order them from most optimal to least optimal. For each approach:
- A short, plain-language explanation of the strategy (concise — optimize for quick comprehension).
- Time complexity with a one-line explanation.
- Space complexity with a one-line explanation.
- A clean Java code example.
- **Algorithm trace**: a trace of the approach on one representative example. Choose the format from the Algorithm Traces section below based on the algorithm type.

The goal is that the user reads `SOLUTION.md` and immediately understands how to solve the problem. Keep explanations tight.

---

## Syncing README

After completing either the **"Done"** or **"Give up"** flow, rebuild the **Solved Problems** table in `README.md` from the actual state of `src/main/java/mx/jovannypcg/base/`. Do not rely on the existing table — derive it fresh:

1. List all subpackages under `mx.jovannypcg.base` (sorted by numeric prefix).
2. For each subpackage, determine:
   - **#** — the numeric prefix (e.g., `01`)
   - **Problem** — a human-readable name derived from the package suffix
   - **Category** — read from the DSA Category field in `REVIEW.md` or `SOLUTION.md` if present; otherwise leave blank
   - **Result** — `✅` if `REVIEW.md` exists, `💡` if only `SOLUTION.md` exists, `🔄` if neither exists yet (stub only)
3. Replace the entire Solved Problems table in `README.md` with the rebuilt version.

---

## Algorithm Traces

Every `REVIEW.md` and `SOLUTION.md` must include an algorithm trace for each approach, run against one representative example from `README.md`. Choose the format based on the algorithm type:

| Algorithm type | Trace format |
|---|---|
| Iterative loop, DP table filling | **Step table** — one row per iteration, columns = key variables |
| Two pointers, sliding window | **Annotated array** — show the array with pointer/window positions per step |
| Recursion, backtracking | **Call stack table** — depth, call arguments, return value |
| Tree / graph traversal | **Mermaid graph** — nodes visited in order, edges labelled with step number |
| Binary search | **Step table** + annotated array showing `lo`, `mid`, `hi` per iteration |
| Divide and conquer | **Mermaid sequence diagram** — recursive calls as a timeline |

### Step table example (Two Sum with hash map)
Input: `nums = [2, 7, 11, 15]`, `target = 9`

| i | nums[i] | complement | map before lookup | found? | map after |
|---|---------|------------|-------------------|--------|-----------|
| 0 | 2 | 7 | {} | No | {2→0} |
| 1 | 7 | 2 | {2→0} | **Yes** | — |
→ return `[0, 1]`

### Annotated array example (Two Pointers)
Input: `nums = [1, 2, 3, 4, 6]`, `target = 6`
```
L=0 R=4  sum=7 > 6 → move R left
[1, 2, 3, 4, 6]
 L           R

L=0 R=3  sum=5 < 6 → move L right
[1, 2, 3, 4, 6]
 L        R

L=1 R=3  sum=6 ✓
[1, 2, 3, 4, 6]
    L     R
→ return [2, 4] (1-indexed)
```

**Rule**: repeat the input array on every step so pointer positions are unambiguous. Never show only the pointer line without the array above it.

### Call stack table example (Fibonacci / recursion)
Input: `n = 4`

| Depth | Call | Returns |
|---|---|---|
| 0 | fib(4) | fib(3) + fib(2) |
| 1 | fib(3) | fib(2) + fib(1) |
| 2 | fib(2) | fib(1) + fib(0) = 1 |
| 2 | fib(1) | 1 |
| 1 | fib(2) | 1 |
→ fib(4) = 3

Annotated arrays and call stack tables must use plain Markdown (code blocks or tables). Tree/graph and sequence diagrams must use Mermaid. Never use ASCII art.

---

## Diagrams

Any diagram included in any `.md` file in this repository must be written in Mermaid (fenced with ` ```mermaid `). Do not use ASCII art or external image links for diagrams.

---

## What Claude Should NOT Do

- Do not implement the solution logic — leave method bodies as stubs (return default values or `throw new UnsupportedOperationException()`).
- Do not modify `BaseApplication.java` or `application.yaml`.
- Do not add dependencies to `pom.xml` unless explicitly asked.
