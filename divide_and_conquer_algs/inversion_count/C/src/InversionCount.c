/*
 ============================================================================
 Name        : InversionCount.c
 Author      : pav
 Version     :
 Copyright   : Your copyright notice
 Description : Hello World in C, Ansi-style
 ============================================================================
 */

#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <string.h>

#define INPUTSIZE 100000

void testPrinter (int d[],int n);
int* fileLoader();
int* mergeSort(int* inArray, int n);

int* fileLoader(){
	FILE *myFile;
	myFile = fopen("./src/InputFile.txt", "r");

	//read file into array
	int* tempArr = (int*) malloc( INPUTSIZE * sizeof(int) );
	int i;

	for (i = 0; i < INPUTSIZE; i++)
	{
		fscanf(myFile, "%d", &tempArr[i]);
	}

	fclose(myFile);

	return tempArr;
}
void testPrinter (int* d,int n)
{
	printf("Test Printer: ");
	int i;
	for(i = 0; i < n; i++)
	{
		if( i % 100 == 0 ){
			printf("\n");
			printf("%d ", d[i]);
		}
	 	//fflush(stdout);
	}
	printf("\n");
}

int main(void)
{
    int* tempArr = fileLoader();
	int n = INPUTSIZE;
	int* sortedArr;
	int* inputArr = (int*) malloc( n * sizeof(int) );
	memcpy(inputArr, tempArr, n * sizeof(int));

	clock_t start_t, end_t;
	start_t = clock();
	sortedArr = mergeSort(inputArr, n);
	end_t = clock();
	double total_t = (double)(end_t - start_t) / CLOCKS_PER_SEC;
	printf("Total time taken by CPU: %f\n", total_t  );
	// time: 0.029776 s

	if( DEBUG )
	{
		testPrinter( sortedArr, n );
	}


	return EXIT_SUCCESS;
}

int* mergeSort(int* inArray, int n)
{
	int* mergeArr = (int*) malloc( n * sizeof(int) );
	int mid, rLen, lLen;
	//Base Case
	if (n==1){
		return inArray;
	}

	// Recursive Stage
	if (n % 2 == 0)
	{
		mid = n/2;
		rLen = mid;
		lLen = mid;
	}
	else
	{
		mid = n/2+1;
		lLen = mid;
		rLen = mid -1;
	}
	int* L = mergeSort(inArray, lLen);
	int* R = mergeSort(inArray + mid, rLen);

	// Merge Stage
	int i, j, k;
	i=0, j=0, k=0;
	for(k=0; k<n; k++)
	{
		if(i == lLen){
			mergeArr[k] = R[j];
			j++;
			continue;
		}
		else if(j == rLen){
			mergeArr[k] = L[i];
			i++;
			continue;
		}

		if(L[i] < R[j])
		{
			mergeArr[k] = L[i];
			i++;
		}
		else
		{
			mergeArr[k] = R[j];
			j++;
		}
	}

	memcpy(inArray, mergeArr,  n * sizeof(int));
	free( mergeArr );
	return inArray;
}












