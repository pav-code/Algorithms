package QuickSort;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Arrays;

class Main
{
	
	public int[] readInputArray() {
		int[] myIntArray = new int[10000];
		try {
			 File myObj = new File("./InputFile.txt");
		     Scanner myReader = new Scanner(myObj);
		     int k=0;
		     while (myReader.hasNextLine()) {
		        String data = myReader.nextLine();
		        int a = Integer.parseInt(data);
		        myIntArray[k++] = a;
		        //System.out.println(a);		        
		      }
		      myReader.close();
		} catch (FileNotFoundException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		      return new int [1];
		}
		return myIntArray;
	}
	
	// comparisons GLOBAL definition
	int comparisons;
// ORIGINAL QuickSort	
	void quickSort (int[] A, int l, int r ) {
		
		if( l == r | l > r ) { // 1-element or empty-array
			return;
		}
		
		this.comparisons =  this.comparisons + ( r - l ) ;
		int i = l+1;
		int j = l+1;
		int pivot = A[l];
		int temp;
		for( ; j <= r; j++ ) {
			if( A[j] < pivot) {
				temp = A[j];
				A[j]=A[i];
				A[i]=temp;
				i++;
			}
		}
		// place pivot in it's rightful place
		temp=pivot;
		A[l]=A[i-1];
		A[i-1]=temp;
		
		quickSort( A,  l, i-2 );
		quickSort( A,  i, r );

		return;
		
	}
	
	void quickSortGetM (int[] A, int l, int r ) {
		
		if( l == r | l > r ) { // 1-element or empty-array
			return;
		}
		
//		this.comparisons =  this.comparisons + ( r - l ) ;
		int i = l+1;
		int j = l+1;
		int pivot = A[l];
		int temp;
		for( ; j <= r; j++ ) {
			if( A[j] < pivot) {
				temp = A[j];
				A[j]=A[i];
				A[i]=temp;
				i++;
			}
		}
		temp=pivot;
		A[l]=A[i-1];
		A[i-1]=temp;
		
		quickSortGetM( A,  l, i-2 );
		quickSortGetM( A,  i, r );

		return;
		
	}

	void quickSortLast (int[] A, int l, int r ) {
		
		if( l == r | l > r ) { // 1-element or empty-array
			return;
		}
		
		int swap = 0;
		swap = A[r];
		A[r] = A[l];
		A[l] = swap;
		
		this.comparisons =  this.comparisons + ( r - l ) ;
		int i = l+1;
		int j = l+1;
		int pivot = A[l];
		int temp;
		for( ; j <= r; j++ ) {
			if( A[j] < pivot) {
				temp = A[j];
				A[j]=A[i];
				A[i]=temp;
				i++;
			}
		}
		temp=pivot;
		A[l]=A[i-1];
		A[i-1]=temp;
		
		quickSortLast( A,  l, i-2 );
		quickSortLast( A,  i, r );

		return;
		
	}
	
	void quickSortMed (int[] A, int l, int r ) {
		
		int pivot;
		if( l == r | l > r ) { // 1-element or empty-array
			return;
		}
		
		int midElement, midPos;
		if( (r-l+1) % 2 == 0 ){
			midPos =  l + (r-l+1)/2 - 1;
			midElement = A[ midPos ];
		}
		else {
			midPos = l + (r-l)/2;
			midElement = A[ midPos ];
		}
		
		//check if A is 3 or more members
		int[] arrToDetMedian = {A[l], midElement, A[r]};
		//int[] arrToDetMedian = {A[l], A[midElement], A[r]};
		this.quickSortGetM( arrToDetMedian, 0, arrToDetMedian.length - 1);
		int pivotM = arrToDetMedian[1];
		
		if ( r-l == 1) {
			pivot = A[l];
		}
		else if(pivotM == A[l] ) {
			pivot = A[l];
		}
//		else if(pivotM == A[midElement]) {
//			pivot = A[midElement];
//			int swap = 0;
//			swap = A[midElement];
//			A[midElement] = A[l];
//			A[l] = swap;
//		}
		else if(pivotM == midElement) {
			pivot = midElement;
			int swap = 0;
			swap = midElement;
			A[midPos] = A[l];
			A[l] = swap;
		}
		else {
			pivot = A[r];
			int swap = 0;
			swap = A[r];
			A[r] = A[l];
			A[l] = swap;
		}
		
		this.comparisons =  this.comparisons + ( r - l ) ;
		
		int i = l+1;
		int j = l+1;
		//int pivot = A[l];
		int temp;
		for( ; j <= r; j++ ) {
			if( A[j] < pivot) {
				temp = A[j];
				A[j]=A[i];
				A[i]=temp;
				i++;
			}
		}
		temp=pivot;
		A[l]=A[i-1];
		A[i-1]=temp;
		
		quickSortMed( A,  l, i-2 );
		quickSortMed( A,  i, r );

		return;
		
	}
	
	
	public static void main(String args[])
	{
		System.out.println("QuickSort");
		
		//int[] myIntArray = obj.readInputArray();
		//int A[] = {5, 7, 1, 2, 3, 845, 4, 0, 32}; //, 845, 4, 0, 32};
		Main obj = new Main();
		obj.comparisons = 0; 
		int[] unsortedArray = obj.readInputArray();
		obj.quickSortMed( unsortedArray, 0, unsortedArray.length - 1 );
		
		System.out.println("Array Size: " + unsortedArray.length);
		System.out.println("Number of comparisons for sorting: " + obj.comparisons);
		System.out.println("Array sorted.\n");
		
		int[] first5Elements = Arrays.copyOfRange(unsortedArray, 0, 5);
		int[] last5Elements = Arrays.copyOfRange(unsortedArray, unsortedArray.length - 5, unsortedArray.length);
		System.out.println("\tFirst 5 positions: \t" + Arrays.toString(first5Elements));
		System.out.println("\tLast 5 positions: \t" + Arrays.toString(last5Elements));
	
//		for( int i = 0; i<myIntArray.length; i++) {
//			System.out.print(myIntArray[i] + ", " );	
//		}

	}
}