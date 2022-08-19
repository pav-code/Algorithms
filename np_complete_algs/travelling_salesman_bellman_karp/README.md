# TravellingSalesman

A Dynamic Programming Algorithm for the Travelling Salesman Problem (TSP)

The cities are given in 2D lattitude/longitude (2D cartesian - coordinates)

TSP is NP-Hard and so, in the current state of things, doesn't admit to
polynomial time solvability. The algorithm is an exact solution 
and an optimization over brute-force search, brute force being ~ O(n^n)
this algorithm gets a runtime of O(n^2 * 2^n). The optimization comes 
from ignoring the order we visit cities. 

1) Load cities
2) Generate all possible city sets combinations of cardinality 1 - numOfCities
   e.g. {1,2,3}, {1,2,4}, {1,2,5}...
3) Setup a hash table to map the sets to the dynamic programming array (where 
the results are built up)
4) Runs the initial conditions, the core of the algorithm and finally accumulates
the final hop to return the minimal cost TSP tour.

 - Bellman–Held–Karp algorithm '62
 - No vectorized/SIMD optimization
 - Minimal benchmarking
