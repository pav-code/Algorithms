// Java code to demonstrate the concept of
// array of ArrayList
package KargerMinCut;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
	final int arrayOffset = 1;
	final int nodeOffset = 1;
	private ArrayList<ArrayList<Integer> > aList;
	ArrayList<Integer> nodeArray;
	public static boolean DEBUG_MODE = java.lang.management.ManagementFactory.getRuntimeMXBean().
		    getInputArguments().toString().indexOf("-agentlib:jdwp") > 0;
    public static boolean TEST_GRAPH = false;
	
	public void mergeNodes(int nodeA, int nodeB) {
		this.removeDuplicateEdges(nodeA, nodeB);
		this.removeDuplicateEdges(nodeB, nodeA);
		if(nodeA<nodeB) {
			this.aList.get(nodeA-arrayOffset).addAll(
					this.aList.get(nodeB-arrayOffset)
						.subList(nodeOffset, this.aList.get(nodeB-arrayOffset).size() ));
			this.removeNode(nodeB);
			this.replaceEntries(nodeB, nodeA);
		}
		else {
			this.aList.get(nodeB-arrayOffset).addAll(
					this.aList.get(nodeA-arrayOffset)
						.subList(nodeOffset, this.aList.get(nodeA-arrayOffset).size() ));
			this.removeNode(nodeA);
			this.replaceEntries(nodeA, nodeB);
		}
	}
	
	public void removeDuplicateEdges(int nodeNumber, int edgeNumber) {
		ArrayList<Integer> removeList = new ArrayList<Integer>();
		removeList.add(edgeNumber);
	     
	    this.aList.get(nodeNumber-arrayOffset).removeAll(removeList);
	}
	
	public void removeNode(int nodeNumber){
		this.aList.get(nodeNumber-arrayOffset).clear();
		Integer deleteInteger = nodeNumber;
		this.nodeArray.remove(deleteInteger);
	}
	
	public void replaceEntries(int eliminationNode, int replacementNode) {//3,1

		for (int i = 0; i < this.aList.size(); i++) {
            for (int j = 0; j < this.aList.get(i).size(); j++) {
               if(this.aList.get(i).get(j) == eliminationNode) {
            	   this.aList.get(i).set(j, replacementNode);
               }
            }
		}
	}
	
	public static int getRandom( List<Integer> array ) {
	    int rnd = new Random().nextInt(array .size());
	    return array .get(rnd);
	}
	
	public void importGraph(boolean debugMode) {

		this.aList = new ArrayList<ArrayList<Integer>>();
		this.nodeArray = new ArrayList<Integer>();
		
		try {
			File myObj;
			if( debugMode ) {
				myObj = new File("./src/KargerMinCut/nodeGraphSimple.txt");
			} else {
				myObj = new File("./src/KargerMinCut/nodeGraph.txt");
			}
			 
			Scanner myReader = new Scanner(myObj);
		
			int nodeIdx = 0;
			while (myReader.hasNextLine()) {
				String data = myReader.nextLine();
			
				this.aList.add( new ArrayList<Integer>() );
				for (String node: data.split("\t")) {
					Integer nodeInt = Integer.parseInt(node);
					this.aList.get(nodeIdx).add( nodeInt );
				}
				nodeIdx++;
				this.nodeArray.add(nodeIdx);
		    }
		    myReader.close();
		} catch (FileNotFoundException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		}
	}
	
	public int getMinCut(int numIterations) { 
        int minCut = 9999;
        for( int iter = 0; iter < numIterations; iter++) {
        	this.importGraph(TEST_GRAPH);
	        while( this.nodeArray.size() > 2 ) {
				int startNode = getRandom(this.nodeArray);
				//System.out.println("random node: "+ startNode);
				
				int endNode = getRandom(this.aList.get(startNode-1).subList(1, this.aList.get(startNode-1).size()) );
				//System.out.println("endNode node: "+ endNode);
				this.mergeNodes(startNode, endNode);
	        }
	        int cut = this.aList.get(this.nodeArray.get(0)-1).size()-1;
		    if( cut < minCut ) {
		    	minCut = cut;
		    }
		    System.out.println("Iteration #: " + (iter + 1) + ". Min Cut Found: " + minCut);
        }
        return minCut;
	}
	
	public Main() {
	//Constructor - Here aList is an ArrayList of ArrayLists
	this.aList = new ArrayList<ArrayList<Integer> >();
    this.nodeArray = new ArrayList<Integer>();// allocate memory on heap
	}
	
    public static void main(String[] args)
    {
    	
    	Main main = new Main();
        
    	int numberIter = 100;
        int minCut = main.getMinCut(numberIter);
	        
		if( DEBUG_MODE ) {
	        for (int i = 0; i < main.aList.size(); i++) {
	            for (int j = 0; j < main.aList.get(i).size(); j++) {	//to length of current sock
	                System.out.print(main.aList.get(i).get(j) + " "); //print each element in sock
	            }
	            System.out.print("\n");
	        }
	        
	        System.out.print("Array: { ");
	        for (int i = 0; i < main.nodeArray.size(); i++) {
	            System.out.print(main.nodeArray.get(i)+ " ");//print nodeArray
	        }
	        System.out.println("}. ");
		}
        
        System.out.println( "The Min-cut found is: " + minCut );
        
    }
}    
    
    