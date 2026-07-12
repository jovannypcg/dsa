| | |
|---|---|
| **Solved on** | 2026-07-11 |
| **DSA Category** | Two Pointers |

## 1. Your Solution Assessment

### Correctness

The solution is correct. It handles all constraint boundaries: single-element arrays, all-negative arrays, all-positive arrays, arrays containing zero, and repeated absolute values on both sides of zero. The tie-breaking rule (using the `else` branch when `|nums[left]| <= |nums[right]|`) doesn't matter for correctness — when the absolute values are equal, both pointers square to the same value, so it doesn't matter which one is written first.

### Code Quality

Clear and idiomatic. `left`/`right`/`out`/`outIdx` are self-explanatory names, and the inline comment on `outIdx` clarifies the non-obvious detail (filling from the back). The blank lines separating the pointer move from the `outIdx--` decrement keep the loop body readable.

### Time Complexity

**O(n)** — each element is visited exactly once across the combined movement of `left` and `right`; the loop runs `n` times total.

### Space Complexity

**O(n)** for the output array, which is required by the problem (excluding the output, the algorithm uses O(1) extra space — just the two pointers and `outIdx`).

### Algorithm Trace

Input: `nums = [-4, -1, 0, 3, 10]`

Each step first shows `nums` with `L`/`R` at the positions being compared (before either pointer moves), then the decision, then `out` after the write (with `O` marking the next slot to fill).

```
Step 1: comparing L=0, R=4
[-4, -1, 0, 3, 10]
 L             R
|nums[L]|=|-4|=4 vs |nums[R]|=|10|=10 → R wins (10 > 4); write nums[R]^2=100 to out[4]; R moves left
out: [_, _, _, _, 100]
               O

Step 2: comparing L=0, R=3
[-4, -1, 0, 3, 10]
 L          R
|nums[L]|=|-4|=4 vs |nums[R]|=|3|=3 → L wins (4 > 3); write nums[L]^2=16 to out[3]; L moves right
out: [_, _, _, 16, 100]
            O

Step 3: comparing L=1, R=3
[-4, -1, 0, 3, 10]
     L      R
|nums[L]|=|-1|=1 vs |nums[R]|=|3|=3 → R wins (3 > 1); write nums[R]^2=9 to out[2]; R moves left
out: [_, _, 9, 16, 100]
         O

Step 4: comparing L=1, R=2
[-4, -1, 0, 3, 10]
     L   R
|nums[L]|=|-1|=1 vs |nums[R]|=|0|=0 → L wins (1 > 0); write nums[L]^2=1 to out[1]; L moves right
out: [_, 1, 9, 16, 100]
      O

Step 5: comparing L=2, R=2
[-4, -1, 0, 3, 10]
         L
         R
|nums[L]|=|0|=0 vs |nums[R]|=|0|=0 → tie, else branch → R wins (0 == 0); write nums[R]^2=0 to out[0]; R moves left
out: [0, 1, 9, 16, 100]
```
→ loop ends (`L=2 > R=1`); return `[0, 1, 9, 16, 100]`

## 2. Optimal Approach

This **is** the optimal approach: two pointers starting at both ends of `nums`, comparing absolute values and filling the result array from the back.

**Why it works:** the largest squared value in a sorted array must come from one of the two ends — either the most negative number or the most positive number (whichever has the larger magnitude). By comparing `|nums[left]|` and `|nums[right]|` and writing the larger square to the back of the output, and repeating, the output is built in sorted order without needing a separate sort step.

### Time Complexity

**O(n)** — a single pass, each element compared and placed exactly once.

### Space Complexity

**O(n)** for the output array (unavoidable, since the problem requires returning a new array); **O(1)** additional space.

### Java Code

```java
public int[] sortedSquares(int[] nums) {
    int n = nums.length;
    int left = 0;
    int right = n - 1;
    int[] out = new int[n];
    int outIdx = n - 1;

    while (left <= right) {
        int leftSq = nums[left] * nums[left];
        int rightSq = nums[right] * nums[right];

        if (leftSq > rightSq) {
            out[outIdx] = leftSq;
            left++;
        } else {
            out[outIdx] = rightSq;
            right--;
        }

        outIdx--;
    }

    return out;
}
```

### Algorithm Trace

