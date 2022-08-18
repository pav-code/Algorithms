package TwoSumAlg;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.HashSet;
import java.util.*;  


public class twoSum{
	
	int[] tarray;
	HashSet <Long> intSet;
	public static final int INPUT_DATA_LENGTH = 20000;
	
	public void importDataIntoHashSet() {
		intSet = new HashSet<Long>();
		try {
			 File myObj = new File("./data.txt");
		     Scanner myReader = new Scanner(myObj);
		     while (myReader.hasNextLine()) {
		        String data = myReader.nextLine();
		        long a = Long.parseLong(data);
		        intSet.add(a);
		      }
		      myReader.close();
		} catch (FileNotFoundException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		      System.exit(-1);
		}
	}
	
	public void populateSumArr() {
		tarray = new int[INPUT_DATA_LENGTH + 1];
		for(int i = -10000; i<=10000; i++) {
			tarray[i+10000] = i;
		}
	}
	
	public void twoSumAlgorithm() {
		
        Iterator<Long> itr=this.intSet.iterator();    
        long lookup = 0;
        int totalOccurences = 0;
        
        for(int i=0; i < INPUT_DATA_LENGTH + 1; i++) {

	        while(itr.hasNext()){
	        	long x = itr.next();
	        	lookup = tarray[i] - x;
	        	if(intSet.contains(lookup) && lookup != x) {
	        		totalOccurences++;
	        		break;
	        	}
	        }
	        itr = this.intSet.iterator();
	        
	        if( i % 100 == 0 ) {
	        	System.out.println("Periodic Update Total: " + totalOccurences + " for T: " + i);
	        }
        }
        System.out.print("Pairs x&y that sum up to t: " + totalOccurences + " \n");
	}
	
	public static void main(String[] args) {
		
		twoSum twoSum = new twoSum();
		
		twoSum.importDataIntoHashSet();
		twoSum.populateSumArr();
		twoSum.twoSumAlgorithm();
		
	}
}