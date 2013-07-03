                                 CS 61B  Lab 9
                                April 2-3, 2013

Goal:  to practice proving asymptotic (big-Oh) results.  The class notes from
Lecture 20 may come in handy; however, we will try to be even more rigorous
here than in lecture.

Recall the definition of big-Oh:

|=============================================================================|
| O(f(n)) is the SET of ALL functions T(n) that satisfy:                      |
|                                                                             |
|   There exist positive constants c and N such that, for all n >= N,         |
|                              T(n) <= c f(n)                                 |
|=============================================================================|

EXAMPLE
-------
Formally prove that n^2 + n + 1  is in  O(n^2).

Solution:
  Let T(n) = n^2 + n + 1.  Let f(n) = n^2.

  Choose c = 3, and N = 1.  Then, we know T(n) is in O(n^2) if we can prove

                               T(n) <= c f(n),
    or equivalently,    n^2 + n + 1 <= 3 n^2,                 for all n >= 1.

  Is this inequality true?  Well, for any n >= 1, we know that 1 <= n <= n^2.
  Hence, all of the following are true:

                                  1 <= n^2
                                  n <= n^2
                                n^2  = n^2

  Adding the left and right sides of these inequalities together, we have

                        n^2 + n + 1 <= 3 n^2, which completes the proof.

Part I:  (1 point)
------------------
Formally prove that 2^n + 1  is in  O(4^n - 16).

HINT:  You may assert without proof that, for all n >=1, 2^n >= 1.
  (You may also assert without proof that 4^n and 2^n are monotonically
  increasing, if you find it useful.)

Part II:  (1 point)
-------------------
Formally prove that if f(n) is in O(g(n)), and g(n) is in O(h(n)), then
  f(n) is in O(h(n)).

NOTE:  The values of c and N used to prove that f(n) is in O(g(n)) are
  not necessarily the same as the values used to prove that g(n) is in O(h(n)).
  Hence, assume that there are positive c', N', c'', and N'' such that

    f(n) <= c' g(n)        for all n >= N', and
    g(n) <= c'' h(n)       for all n >= N''.

Part III:  (2 points)
---------------------
Formally prove that 0.01 n^2 - 1  is NOT in  O(n).

We need to show that, no matter how large we choose c and N, we will never
  obtain the desired inequality.  We cannot prove this by picking a specific
  value of c and N.  Instead, we must study how the two functions behave as
  n approaches infinity.

Let T(n) = 0.01 n^2 - 1, and let f(n) = n.  Prove that

                    c f(n)
            lim     ------ = 0,
        n->infinity  T(n)

  no matter how large we choose c to be.  You will need to scale both the
  numerator and the denominator by a well-chosen multiplier to get the result.

Use this result to show that there are no values c, N such that T(n) <= c f(n)
  for all n >= N.

Postscript
----------
The functions |cos(n)| and |sin(n)| are interesting, because neither is
  dominated by the other.  Can you informally suggest why |cos(n)| is not in
  O(|sin(n)|), and |sin(n)| is not in O(|cos(n)|)?

How would you prove that, for all n >=1, 2^n >= 1?  (Hint:  use calculus.)