Same as above (this is the user's own solution).

## 3. Alternative Approaches

### A. Brute Force: Square and Sort

Square every element into a new array, then sort it with a general-purpose sort.

- **Time:** **O(n log n)** — dominated by the sort.
- **Space:** **O(n)** for the new array (plus whatever the sort algorithm uses internally, e.g. O(log n) for `Arrays.sort`'s dual-pivot quicksort on primitives).
- **When acceptable:** fine under interview time pressure as a first correct pass, or when `n` is small enough that the constant-factor simplicity outweighs the asymptotic cost. This is literally the approach the problem statement calls "trivial" before asking for the O(n) follow-up.

```java
public int[] sortedSquares(int[] nums) {
    int[] out = new int[nums.length];

    for (int i = 0; i < nums.length; i++) {
        out[i] = nums[i] * nums[i];
    }

    Arrays.sort(out);
    return out;
}
```

**Trace** (`nums = [-4, -1, 0, 3, 10]`):

```
Square each element:
[-4, -1, 0, 3, 10]
  ↓   ↓  ↓  ↓   ↓
[16,  1, 0, 9, 100]

Sort:
[16, 1, 0, 9, 100] → [0, 1, 9, 16, 100]
```
→ return `[0, 1, 9, 16, 100]`

### B. Find the Pivot, Then Merge Two Sorted Halves

Binary search for the first non-negative index (the "pivot"). This splits `nums` into two already-sorted-by-magnitude sequences: the negative prefix (read right-to-left, magnitudes increase) and the non-negative suffix (read left-to-right, magnitudes increase). Merge these two sequences by comparing magnitudes, same as merging two sorted arrays.

- **Time:** **O(n)** — O(log n) for the binary search plus O(n) for the merge.
- **Space:** **O(n)** for the output array; **O(1)** additional space.
- **When acceptable:** functionally equivalent to the two-ends approach above; worth mentioning in an interview to show you see the "two sorted sequences" structure explicitly, but it's more code for the same complexity — the two-pointer-from-the-ends version is preferred in practice.

```java
public int[] sortedSquares(int[] nums) {
    int n = nums.length;
    int lo = 0;
    int hi = n;

    while (lo < hi) {
        int mid = lo + (hi - lo) / 2;
        if (nums[mid] < 0) {
            lo = mid + 1;
        } else {
            hi = mid;
        }
    }

    int neg = lo - 1;
    int pos = lo;
    int[] out = new int[n];
    int outIdx = 0;

    while (neg >= 0 && pos < n) {
        int negSq = nums[neg] * nums[neg];
        int posSq = nums[pos] * nums[pos];

        if (negSq < posSq) {
            out[outIdx++] = negSq;
            neg--;
        } else {
            out[outIdx++] = posSq;
            pos++;
        }
    }

    while (neg >= 0) {
        out[outIdx++] = nums[neg] * nums[neg];
        neg--;
    }

    while (pos < n) {
        out[outIdx++] = nums[pos] * nums[pos];
        pos++;
    }

    return out;
}
```

**Trace** (`nums = [-4, -1, 0, 3, 10]`):

```
Binary search for pivot (first index with nums[i] >= 0):
lo=0, hi=5, mid=2 → nums[2]=0 >= 0 → hi=2
lo=0, hi=2, mid=1 → nums[1]=-1 < 0 → lo=2
lo=2, hi=2 → stop; pivot=2 (nums[2]=0)

neg=1 (points into negative prefix, at nums[1]=-1), pos=2 (points into non-negative suffix, at nums[2]=0)

[-4, -1, 0, 3, 10]
     neg pos
|-1|^2=1 vs |0|^2=0 → pos wins (0 < 1); write 0; pos moves right
out: [0]

[-4, -1, 0, 3, 10]
     neg    pos
|-1|^2=1 vs |3|^2=9 → neg wins (1 < 9); write 1; neg moves left
out: [0, 1]

[-4, -1, 0, 3, 10]
 neg       pos
|-4|^2=16 vs |3|^2=9 → pos wins (9 < 16); write 9; pos moves right
out: [0, 1, 9]

[-4, -1, 0, 3, 10]
 neg           pos
neg exhausted after this? No — neg=0 still valid. |-4|^2=16 vs |10|^2=100 → neg wins (16 < 100); write 16; neg moves left
out: [0, 1, 9, 16]

neg=-1 (exhausted); drain remaining suffix: write |10|^2=100
out: [0, 1, 9, 16, 100]
```
→ return `[0, 1, 9, 16, 100]`
