clear all
clc

INF = 10000;
n = 6;
A = zeros(n,n,n+1);
A(:, :, 1) = [ 0 -1 -1 -1 -1 -1; 
               -1 0 -1 -1 -1 -1; 
               -1 -1 0 -1 -1 -1; 
               -1 -1 -1 0 -1 -1; 
               -1 -1 -1 -1 0 -1;
               -1 -1 -1 -1 -1 0];

for( k = 2:n+1 )
  for( i = 1:n )
    for( j = 1:n )
      
      A(i, j, k) = min( A(i, j, k-1), A(i, k-1, k-1) + A( k-1, j, k-1));
    
    endfor
  endfor
endfor

disp( ["Items Aquired: " ] )
A(:,:, n+1)