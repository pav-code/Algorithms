import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Scanner;

/*
 * 
 * [number_of_nodes] [number_of_edges]

[one_node_of_edge_1] [other_node_of_edge_1] [edge_1_cost]

[one_node_of_edge_2] [other_node_of_edge_2] [edge_2_cost]

Good prebiotic foods include vegetables such as artichokes, asparagus, garlic, onions, 
and any green vegetable; fruits such as bananas, berries, and tomatoes; herbs such as 
chicory or garlic; grains like barley, oat, and wheat; and other fibers such as inulin 
that may be available on its own or added to foods such as granola bars, cereal, and yogurt.
 */

public class Prims {
	ArrayList<Graph> graph;
	int numNodes;
	int numEdges;
	ArrayList<Integer> X;
	ArrayList<Graph> T;
	PriorityQueue<Graph> minHeap;
	static final int infinity = 1000000;
	
	public static void main(String[] args) {
		System.out.println("Begin.");
		
		// Parameters
		boolean testMode = false;
		
		Prims primsObj = new Prims();
		primsObj.importJobs( testMode );
		
		//primsObj.primsNaive();
		primsObj.primsFast();
		
		int cost = primsObj.calcMSTCost();
		
		System.out.println("Total cost of MST: " + cost);
		System.out.println("End.");
	}
	
	public void importJobs( boolean test ) {
		graph = new ArrayList< Graph >();
		
		try {
			File myObj;
			if( test == true ) {
				myObj = new File("./src/test1.txt"); 
			} else {
				myObj = new File("./src/graph.txt");
			}
			Scanner myReader = new Scanner(myObj);
		
			String header = myReader.nextLine(); // "500 2184" -> "500" "2184"
			String[] headerSplit = header.split("\s");
			
			numNodes = Integer.parseInt( headerSplit[0] );
			numEdges = Integer.parseInt( headerSplit[1] );
			
			while (myReader.hasNextLine()) {
				String data = myReader.nextLine(); // "1 2 6807"
				String[] lineSplit = data.split("\s"); // "1" "2" "6807"
				
				int edgeFrom	= Integer.parseInt( lineSplit[0] );
				int edgeTo 		= Integer.parseInt( lineSplit[1] );
				int cost 		= Integer.parseInt( lineSplit[2] );
				graph.add( new Graph( edgeFrom, edgeTo, cost ) );
				
		    }
		    myReader.close();
		} catch (FileNotFoundException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		}
	}

	public void fillHeap() {
		Comparator<Graph> graphComparator = new graphComparator();
		minHeap = new PriorityQueue<Graph>( numNodes - 1, graphComparator);
		
		for( int node = 2; node <= numNodes; node++ ) {
			minHeap.add( new Graph( node, node, infinity ) );
		}
		
		int startingNode = X.get(0);
		
		for( int edge = 0; edge < numEdges; edge++ ) {
			if( graph.get(edge).fromNode == startingNode /*|| graph.get(edge).toNode == startingNode*/ ) {				
				removeNode( graph.get(edge).toNode );
				minHeap.add( new Graph( graph.get(edge).toNode, graph.get(edge).toNode, graph.get(edge).cost ) );
			}
		}
	}
	
	public int removeNode( int nodeToRemove ) {
		Iterator<Graph> it = minHeap.iterator();
	 
		while( it.hasNext() ) {
			Graph obj = it.next();
			if( obj.toNode == nodeToRemove ) {
				int cost = obj.cost;
				it.remove();
				return cost;
			}
		}
		return infinity;
	}
	
	/*
	 * primsNaive - has a bug. Most likely due to the fact edges are ambigously defined. 
	 * An edge in the undirected graph might be defined 4 5 12 meaning it starts at 4, connects
	 * to node 5 with a cost of 12. But it will NOT appear in the graph a second time as 
	 * 5 4 12, even though the graphs are undirected.
	 */
	public void primsNaive() {
		
		X = new ArrayList<Integer>();
		X.add( graph.get(0).fromNode );
		
		T = new ArrayList<Graph>();
		
		while( X.size() < numNodes ) {
			int minCost = 100000;
			Graph minNode = null;
			
			for( int uIdx = 0; uIdx < X.size(); uIdx++ ) {
				Integer u = X.get(uIdx);
				
				for( int vIdx = 0; vIdx < graph.size(); vIdx++ ) {
					int startLocation = graph.get(vIdx).fromNode;
					
					if( startLocation == u ) { // for toNode
						int v = graph.get(vIdx).toNode; // to fromNode
						
						if( X.indexOf(v) == -1) {
							if( graph.get(vIdx).cost < minCost ) {
								minNode = graph.get(vIdx);
								minCost = minNode.cost;
							}
						}
					}									
				}
			}
			
			T.add( minNode );
			X.add( minNode.toNode );
			
		}
		
	}
	
	public void primsFast() {
		X = new ArrayList<Integer>();
		X.add( graph.get(0).fromNode );
		
		T = new ArrayList<Graph>();
		this.fillHeap();
		
		while( X.size() < numNodes ) {
			Graph minNode = minHeap.poll();
			
			X.add( minNode.toNode );
			T.add( minNode );
			
			for( int node = 0; node < numEdges; node++ ) {
				Graph edge = graph.get( node );
				if( minNode.toNode == edge.fromNode && !X.contains( edge.toNode ) ) {
					int prevKey = removeNode( edge.toNode );
					int newKey = Math.min( prevKey, edge.cost );
					minHeap.add( new Graph( edge.toNode, edge.toNode, newKey ) );
				}
				
				if( minNode.toNode == edge.toNode && !X.contains( edge.fromNode )	) {
						int prevKey = removeNode( edge.fromNode );
						int newKey = Math.min( prevKey, edge.cost );
						minHeap.add( new Graph( edge.fromNode, edge.fromNode, newKey ) );
				}
			}
		}
	}
	
	public int calcMSTCost() {
		int totalCost = 0;
		
		for( int edgeIdx = 0; edgeIdx < numNodes - 1; edgeIdx++ ) {
			Graph edge = T.get(edgeIdx);
			totalCost += edge.cost;
		}
		
		return totalCost;
	}
}
