package Dijkstra;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;



public class Dijkstra{
	
	private ArrayList<ArrayList<Arcs> > dijkstraAdjList;
	ArrayList<Integer> X; // Nodes explored
	int [] A; // Distances
	
	public void importGraph() {
	
			this.dijkstraAdjList = new ArrayList<ArrayList<Arcs>>();
			this.X = new ArrayList<Integer>();
			
			try {
				File myObj = new File("./dijkstraData.txt");
				Scanner myReader = new Scanner(myObj);
			
				int nodeIdx = 0;
				while (myReader.hasNextLine()) {
					String data = myReader.nextLine();
				
					this.dijkstraAdjList.add( new ArrayList<Arcs>() );
//					for (String node: data.split("\t")) {
					String[] nodeArr = data.split("\t");
					
					for( int i=0; i<nodeArr.length; i++  ) {
						
						
						String[] inside = nodeArr[i].split(",");
						
						Arcs nodeInt;
						if(inside.length == 1) {
							nodeInt = new Arcs(inside[0]);
						}
						else {
							nodeInt = new Arcs(inside[0] , inside[1]);
						}
						// = Integer.parseInt(node);
						this.dijkstraAdjList.get(nodeIdx).add( nodeInt );
					
					}
					nodeIdx++;
					this.X.add(nodeIdx);
			    }
			    myReader.close();
			} catch (FileNotFoundException e) {
			      System.out.println("An error occurred.");
			      e.printStackTrace();
			}
		}
	
	public void removeNode(int nodetodelete) {
		for (int i=0; i<X.size(); i++) {
			ArrayList<Arcs> sock = this.dijkstraAdjList.get(X.get(i)-1);
			for(int j=1; j<sock.size(); j++) {
				if(sock.get(j).getNode()==nodetodelete) {
					sock.remove(j);
				}
			}
		}
		
	}
	
	public void dJIKSTRA() {
		X = new ArrayList<Integer>();
		A = new int [201];
		int shortestDist = 10000000;
		int atNode = 1;
		
		X.add(1);
		while(X.size() < dijkstraAdjList.size()) {
			
			for (int i=0; i<X.size(); i++) {
				ArrayList<Arcs> currentSock = this.dijkstraAdjList.get(X.get(i)-1);
				for(int j=1; j<currentSock.size(); j++) {
					Arcs returnPair = currentSock.get(j);
					if(!checkIfExplored(returnPair.getNode())) {
						if(A[ X.get(i) ] + returnPair.getDistance() < shortestDist) {
							shortestDist = A[ X.get(i) ] + returnPair.getDistance();
							atNode = returnPair.getNode();
						}
					}
				}
			}
			X.add(atNode);
			removeNode(atNode);
			A[atNode] = shortestDist;
			shortestDist = 10000000;
		}
		
		
		
	}
	
	public boolean checkIfExplored(int node) {
		for(int i=0; i<X.size(); i++) {
			if(X.get(i)==node) {
				return true;
			}
		}
		return false;
	}
	
	public void printShortestDistances() {
		System.out.println("Shortest distances from Node 1 are:\n");
		for(int i=1; i<A.length; i++) {
			System.out.println( "Node #: " + i + "\tDistance: " + A[i] );
		}
		System.out.println("\nShortest distances from Node 1 to all 200 nodes complete.");
	}
	
	
//	public int computeShortestPath(int nodeNr) {
//		int shortestDist = 0;
//		for (int count=0; count<this.dijkstraAdjList.get(nodeNr).size(); count++) {
//			if(this.dijkstraAdjList.get(nodeNr).get(count).distance<shortestDist) {
//				shortestDist = this.dijkstraAdjList.get(nodeNr).get(count).distance;
//			}
//		}
//		return shortestDist;
//	}
	
	public static void main(String[] args)
    {
		
		Dijkstra obj = new Dijkstra();
		obj.importGraph();
		obj.dJIKSTRA();
		obj.printShortestDistances();
		
//        for (int i = 0; i < obj.dijkstraAdjList.size(); i++) {
//            for (int j = 0; j < obj.dijkstraAdjList.get(i).size(); j++) {
//                System.out.println(obj.dijkstraAdjList.get(i).get(j).getNode() + " " + obj.dijkstraAdjList.get(i).get(j).getDistance());
//                System.out.println("new");
//            }
//			System.out.print("Is there a problem?");
//			System.out.print("\n");
//        }
		
 
		
    }
	

}

