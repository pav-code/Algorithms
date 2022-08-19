package maxSpacekClusters;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

public class HammingKClusters {
	//ArrayList< Integer > nodeCodes;
	HashMap <Integer, Nodes> nodeCodes;
	UnionFind unionFind;
	int numNodes;
	int numBits;
	int bitCodeDuplicates;
	final int unionOffset = 1;
	
	/*
	 * 
	 */
	public void hammingClusters() {
		// Test Parameters
		boolean testMode = false; // Test file or real input
		
		// Work-Horse
		importHammingPoints( testMode );
		unionizeNodes();
		findAllClosestNeighbourhoods();
		int k = findMinClusterSize();
		
		System.out.println("The minimum number of clusters (k), all under invariant that the minimum Hamming \n" +
		"distance between all nodes of the " + numBits + "-bit location is of\n size >= 3 (at least any 2-bit difference)" + 
		" over the total number of nodes: " + numNodes + " is: \nsize of k is: " + k);
		System.out.println("The runtime of the algorithm is: Loop over all nodes n, 1 digit permutation C(bits, 1),\n2 digit"
				+ " permutation C(bits,2) - C(bits,1), each with a hash table lookup O(1). \n RunTime = O( C(bits,2) * n )\n"
				+ " Brute-force is about O(n^2). On a 200 000 node graph we got 4x10^10 vs 5x10^7 !");
	}
	
	public int findMinClusterSize() {
		// Explicitly removing duplicates. Note the unionFind's inital size is of the number of nodes.
		// But there are duplicates: nodes with the same distance vector. These nodes don't make it
		// into the nodeCodes hashMap - bitwise distances are used as keys (no duplicates in map). 
		// Therefore duplicates nodes that exist in unionFind but not hashMap are deleted. 
		int minNumberOfClusters = unionFind.size() - bitCodeDuplicates;
		return minNumberOfClusters;
	}
	
	public void findAllClosestNeighbourhoods() {
		for ( Integer bitCodeA : nodeCodes.keySet() ) {
			
			Nodes nodeA = nodeCodes.get(bitCodeA);
			ArrayList<Integer> oneDigitPermutation = lookupSingleDigitDifference( nodeA.getBitCode() );
			ArrayList<Integer> twoDigitPermutation = lookupDoubleDigitDifference( nodeA.getBitCode() );
			
			unionClosestPairs( nodeA, oneDigitPermutation );
			unionClosestPairs( nodeA, twoDigitPermutation );
	
		}
	}
	
	public void unionClosestPairs( Nodes nodeA, ArrayList<Integer> possibleClosestPairs ) {
		
		for (int lookupIdx = 0; lookupIdx < possibleClosestPairs.size(); lookupIdx++) { 		      
	    	int bitCodeB = possibleClosestPairs.get(lookupIdx);
	    	
	    	if( nodeCodes.containsKey( bitCodeB ) ) {
	    		Nodes nodeB = nodeCodes.get( bitCodeB );
	    		unionFind.Union( 
						nodeA.getNode() - unionOffset, 
						nodeB.getNode() - unionOffset
						);
	    	}
	    }
	}

	public ArrayList<Integer> lookupSingleDigitDifference( int x ) {
		ArrayList<Integer> permutations = new ArrayList<Integer>();		
		
		for( int bit = 0; bit < numBits; bit++) {
			int permutatedNumber = flipBit( x, bit );
			permutations.add(permutatedNumber);
		}
		
		return permutations;	
	}
	
	public ArrayList<Integer> lookupDoubleDigitDifference( int x ) {
		ArrayList<Integer> permutations = new ArrayList<Integer>();		
		
		for( int bit1 = 0; bit1 < numBits; bit1++ ) {
			for( int bit2 = bit1 + 1; bit2 < numBits; bit2++ ) {
				int permutatedNumber = flipBit( x, bit1 );
				permutatedNumber = flipBit( permutatedNumber, bit2 );
				
				permutations.add(permutatedNumber);
			}
		}
		
		return permutations;
	}
	
	public void importHammingPoints( boolean test ) {
		nodeCodes = new HashMap <Integer, Nodes>();;
		
		try {
			File myObj;
			if( test == true ) {
				myObj = new File("./src/maxSpacekClusters/part2_test.txt");
			} else {
				myObj = new File("./src/maxSpacekClusters/part2_input.txt");
			}
			Scanner myReader = new Scanner( myObj );
		
			String line = myReader.nextLine(); // "500 24"
			String[] header = line.split("\s");
			
			numNodes = Integer.parseInt( header[0] );
			numBits  = Integer.parseInt( header[1] );
			
			int nodeNumber = 1;
			bitCodeDuplicates = 0;
			while ( myReader.hasNextLine() ) {
				String data = myReader.nextLine(); // "0 1 1 0 1 1 1 1 0 ... 1 : numBits"
				data = data.replaceAll("[\s]+", "");
				int nodeHammingCode = stringToBinary( data );
				
				if( nodeCodes.containsKey( nodeHammingCode ) ) {
					bitCodeDuplicates++;
				}
				
				nodeCodes.put( nodeHammingCode, new Nodes( nodeNumber, nodeHammingCode ) );
				nodeNumber++;
		    }
		    myReader.close();
		} catch (FileNotFoundException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		}
	}
	
	public void unionizeNodes() {
		unionFind = new UnionFind( numNodes );
	}
	
	public static int stringToBinary( String str ) {
	    int numBits = str.length(); 
		int intRepresentation = 0;
	    
	    for(int digitIndex = 0; digitIndex < str.length(); digitIndex++) {
	    	
	    	int bit = Integer.parseInt( String.valueOf( str.charAt( digitIndex ) ) );
	    	int power = ( numBits - 1 ) - digitIndex; 
	    	intRepresentation += bit * Math.pow(2, power);
	    }
	    return intRepresentation;
	}

	public static int intToBitmap( int x ) {
		int bitMap = 0;
		bitMap = 1 << x;
		return bitMap;
	}

	public static int flipBit( int x, int bit ) {
		int permutatedNumber = 0;
		
		int bitMask = intToBitmap( bit );
		boolean bitValue = ( x & bitMask ) > 0 ? true : false;
		
		int rmDigit = x & ~bitMask;
		if ( bitValue ) {
			permutatedNumber = rmDigit & ~bitMask;
		} else {
			permutatedNumber = rmDigit | bitMask;
		}
		
		return permutatedNumber;
	}
	
	public static int hammingDist( int x, int y ) {
		int bitDiff = x ^ y;
		int hammingDistance = Integer.bitCount(bitDiff);
		return hammingDistance;
	}
	
}

//public void smallestHammingDistNaive() {
//int minHammingDistance = 100000;
//int firstNode = -1;
//int secondNode = -1;
//for (int i = 0; i < numNodes; i++) {
//	for (int j = i + 1; j < numNodes; j++) {
//		int a = nodeCodes.get(i);
//		int b = nodeCodes.get(j);
//		int c = a ^ b;
//		int HammingDistance = Integer.bitCount(c);
//		if (minHammingDistance > HammingDistance) {
//			minHammingDistance = HammingDistance;
//			firstNode = i + 2;
//			secondNode = j + 2;
//		}
//	}
//}
//System.out.println("Min HammingDIst: " + minHammingDistance + " line A: " + firstNode + " line B: " + secondNode);
//}

//public void xorAll() {
//int xor = nodeCodes.contains(0);
//
//for (int i = 1; i < numNodes; i++) {
//	xor = xor ^ nodeCodes.contains(i);
//	
//}
//System.out.println("Min xor: " + Integer.bitCount(xor) );
//}