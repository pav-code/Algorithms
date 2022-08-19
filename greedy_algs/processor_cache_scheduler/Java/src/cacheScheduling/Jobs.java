package cacheScheduling;

public class Jobs implements Comparable<Jobs> {
	private int weight;
	private int length;
	private double score;
	
	Jobs(int w, int l){
		weight = w;
		length = l;
	}
	
	int getWeight() {
		return weight;
	}
	
	int getLength() {
		return length;
	}
	
	double getScore() {
		return score;
	}
	
	void calcScore_f1() {
		score = weight - length;
	}
	
	void calcScore_f2() {
		score = (double) weight / length; // Java needs a cast otherwise it'll do int div
	}
	
	/* If score is equal return job with higher weight */
	@Override
	public int compareTo(Jobs toCmp) {
		if( toCmp.getScore() > this.getScore()) {
			return 1;
		} else if ( toCmp.getScore() < this.getScore()) {
			return -1;
		} else {
			if( toCmp.getWeight() > this.getWeight() ) {
				return 1;
			} else if( toCmp.getWeight() < this.getWeight() ) {
				return -1;
			} else {
				return 0;
			}
		}
	}
	
	@Override
	public String toString() {
		return( "w: " + weight + " l: " + length + " score: " + score );
	}
}
