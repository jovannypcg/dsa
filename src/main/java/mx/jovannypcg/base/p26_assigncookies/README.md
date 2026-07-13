# Assign Cookies

**Date added:** 2026-07-12

## Problem Description

Assume you are an awesome parent and want to give your children some cookies. But, you should give each child at most one cookie. Each child `i` has a greed factor `g[i]`, which is the minimum size of a cookie that the child will be content with; and each cookie `j` has a size `s[j]`. If `s[j] >= g[i]`, we can assign the cookie `j` to the child `i`, and the child `i` will be content. Your goal is to maximize the number of your content children and output the maximum number.

**Source:** https://leetcode.com/problems/assign-cookies

## Examples

**Example 1**
```
Input: g = [1,2,3], s = [1,1]
Output: 1
Explanation: You have 3 children and 2 cookies. The greed factors of 3 children are 1, 2, 3. And even though you have 2 cookies, since their size is both 1, you could only make the child whose greed factor is 1 content. You need to output 1.
```

**Example 2**
```
Input: g = [1,2], s = [1,2,3]
Output: 2
Explanation: You have 2 children and 3 cookies. The greed factors of 2 children are 1, 2. You have 3 cookies and their sizes are big enough to gratify all of the children. You need to output 2.
```

**Example 3**
```
Input: g = [1,2,3], s = []
Output: 0
Explanation: There are no cookies available, so no child can be content.
```

**Example 4**
```
Input: g = [10], s = [1,2,3,9]
Output: 0
Explanation: The single child has a greed factor of 10, but the largest cookie available has size 9, so no cookie can satisfy the child.
```

**Example 5**
```
Input: g = [1,1,1], s = [1,1,1]
Output: 3
Explanation: Every child has a greed factor of 1, and every cookie has size 1, so each child can be matched to a distinct cookie.
```

## Constraints

- `1 <= g.length <= 3 * 10^4`
- `0 <= s.length <= 3 * 10^4`
- `1 <= g[i], s[j] <= 2^31 - 1`

## Hints

1. Think about which child you should try to satisfy first: one who is easy to please or one who is hard to please?
2. Sorting both arrays might reveal a natural way to match children to cookies.
3. Once sorted, consider walking through the cookies from smallest to largest, and only advance to the next child once the current one is satisfied.
4. If the smallest available cookie can't satisfy the least greedy remaining child, could it possibly satisfy anyone more greedy?
5. Use two pointers, one for children and one for cookies, and greedily assign the smallest cookie that can satisfy the current child.
