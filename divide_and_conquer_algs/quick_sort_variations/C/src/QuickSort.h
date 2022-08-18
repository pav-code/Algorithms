/*
 * QuickSort.h
 *
 *  Created on: Aug. 31, 2021
 *      Author: pav
 */

#ifndef SRC_QUICKSORT_H_
#define SRC_QUICKSORT_H_

void testCases( char testType[] );
void quickSortFirst( int* array, const int L, const int R);
void quickSortLast( int* array, int L, int R);
void quickSortMiddle( int* array, int L, int R);
void swap( int* A, int* B );
void runQSCmps(int* array, int len, char testType[] );
int* fileLoader();
void printList( int* array, int len );

#endif /* SRC_QUICKSORT_H_ */
