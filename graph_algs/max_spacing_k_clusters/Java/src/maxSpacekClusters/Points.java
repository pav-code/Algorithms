package maxSpacekClusters;

public class Points implements Comparable<Points> {

	private int x;
	private int y;
	private int distance;
	
	public Points( int xCoord, int yCoord, int dist) {		 
		x = xCoord;
		y = yCoord;
		distance = dist;
	}
	
	int getX() {
		return x;
	}
	
	int getY() {
		return y;
	}
	
	int getDistance() {
		return distance;
	}
	
	@Override
	public String toString() {
		return( "X: " + x + " Y: " + y + " Distance: " + distance );
	}
	
	@Override
	public int compareTo( Points A ){    
		if( this.getDistance() == A.getDistance() ) {   // Use this.getDistance() method for good practice
			return 0;
		} else if( this.getDistance() > A.getDistance()) {    
			return 1;
		} else {
			return -1;
		}   
	}
}
