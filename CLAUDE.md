# CLAUDE.md

This repository is a Java DSA (Data Structures & Algorithms) study project used to prepare for a software engineering interviews.

## Project Structure

- **Language**: Java 17
- **Build tool**: Maven (use `./mvnw`)
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
   - Use JUnit 5 (`@Test`, `@ParameterizedTest`, etc.) and AssertJ (`assertThat(...)`).
   - Every `@Test` method must have a `@DisplayName` annotation with a human-readable description of what the test checks (e.g., `@DisplayName("two courses, direct cycle → empty array")`).
   - Every `@ParameterizedTest` must set `name` to include the index and relevant inputs (e.g., `@ParameterizedTest(name = "[{index}] nums={0}, target={1} → {2}")`), so that each failing case is immediately identifiable in the output without looking up the source.
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
./mvnw test
```

To run tests for a single problem:

```bash
./mvnw test -Dtest="SolutionTest" -pl .
```

## "Done" Signal — Post-Exercise Review

When the user sends the message **"Done"**, read their completed `Solution.java` for the current problem and generate a review file at:

```
src/main/java/mx/jovannypcg/base/p<NN>_<name>/REVIEW.md
```

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

### 2. Optimal Approach
Describe the optimal solution (even if the user already found it):
- Explain the approach in plain language.
- Provide time and space complexity with explanation.
- Include a clean Java code example.

### 3. Alternative Approaches
List every other meaningful approach (brute force, suboptimal, or lateral), each with:
- A short description of the approach.
- Time and space complexity with explanation.
- A note on when it might be acceptable (e.g., small input, interview time pressure).

---

## "Give Up" Signal — Solution Reveal

When the user sends the message **"Give up"**, generate a solution file at:

```
src/main/java/mx/jovannypcg/base/p<NN>_<name>/SOLUTION.md
```

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

The goal is that the user reads `SOLUTION.md` and immediately understands how to solve the problem. Keep explanations tight.

---

## Diagrams

Any diagram included in any `.md` file in this repository must be written in Mermaid (fenced with ` ```mermaid `). Do not use ASCII art or external image links for diagrams.

---

## What Claude Should NOT Do

- Do not implement the solution logic — leave method bodies as stubs (return default values or `throw new UnsupportedOperationException()`).
- Do not modify `BaseApplication.java` or `application.yaml`.
- Do not add dependencies to `pom.xml` unless explicitly asked.
