clear all

n = 7;

A = zeros(n,n);

p = [0.05 0.4 0.08 0.04 0.1 0.1 0.23];
p = [0.2 0.05 0.17 0.1 0.2 0.03 0.25];

for S = 0:n-1
  for i = 1:n
    
    root = 100000;
    if( i+S > n )
       break
    end
    j = min( i+S, n);
    
    for r = i:j
      
      r = min( r, n );  
      
      if( i > r - 1 && r+1 > j )
        temp = sum( p(i:j) ) + 0 + 0;
      elseif( r == 1 || i > r ) 
        temp = sum( p(i:j) ) + 0 + A(r+1, j);
      elseif( r == j || r+1 > j )
        temp = sum( p(i:j) ) + A(i, r-1) + 0;
      else
        temp = sum( p(i:j) ) + A(i, r-1) + A(r+1, j);
      end
      
      if( temp < root )
        root = temp;
      end
     
    end
    j = min( i+S, n);
    
    A(i, j) = root;
    root = 100000;
  end 
end

A