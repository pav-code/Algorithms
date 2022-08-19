package Kosaraju2p;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;


import java.util.*;

public class Main{
	
	public int[] [] listG;
	public int[] [] listGrev;
	public ArrayList<ArrayList<Integer>> graphG_adjList;
	public ArrayList<ArrayList<Integer>> graphGrev_adjList;
	public int[] explored;
	public int[] leader;
	public int[] leaderCount;
	public int[] finishingTime;
	public int[] ansArray;
	int t, s;
	
	static final boolean TEST_GRAPH = false;
	
	public void importGraph() {
		String filename;
		int inputSize;
		
		if( TEST_GRAPH ) {
			filename = "./src/Kosaraju2p/TestInput.txt";
			inputSize = 10 + 1;
		} else {
			filename = "./src/Kosaraju2p/SCC.txt";
			inputSize = 5105042 + 1;
		}
		
		this.listG = new int [inputSize] [2];
		this.listGrev = new int [inputSize] [2];
		
		try {
			File myObj = new File(filename);
			Scanner myReader = new Scanner(myObj);
		
			int nodeIdx = 0;
			while (myReader.hasNextLine()) {
				String data = myReader.nextLine();
			
				int writeIndex = 0;
				for (String node: data.split(" ")) {
					Integer nodeInt = Integer.parseInt(node);
					listG[nodeIdx][writeIndex] = nodeInt;
					listGrev[nodeIdx][writeIndex ^ 1] = nodeInt;
					writeIndex++;
				}
				nodeIdx++;
		    }
		    myReader.close();
		    
		} catch (FileNotFoundException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		}
	}
	
	public void getAdjLists() {
	    graphG_adjList = createAdjList(listG);
	    graphGrev_adjList = createAdjList(listGrev);
	}
	
	public ArrayList<ArrayList<Integer>> createAdjList (int [] [] edgeList){
		ArrayList<ArrayList<Integer>> adjList = new ArrayList<ArrayList<Integer>>();
				
		Sort2DArrayBasedOnColumnNumber(edgeList,1);
		
		int currentALentry = 0;
		int maxNode = 0;
		
		for (int i = 0; i<edgeList.length; i++) {
			
			if(maxNode<edgeList[i][1]) {
				maxNode = edgeList[i][1];
			}
			
			if( edgeList[i][0] - currentALentry > 1  ) {
				while( edgeList[i][0] - currentALentry > 1 ) {
					adjList.add( new ArrayList<Integer>() );
					
					adjList.get(currentALentry).add( currentALentry + 1 );
					adjList.get(currentALentry).add( currentALentry + 1 );
					
					currentALentry++;
				}
			}
			
			if(edgeList[i][0] != currentALentry) {
				adjList.add( new ArrayList<Integer>() );
				adjList.get(currentALentry).add(edgeList[i][0]);			
				currentALentry = edgeList[i][0];
			}
			adjList.get(currentALentry-1).add(edgeList[i][1]);
		}
		
		if(maxNode > adjList.size()) {
			while(maxNode > adjList.size()) {
				adjList.add( new ArrayList<Integer>() );
				adjList.get(currentALentry).add(currentALentry);
				adjList.get(currentALentry).add(currentALentry);
				currentALentry++;
			}
		}
		//System.out.println("Max Node is; " + maxNode);
		return adjList;
	}
	
    public static  void Sort2DArrayBasedOnColumnNumber (int[][] array, final int columnNumber){
        Arrays.sort(array, new Comparator<int[]>() {
            @Override
            public int compare(int[] first, int[] second) {
               if(first[columnNumber-1] > second[columnNumber-1]) {
            	   return 1;
               } 
               else if(first[columnNumber-1] < second[columnNumber-1]) {
            	   return -1;
               }
               else {
            	   return 0;
               }
            }
        });
    }
	
    public int[] kojuraju () {
    	System.out.println("Start calc finish times.");
    	DFS_Loop(graphGrev_adjList);
    	
		injectFinishingTimeInlistG();
		
		System.out.println("Start calc leader nodes.");
		getAdjLists();
		
		DFS_Loop(graphG_adjList);
		
		return leader;
    }
    
	public void DFS_Loop( ArrayList<ArrayList<Integer>> G ) {
		int N = G.size();
		t = 0;
		s = 0; // leader node
		explored = new int [N + 1];
		leader = new int [N + 1];
		finishingTime = new int [N + 1];
				
		for (int i=N; i>=1; i--) {
			if(explored[i] == 0) {
				s = i;
				DFS(G, i);
			}
		}
		
		
	}
	
	public void DFS(ArrayList<ArrayList<Integer>> G,int i) {
		explored[i] = i;
		leader[i]= s;
		
		ArrayList<Integer> arcs = G.get(i-1);
		for( int arcIndex = 1; arcIndex < arcs.size(); arcIndex++ ) {
			int j = arcs.get(arcIndex);
			if(explored[j] == 0) {
				DFS(G, j);
			}
		}
		t++;
		finishingTime[i] = t;
	}
	
	public void injectFinishingTimeInlistG() {
		for (int i=0; i<listG.length; i++) {
			for (int j=0; j<2; j++) {
				//graphG_adjList.get(i).set(j, finishingTime[ graphG_adjList.get(i).get(j) ] );
				listG[i][j] = finishingTime[listG[i][j]];
			
			}
		}
		
		
	}

	public void leaderCount() {
		leaderCount = new int [ansArray.length];
		
		for (int count=0; count<ansArray.length; count++) {
			leaderCount[ansArray[count]]++;
		}
		Arrays.sort(leaderCount);
	}
	
	public void debug() {
//		int arrCount[] = new int [listG.length];
//		
//		for (int count=0; count<arrCount.length; count++) {
//			arrCount[listG[count][0]]++;
//		}
		
		System.out.println(" the size o fgraphGrev_adjList graph " + this.graphGrev_adjList.size() );
		
	    try {
	        FileWriter myWriter = new FileWriter("filename.txt");
	        
	        for (int i = 0; i < graphGrev_adjList.size(); i++) {
	            for (int j = 0; j < graphGrev_adjList.get(i).size(); j++) {
	                myWriter.write( graphGrev_adjList.get(i).get(j) + " ");
	            }
	            myWriter.write("\n");
	        }
	        
	        myWriter.close();
	        System.out.println("Successfully wrote to the file.");
	    } catch (IOException e) {
	        System.out.println("An error occurred.");
	        e.printStackTrace();
	    }
		
	}
	
	public static void main(String[] args)
    {		
		Main obj = new Main();
		System.out.println("Start import. Will need a large stack if TEST_GRAPH is false: " + TEST_GRAPH);
		obj.importGraph();
		System.out.println("Done import.");
		obj.getAdjLists();
		System.out.println("Done adj list creation.");
		obj.debug();
		obj.ansArray = obj.kojuraju();
		System.out.println("Done Kojuraju's 2 Passes.");
		obj.leaderCount();
		System.out.println("\nTop Strongly Connected Component Nodes and each of their member count. ");
		
		for( int arcIndex = obj.leaderCount.length -1; arcIndex > obj.leaderCount.length -6 ; arcIndex-- ) {
				System.out.print("\tNode " + arcIndex + " " + obj.leaderCount[arcIndex] + "\n");
		}
    }

}

//for( int arcIndex = 0; arcIndex < obj.finishingTime.length; arcIndex++ ) {
//System.out.print(obj.finishingTime[arcIndex]);
//}
//for( int arcIndex = 0; arcIndex < obj.ansArray.length; arcIndex++ ) {
//	System.out.print("Node " + arcIndex + " " + obj.ansArray[arcIndex] + "\n");
//}
