# 3Sum — Solution

| | |
|---|---|
| **Created on** | 2026-06-12 |
| **DSA Category** | Two Pointers |

---

## Approach 1: Sort + Two Pointers (Optimal)

Sort the array. Fix one element `nums[i]` and use two pointers (`left`, `right`) on the
remaining subarray to find pairs that sum to `-nums[i]`. After sorting, moving pointers
inward based on the current sum efficiently covers all pairs. Skip duplicate values at
each pointer position to avoid duplicate triplets in the output.

- **Time:** O(n²) — one outer loop × one linear two-pointer scan per iteration
- **Space:** O(log n) — sorting in-place; output list not counted

```java
public List<List<Integer>> threeSum(int[] nums) {
    List<List<Integer>> result = new ArrayList<>();
    Arrays.sort(nums);

    for (int i = 0; i < nums.length - 2; i++) {
        if (i > 0 && nums[i] == nums[i - 1]) continue;

        int left = i + 1, right = nums.length - 1;

        while (left < right) {
            int sum = nums[i] + nums[left] + nums[right];

            if (sum == 0) {
                result.add(List.of(nums[i], nums[left], nums[right]));
                while (left < right && nums[left] == nums[left + 1]) left++;
                while (left < right && nums[right] == nums[right - 1]) right--;
                left++;
                right--;
            } else if (sum < 0) {
                left++;
            } else {
                right--;
            }
        }
    }

    return result;
}
```

**Algorithm trace** — Input: `nums = [-1, 0, 1, 2, -1, -4]` → after sort: `[-4, -1, -1, 0, 1, 2]`

```
i=0, nums[i]=-4, left=1, right=5:
  [-4, -1, -1,  0,  1,  2]
    i   L                R    sum=-4+(-1)+2=-3 < 0 → left++
  [-4, -1, -1,  0,  1,  2]
    i       L            R    sum=-4+(-1)+2=-3 < 0 → left++
  [-4, -1, -1,  0,  1,  2]
    i           L        R    sum=-4+0+2=-2 < 0 → left++
  [-4, -1, -1,  0,  1,  2]
    i               L    R    sum=-4+1+2=-1 < 0 → left++
  left=right → exit

i=1, nums[i]=-1 (first -1), left=2, right=5:
  [-4, -1, -1,  0,  1,  2]
        i   L            R    sum=-1+(-1)+2=0 ✓ → add [-1,-1,2], left++, right--

  [-4, -1, -1,  0,  1,  2]
        i       L    R        sum=-1+0+1=0 ✓ → add [-1,0,1], left++, right--
  left=right → exit

i=2, nums[i]=-1 == nums[1]=-1 → skip (duplicate)

i=3, nums[i]=0, left=4, right=5:
  [-4, -1, -1,  0,  1,  2]
                i   L    R    sum=0+1+2=3 > 0 → right--
  left=right → exit
```

→ return `[[-1,-1,2], [-1,0,1]]`

---

## Approach 2: Sort + HashSet

Sort the array. Fix one element `nums[i]`, then iterate `j` from `i+1` onward. For each
`nums[j]`, check whether its complement (`-nums[i] - nums[j]`) has already been seen in a
`HashSet`. If yes, a triplet is found; skip subsequent duplicates of `nums[j]` before
continuing.

- **Time:** O(n²) — same asymptotic as two pointers, but with higher constant due to hash operations
- **Space:** O(n) — the `HashSet` holds up to n elements per outer iteration

```java
public List<List<Integer>> threeSum(int[] nums) {
    List<List<Integer>> result = new ArrayList<>();
    Arrays.sort(nums);

    for (int i = 0; i < nums.length - 2; i++) {
        if (i > 0 && nums[i] == nums[i - 1]) continue;

        Set<Integer> seen = new HashSet<>();

        for (int j = i + 1; j < nums.length; j++) {
            int complement = -nums[i] - nums[j];

            if (seen.contains(complement)) {
                result.add(List.of(nums[i], complement, nums[j]));
                while (j + 1 < nums.length && nums[j] == nums[j + 1]) j++;
            }

            seen.add(nums[j]);
        }
    }

    return result;
}
```

**Algorithm trace** — Input (after sort): `[-4, -1, -1, 0, 1, 2]`

i=1, nums[i]=-1, seen={}:

| j | nums[j] | complement = 1-nums[j] | seen contains complement? | action | seen after |
|---|---------|------------------------|--------------------------|--------|------------|
| 2 | -1 | 2 | {} → No | — | {-1} |
| 3 | 0 | 1 | {-1} → No | — | {-1, 0} |
| 4 | 1 | 0 | {-1,0} → **Yes** | add [-1,0,1] | {-1, 0, 1} |
| 5 | 2 | -1 | {-1,0,1} → **Yes** | add [-1,-1,2] | {-1, 0, 1, 2} |

→ result includes `[-1,0,1]` and `[-1,-1,2]`

---

## Approach 3: Brute Force

Check every possible triple with three nested loops. Sort first so duplicates can be
deduplicated by inserting into a `Set<List<Integer>>`.

- **Time:** O(n³) — three nested loops over n elements
- **Space:** O(n) — the result set; acceptable only when n is very small

Acceptable only for tiny inputs or under extreme interview time pressure where coding
speed matters more than performance.

```java
public List<List<Integer>> threeSum(int[] nums) {
    Set<List<Integer>> result = new HashSet<>();
    Arrays.sort(nums);

    for (int i = 0; i < nums.length - 2; i++) {
        for (int j = i + 1; j < nums.length - 1; j++) {
            for (int k = j + 1; k < nums.length; k++) {
                if (nums[i] + nums[j] + nums[k] == 0) {
                    result.add(List.of(nums[i], nums[j], nums[k]));
                }
            }
        }
    }

    return new ArrayList<>(result);
}
```

**Algorithm trace** — Input (after sort): `[-4, -1, -1, 0, 1, 2]`

| i | j | k | sum | add? |
|---|---|---|-----|------|
| 0(-4) | 1(-1) | 2(-1) | -6 | No |
| 0(-4) | 1(-1) | 3(0) | -5 | No |
| 0(-4) | 1(-1) | 4(1) | -4 | No |
| 0(-4) | 1(-1) | 5(2) | -3 | No |
| ... | | | | (all i=0 sums ≤ -1, skip) |
| 1(-1) | 2(-1) | 5(2) | 0 | **Yes** → [-1,-1,2] |
| 1(-1) | 3(0) | 4(1) | 0 | **Yes** → [-1,0,1] |
| ... | | | | |

→ return `[[-1,-1,2], [-1,0,1]]`
