package A1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Main {
	long inversions = 0;
	
	public void test() {
		int Array1[] = {1,4,3,2};
		long answer = this.naiveInsertCount(Array1);
		System.out.println("Array1 # inversions: " + answer);
		
		int Array2[] = {1,3,5,2,4,6};
		answer = this.naiveInsertCount(Array2);
		System.out.println("Array2 # inversions: " + answer);
		
		int Array3[] = {1,2,3,4,5,6};
		answer = this.naiveInsertCount(Array3);
		System.out.println("Array3 # inversions: " + answer);
		
		int Array4[] = {6,5,4,3,2,1};
		answer = this.naiveInsertCount(Array4);
		System.out.println("Array4 # inversions: " + answer);
		
		int[] myIntArray = readInputArray();
		
		answer = this.naiveInsertCount(myIntArray);
		System.out.println("Runtime: O(n^2) \t\t Naive implementation.\t\t Inversion #: " + answer );
//        System.out.println(myIntArray[2]);
        
//        int Array5[] = {1,2,5,8,3,6,7,9};
//        List<Integer> ints = loadList( Array5 );
//        inversions = 0;
//        List<Integer> sorted = mergeSort( ints );
//        System.out.println( "Inversions: " + inversions + " for array: " + ints.toString() );
        
        inversions = 0;
        List<Integer> hugeArray = convertArrayToList( myIntArray );
        List<Integer> hugeSorted = mergeSort( hugeArray );
        
        System.out.println( "Runtime: O(n * log2(n)) \t Div&Conq implementation.\t Inversion #: " + inversions );
        
//        boolean printArray = true;
//        testMergeInversions( Array1, printArray );
//        testMergeInversions( Array2, printArray );
//        testMergeInversions( Array3, printArray );
//        testMergeInversions( Array4, printArray );
        
        
        //List<Integer> sortedAssignment = mergeSort( loadList(assignmentArray) );
        
	}
	
	public void testNativeInversions( int Array[] ) {
		long inversions = naiveInsertCount(Array);
		System.out.println( "Naive Inversions: " + inversions + "." );
	}
	
	public void testMergeInversions( int Array[], boolean printArray ) {
		inversions = 0;
		List<Integer> integerList = loadList( Array );
        mergeSort( integerList );
        
        if( printArray == true ) {
        	System.out.println( "Merge Inversions: " + inversions + " for array: " + integerList.toString() );	
        } else {
        	System.out.println( "Merge Inversions: " + inversions + "." );
        }
        
	}
	
	public List<Integer> loadList( int Array[] ) {
		List<Integer> intList = new ArrayList<>();
		
		for( int i = 0; i < Array.length; i++ ) {
			intList.add( Array[i] );
		}
		
		return intList;
	}
	
	public List<Integer> convertArrayToList( int Array[] ) {
		List<Integer> stanfArray = new ArrayList<>();
		
		for( int i = 0; i < Array.length; i++ ) {
			stanfArray.add( Array[i] );
		}
		
		return stanfArray;
	}
	
	public int[] readInputArray() {
		int[] myIntArray = new int[100000];
		try {
			///home/pav/Desktop/pav-code/DivNConq-Algorithms/inversion_count/Java/src/A1/InputFile.txt
			 File myObj = new File("./A1/InputFile.txt");
		     Scanner myReader = new Scanner(myObj);
		     int k=0;
		     while (myReader.hasNextLine()) {
		        String data = myReader.nextLine();
		        int a = Integer.parseInt(data);
		        myIntArray[k++] = a;		        
		      }
		      myReader.close();
		} catch (FileNotFoundException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		      return new int [1];
		}
		return myIntArray;
	}
	
	public long naiveInsertCount(int Array[]) {
		long inv = 0;
		for( int i=0; i < Array.length; i++ ) {
			for( int j=i+1; j < Array.length; j++ ) {
				if( Array[i] > Array[j] ){
					inv++;
				}
			}
		}
		return inv;
	}

	public List<Integer> mergeSort( List<Integer> inputArr ) {
		
		// Base Case
		if( inputArr.size() == 1 ) {
			return inputArr;
		}
		
		// Recursive Calls
		int n = inputArr.size();
		List<Integer> L = mergeSort( inputArr.subList(0, n/2) );
		List<Integer> R = mergeSort( inputArr.subList(n/2, n) );
		
		// Merge
		int j = 0; 
		int i = 0;
		List<Integer> sortedArr = new ArrayList<>();
		
		for( int k = 0; k < n; k++ ) {
			if( L.size() == i ) {
				sortedArr.add( R.get(j) );
				j++;
				continue;
			} else if( R.size() == j ) {
				sortedArr.add( L.get(i) );
				i++;
				continue;
			}
			
			if( L.get(i) < R.get(j) ) {
				sortedArr.add( L.get(i) );
				i++;
			} else {
				sortedArr.add( R.get(j) );
				j++;
				inversions = inversions + (L.size() - i);
			}
		}
		return sortedArr;
	}
	
	public static void main(String[] args) {
		Main myMainObj = new Main();
		myMainObj.test();
	}
  
}
