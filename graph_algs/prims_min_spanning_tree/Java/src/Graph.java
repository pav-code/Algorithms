import java.util.Comparator;

public class Graph {
	int fromNode;
	int toNode;
	int cost;
	
	public Graph( int n1, int n2, int c ) {
		fromNode = n1;
		toNode = n2;
		cost = c;
	}
	
	@Override
	public String toString() {
		return( "source node: " + this.fromNode + " dest node: " + this.toNode + " cost: " + this.cost );
	}
}

class graphComparator implements Comparator<Graph> {

	@Override
    public int compare(Graph a, Graph b) {
        if( a.cost < b.cost ) {
        	return -1;
        } else if ( a.cost > b.cost  ) {
        	return 1;
        } else {
        	return 0;
        }
    }
}

