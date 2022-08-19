clear all
clc

# Input File Name
fileName = "Input.txt"

# Knapsack and Items
W = 12;

values =  [10 10 10 1]; # [3 2 4 4 8 6 4 7 3 1]
weights = [4 4 4 2]; # [4 3 2 3 3 1 4 7 3 1]

[n, W, weights, values] = readInputFile( fileName );



n = size( values, 2 );

A = zeros(n+1, W+1);

for i = 2:n+1
  for x = 1:W+1
    
    if( i - 1 < 1 )
      A1 = 0;
    else
      A1 = A(i-1, x);
    endif
    
    if( x - weights(i-1) < 1 )
      A(i,x) = A1;
    else 
      A(i,x) = max( A1, A(i-1, x-weights(i-1) ) + values(i-1) );
    endif
    
  endfor
endfor

disp( ["Sum of total value: ", num2str( A(n+1,W+1) ) ] )

items = [];
x = W+1;
for i = n+1:-1:2
  A1 = A(i-1,x);
  
  if( x - weights(i-1) < 1 )
    A2 = 0;
  else
    A2 = A(i-1, x-weights(i-1) ) + values(i-1);
  endif
  
  if( A1 < A2 )
    items = [items i-1 ];
    x = x-weights(i-1);
  endif

endfor

disp( ["Items Aquired: ", num2str(items) ] )
disp( ["Items' Total Weight: ", num2str(sum(weights(items))) ] ) 

# Part of two knapsack problem, running the algorithm consequtively:
# values( [3   1] ) = [];
# weights( [3   1] ) = [];