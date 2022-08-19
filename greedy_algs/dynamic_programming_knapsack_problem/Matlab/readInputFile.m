function [n, W, weights, values] = readInputFile( fullFileName )
  fileID = fopen( fullFileName,'r' );
  fileArray = fscanf( fileID, "%d %d", [2 Inf] );
  fclose( fileID );
  
  n = fileArray(2,1);
  W = fileArray(1,1);
  values = fileArray(1,2:end);
  weights = fileArray(2,2:end);
end