package maxSpacekClusters;

public class Nodes implements Comparable<Nodes> {

	private int node;
	private int bitCode;
	
	public Nodes( int n, int code) {		 
		node = n;
		bitCode = code;
	}
	
	int getNode() {
		return node;
	}
	
	int getBitCode() {
		return bitCode;
	}
	
	@Override
	public String toString() {
		return( "Node: " + node + " Bit-Code: " + bitCode );
	}
	
	@Override
	public int compareTo( Nodes A ){    
		if( this.getBitCode() == A.getBitCode() ) {   // Use this.getBitCode() method for good practice
			return 0;
		} else if( this.getBitCode() > A.getBitCode()) {    
			return 1;
		} else {
			return -1;
		}   
	}

}
