package maxSpacekClusters;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.PriorityQueue;

public class EuclidianKClusters {
	PriorityQueue<Points> pointsHeap;
	UnionFind unionFind;
	int numNodes;
		
	public void eucledianClusters() {
		// Test Parameters
		boolean testMode = false; // Test file or real input
		int k = 4; // Number of clusters
		
		// Work-Horse
		
		importAndHeapifyPoints( testMode );
		unionizeNodes();
		kClusterAlgo( k );
		int maxSpacing = maxSpacing();
		
		System.out.println("The minimal max-spacing is: " + maxSpacing + " for " 
		+ numNodes + " nodes and k-clusters: " + k);
	}
	
	/*
	 * importPoints: Take input text file holding x,y,dist tuplets and use them to heapify
	 * the pointsHeap. Optional boolean for test mode or not.
	 */
	public void importAndHeapifyPoints( boolean test ) {
		pointsHeap = new PriorityQueue< Points >();
		
		try {
			File myObj;
			if( test == true ) {
				myObj = new File("./src/maxSpacekClusters/part1_test.txt");
			} else {
				myObj = new File("./src/maxSpacekClusters/part1_input.txt");
			}
			Scanner myReader = new Scanner( myObj );
		
			String header = myReader.nextLine(); // "500"
			
			numNodes = Integer.parseInt( header );
			
			while ( myReader.hasNextLine() ) {
				String data = myReader.nextLine(); // "1 2 6807"
				String[] lineSplit = data.split("\s"); // "1" "2" "6807"
				
				int x			= Integer.parseInt( lineSplit[0] );
				int y 			= Integer.parseInt( lineSplit[1] );
				int distance 	= Integer.parseInt( lineSplit[2] );
				pointsHeap.add( new Points( x, y, distance ) );
				
		    }
		    myReader.close();
		} catch (FileNotFoundException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		}
	}
	
	/*
	 * unionizeNodes: Places everything in the union as their own set (disjoint-set): 
	 * [a b c d e ...]. Later union(a,c) will join ac together: [ac b d e ... ]
	 */
	public void unionizeNodes() {
		unionFind = new UnionFind( numNodes );
	}
	
	/*
	 * kClusterAlgo: Shrinks the union size by taking the min distance point from the 
	 * heap. It then tries to unionize the points. If they are unionized then two clusters
	 * were just formed, if not then it was an internal edge of a cluster being removed
	 * from the heap. This fulfills the greedy criteria as only "separated" (not in same group)
	 * points are only joined based on minimal global distance (by definition).
	 */
	public void kClusterAlgo( int k ) {
		final int unionNodeOffset = 1;
		int unionSize = unionFind.size();
		
		while( unionSize > k ) {
			Points closestPair = pointsHeap.poll();
			
			unionFind.Union( 
					closestPair.getX() - unionNodeOffset, 
					closestPair.getY() - unionNodeOffset 
					);
			
			unionSize = unionFind.size();
		}
	}
	
	/*
	 * maxSpacing: 
	 * Trivial case: the maximal distance is the next edge in the heap. 
	 * Non-trivial:  the next edge is an internal edge to one of the clusters. In that
	 *  			 case we will exhaust all internal edges from all the clusters, as
	 * 				 soon as we shrink the size of the union, we know we just made a new
	 * 				 new cluster (k-1). Therefore this is the edge that determines the 
	 * 				 maximal spacing: minimal separation points merged (by greedy criteria!)
	 */
	public int maxSpacing() {
		final int unionNodeOffset = 1;
		int unionOrignalSize = unionFind.size();
		int unionSize = unionFind.size();
		
		Points maximalSpacing = null;
		while( unionOrignalSize == unionSize ) {
			maximalSpacing = pointsHeap.poll();
			
			unionFind.Union( 
					maximalSpacing.getX() - unionNodeOffset, 
					maximalSpacing.getY() - unionNodeOffset 
					);
			
			unionSize = unionFind.size();
		}
		int S = maximalSpacing.getDistance();
		return S;
	}
	
}
