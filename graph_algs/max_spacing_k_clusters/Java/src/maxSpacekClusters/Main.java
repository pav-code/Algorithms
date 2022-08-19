package maxSpacekClusters;

public class Main {
	static final int EUCLEDIAN_CLUSTER_ALGORITHM = 0;
	static final int HAMMING_CLUSTER_ALGORITHM = 1;
	
	public static void main( String[] args ) {
		System.out.println("Begin.");
		
		int algorithmSelection = HAMMING_CLUSTER_ALGORITHM; // must be static to be used w/o an obj!
		
		if( algorithmSelection == EUCLEDIAN_CLUSTER_ALGORITHM ) {
			EuclidianKClusters cluster = new EuclidianKClusters();
			cluster.eucledianClusters();
		} else if ( algorithmSelection == HAMMING_CLUSTER_ALGORITHM ) {
			HammingKClusters cluster = new HammingKClusters();
			cluster.hammingClusters();
		}
		
		System.out.println("End.");
	}
}
