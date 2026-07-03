| | |
|---|---|
| **Solved on** | 2026-07-02 |
| **DSA Category** | Two Pointers |

## 1. Your Solution Assessment

**Correctness:** Handles every case in the test suite (16/16 passing), including the
minimum size `n = 2`, all-zero heights, duplicate heights, strictly increasing/decreasing
sequences, and the `10^4` height boundary. The `heights == null || heights.length < 2`
guard is defensive — the constraint `2 <= n <= 10^5` guarantees it's never hit, but it
doesn't hurt. The core logic is the textbook optimal two-pointer approach: start at both
ends (widest container), and always move the pointer at the *shorter* line, since keeping
it fixed can never produce a taller container and the width can only shrink from there.

**Code quality:** Clean and readable. Variable names (`left`, `right`, `distance`,
`minHeight`, `area`, `maxArea`) make the formula `area = distance * minHeight` self-evident
without needing the comment. The one comment that is present (`// The minimum height
decides the next move`) earns its place — it explains *why* the shorter side moves, which
isn't obvious just from reading `if (heights[left] <= heights[right])`.

**Time complexity:** O(n) — `left` and `right` move toward each other and the loop stops
the moment they meet, so each index is visited at most once.

**Space complexity:** O(1) — only a fixed number of scalar variables regardless of input size.

**Algorithm trace** (annotated array, `L`/`R` pointers) on `height = [1,8,6,2,5,4,8,3,7]`:

```
L=0 R=8  area = min(1,7) * 8 = 8   maxArea=8   heights[L] <= heights[R] → move L
[1, 8, 6, 2, 5, 4, 8, 3, 7]
 L                       R

L=1 R=8  area = min(8,7) * 7 = 49  maxArea=49  heights[L] > heights[R]  → move R
[1, 8, 6, 2, 5, 4, 8, 3, 7]
    L                    R

L=1 R=7  area = min(8,3) * 6 = 18  maxArea=49  heights[L] > heights[R]  → move R
[1, 8, 6, 2, 5, 4, 8, 3, 7]
    L                 R

L=1 R=6  area = min(8,8) * 5 = 40  maxArea=49  heights[L] <= heights[R] → move L
[1, 8, 6, 2, 5, 4, 8, 3, 7]
    L              R

L=2 R=6  area = min(6,8) * 4 = 24  maxArea=49  heights[L] <= heights[R] → move L
[1, 8, 6, 2, 5, 4, 8, 3, 7]
       L           R

L=3 R=6  area = min(2,8) * 3 = 6   maxArea=49  heights[L] <= heights[R] → move L
[1, 8, 6, 2, 5, 4, 8, 3, 7]
          L        R

L=4 R=6  area = min(5,8) * 2 = 10  maxArea=49  heights[L] <= heights[R] → move L
[1, 8, 6, 2, 5, 4, 8, 3, 7]
             L     R

L=5 R=6  area = min(4,8) * 1 = 4   maxArea=49  heights[L] <= heights[R] → move L
[1, 8, 6, 2, 5, 4, 8, 3, 7]
                L  R

L=6 R=6 → left == right → loop ends
```
→ return `maxArea = 49`

## 2. Optimal Approach

This **is** the optimal approach — your solution already implements it. The insight:
start with the widest possible container (`left = 0`, `right = n - 1`). At each step,
the container's area is capped by `min(height[left], height[right])`. If you move the
pointer at the *taller* line inward, the width shrinks and the height stays capped by the
same (still shorter) line — the area can never improve. So the only pointer worth moving
is the one at the *shorter* line, since that's the only move with a chance of finding a
taller boundary. Track the best area seen while the pointers converge.

**Time complexity:** O(n) — each of the `n` lines is examined once as either `left` or
`right` moves past it.

**Space complexity:** O(1) — no auxiliary data structures.

```java
public int maxArea(int[] height) {
    int left = 0, right = height.length - 1;
    int maxArea = 0;

    while (left < right) {
        int width = right - left;
        int boundedHeight = Math.min(height[left], height[right]);

        maxArea = Math.max(maxArea, width * boundedHeight);

        if (height[left] <= height[right]) {
            left++;
        } else {
            right--;
        }
    }

    return maxArea;
}
```

**Algorithm trace:** identical to the trace in section 1 — your implementation already
follows this exact sequence of moves.

## 3. Alternative Approaches

### Brute force — check every pair

Try every pair of lines `(i, j)` with `i < j`, compute `min(height[i], height[j]) * (j - i)`
for each, and keep the maximum. Simple to reason about but doesn't scale.

**Time complexity:** O(n^2) — nested loop over all `C(n, 2)` pairs.

**Space complexity:** O(1) — only a running maximum is tracked.

**When acceptable:** Fine for small inputs or as a warm-up/sanity-check solution under
interview time pressure, but would time out well before `n = 10^5` from the constraints.

```java
public int maxArea(int[] height) {
    int maxArea = 0;

    for (int i = 0; i < height.length; i++) {
        for (int j = i + 1; j < height.length; j++) {
            int width = j - i;
            int boundedHeight = Math.min(height[i], height[j]);
            maxArea = Math.max(maxArea, width * boundedHeight);
        }
    }

    return maxArea;
}
```

**Algorithm trace** (step table) on `height = [4,3,2,1,4]` (output `16`):

| i | j | height[i] | height[j] | width | min height | area | maxArea |
|---|---|-----------|-----------|-------|------------|------|---------|
| 0 | 1 | 4 | 3 | 1 | 3 | 3 | 3 |
| 0 | 2 | 4 | 2 | 2 | 2 | 4 | 4 |
| 0 | 3 | 4 | 1 | 3 | 1 | 3 | 4 |
| 0 | 4 | 4 | 4 | 4 | 4 | **16** | **16** |
| 1 | 2 | 3 | 2 | 1 | 2 | 2 | 16 |
| 1 | 3 | 3 | 1 | 2 | 1 | 2 | 16 |
| 1 | 4 | 3 | 4 | 3 | 3 | 9 | 16 |
| 2 | 3 | 2 | 1 | 1 | 1 | 1 | 16 |
| 2 | 4 | 2 | 4 | 2 | 2 | 4 | 16 |
| 3 | 4 | 1 | 4 | 1 | 1 | 1 | 16 |

→ return `maxArea = 16`
