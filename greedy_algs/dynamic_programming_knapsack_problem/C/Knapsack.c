/*
 * Knapsack.c
 *
 *  Created on: Aug. 12, 2021
 *      Author: pav
 */

#include <stdio.h>
#include <stdlib.h>

#define MAX(x, y) (((x) > (y)) ? (x) : (y))
#define ARRAY_ZERO_OFFSET 1
#define DEBUG 0
#define PROBLEM_1 1
#define OPTIMIZATION 1

struct inputParameters
{
   int n; 			// Number of Items
   int W; 			// Capacity
   int* weights; // //int* returnArray = (int*) malloc( INPUTSIZE * sizeof(int) );
   int* values;
};

typedef struct inputParameters inputParameters;

void fileLoader( char* filename, inputParameters* params );
void testVector( inputParameters* params );
int  knapsackDynamicProgramming( inputParameters* params );
int  knapsackDynamicProgrammingOptimized( inputParameters* params );
void cleanUp( inputParameters* params );

int main(void)
{
	printf("Begin Knapsack\n");

	char* filename;
	if( PROBLEM_1 ){
		filename = "./Input.txt";
	} else {
		filename = "./InputLarge.txt";
	}

	inputParameters paramsKnapsack;

	if( DEBUG )
	{
		testVector( &paramsKnapsack );
	}
	else
	{
		fileLoader( filename, &paramsKnapsack );
	}

	int maxValue;
	if( OPTIMIZATION )
	{
		maxValue = knapsackDynamicProgrammingOptimized( &paramsKnapsack );
	} else {
		maxValue = knapsackDynamicProgramming( &paramsKnapsack );
	}

	cleanUp( &paramsKnapsack );
	printf("Knapsack DP Algorithm Result: %d.\n", maxValue);
	return EXIT_SUCCESS;
}

int knapsackDynamicProgramming( inputParameters* params )
{
	int (*A)[params->W + 1] = malloc(sizeof(int[params->n + 1][params->W + 1]));

	for( int i = 0; i < params->W; i++)
	{
		A[0][i] = 0;
	}

	for( int i = 1; i <= params->n; i++)
	{
		for( int x = 0; x <= params->W; x++)
		{
			if( x - params->weights[ i - ARRAY_ZERO_OFFSET ] < 0 )
			{
				A[i][x] = A[i-1][x];
			}
			else
			{
				A[i][x] = MAX(
						A[i-1][x],
						A[i-1][x-params->weights[i-ARRAY_ZERO_OFFSET]] + params->values[i-ARRAY_ZERO_OFFSET] );
			}
		}
	}

	int maxKnapsackValue = A[params->n][params->W];
	free(A);
	return maxKnapsackValue;
}

int knapsackDynamicProgrammingOptimized( inputParameters* params )
{
	int (*A)[params->W + 1] = malloc(sizeof(int[2][params->W + 1]));

	for( int i = 0; i < params->W; i++)
	{
		A[0][i] = 0;
	}

	for( int i = 1; i <= params->n; i++)
	{
		for( int x = 0; x <= params->W; x++)
		{

			int currentIdx = i % 2;
			int previousIdx = ( i-1 ) % 2;

			if( x - params->weights[ i - ARRAY_ZERO_OFFSET ] < 0 )
			{
				A[currentIdx][x] = A[previousIdx][x];
			}
			else
			{
				A[currentIdx][x] = MAX(
						A[previousIdx][x],
						A[previousIdx][x-params->weights[i-ARRAY_ZERO_OFFSET]] + params->values[i-ARRAY_ZERO_OFFSET] );
			}
		}
	}

	int maxKnapsackValue = A[params->n % 2][params->W];
	free(A);
	return maxKnapsackValue;
}

void fileLoader( char* filename, inputParameters* params )
{
	FILE *myFile;
	myFile = fopen(filename, "r");

	fscanf(myFile, "%d %d", &params->W, &params->n);

	params->values = malloc( params->n * sizeof(int) );
	params->weights = malloc( params->n * sizeof(int) );

	for (int i = 0; i < params->n; i++)
	{
		fscanf(myFile, "%d %d", &params->values[i], &params->weights[i] );
	}
	fclose(myFile);
}

void cleanUp( inputParameters* params )
{
	free(params->weights);
	free(params->values);
}

void testVector( inputParameters* params )
{
	params->W = 6;
	params->n = 4;

	params->values[0] = 3;
	params->values[1] = 2;
	params->values[2] = 4;
	params->values[3] = 4;

	params->weights[0] = 4;
	params->weights[1] = 3;
	params->weights[2] = 2;
	params->weights[3] = 3;
}
