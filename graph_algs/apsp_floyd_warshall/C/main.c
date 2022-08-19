/*
 * main.c
 *
 *  Created on: Aug. 16, 2021
 *      Author: pav
 */
#include <stdio.h>

#define N 3
#define BF_N 5
#define INF 10000
#define MIN(a,b) (((a)<(b))?(a):(b))

void modifiedFloydWarshall();
void FloydWarshall();
void BellmanFord();
void modifiedBellmanFord();

int main(){


	//FloydWarshall();
	//modifiedFloydWarshall();
	BellmanFord();
	modifiedBellmanFord();

	return 0;
}

void BellmanFord()
{

	int n = 5;
	int infinity = 10000;

	int graph[BF_N][BF_N] = {
			{ 0, 2, infinity, infinity, infinity },
			{ infinity, 0, 1, 2, infinity },
			{ -2, infinity, 0, infinity, 4 },
			{ infinity, infinity, infinity, 0, 2 },
			{ infinity, infinity, infinity, infinity, 0 },};

	int A[BF_N-1][BF_N];
	A[0][0] = 0; // source vertex
	A[0][1] = INF;
	A[0][2] = INF;
	A[0][3] = INF;
	A[0][4] = INF;

	for( int i = 1; i < n; i++)
	{
		for( int v = 0; v < n; v++ )
		{
			int minInDegree = infinity;

			for( int w = 0; w < n; w++ )
			{
				int min = A[i-1][w] + graph[w][v];
				if( min < minInDegree ){
					minInDegree = min;
				}
			}
			A[i][v] = MIN( A[i-1][v], minInDegree );

		}
	}

	printf("To V0: %d \n", A[BF_N-1][0] );
	printf("To V1: %d \n", A[BF_N-1][1] );
	printf("To V2: %d \n", A[BF_N-1][2] );
	printf("To V3: %d \n", A[BF_N-1][3] );
	printf("To V4: %d \n", A[BF_N-1][4] );

}

void modifiedBellmanFord()
{
	int n = 5;
	int infinity = 10000;

	int graph[BF_N][BF_N] = {
			{ 0, 2, infinity, infinity, infinity },
			{ infinity, 0, 1, 2, infinity },
			{ -2, infinity, 0, infinity, 4 },
			{ infinity, infinity, infinity, 0, 2 },
			{ infinity, infinity, infinity, infinity, 0 },};

	int A[BF_N-1][BF_N];
	A[0][0] = 0; // source vertex
	A[0][1] = INF;
	A[0][2] = INF;
	A[0][3] = INF;
	A[0][4] = INF;

	for( int i = 1; i < n; i++)
	{
		if( i % 2 == 1 ) // Odd Branch
		{
			for( int v = 0; v < n; v++ )
			{
				int minInDegree = infinity;

				for( int w = 0; w < n; w++ )
				{
					if( w < v )
					{
						int min = A[i][w] + graph[w][v];
						if( min < minInDegree ){
							minInDegree = min;
						}
					}
				}
				A[i][v] = MIN( A[i-1][v], minInDegree );
			}
		}
		else // Even Branch
		{
			for( int v = n - 1; v >= 0; v-- )
			{
				int minInDegree = infinity;

				for( int w = 0; w < n; w++ )
				{
					if( w > v )
					{
						int min = A[i][w] + graph[w][v];
						if( min < minInDegree ){
							minInDegree = min;
						}
					}
				}
				A[i][v] = MIN( A[i-1][v], minInDegree );
			}
		}


	}

	printf("To V0: %d \n", A[BF_N-1][0] );
	printf("To V1: %d \n", A[BF_N-1][1] );
	printf("To V2: %d \n", A[BF_N-1][2] );
	printf("To V3: %d \n", A[BF_N-1][3] );
	printf("To V4: %d \n", A[BF_N-1][4] );

}

void FloydWarshall()
{
	int arr[N][N][N+1] = { 0 };

	arr[0][0][0] = 0;
	arr[0][1][0] = -1;
	arr[0][2][0] = INF;

	arr[1][0][0] = INF;
	arr[1][1][0] = 0;
	arr[1][2][0] = -1;

	arr[2][0][0] = -1;
	arr[2][1][0] = INF;
	arr[2][2][0] = 0;

	int viewK = 0;
	printf(" %d %d %d \n", arr[0][0][viewK], arr[0][1][viewK], arr[0][2][viewK] );
	printf(" %d %d %d \n", arr[1][0][viewK], arr[1][1][viewK], arr[1][2][viewK] );
	printf(" %d %d %d \n", arr[2][0][viewK], arr[2][1][viewK], arr[2][2][viewK] );

	for( int k = 1; k <= N; k++)
	{
		for( int i = 0; i < N; i++)
		{
			for( int j = 0; j < N; j++)
			{
				arr[i][j][k] = MIN(
						arr[i][j][k-1],
						arr[i][k-1][k-1] + arr[k-1][j][k-1] );

			}
		}
	}

	printf("\n");

	viewK = 3;
	printf(" %d %d %d \n", arr[0][0][viewK], arr[0][1][viewK], arr[0][2][viewK] );
	printf(" %d %d %d \n", arr[1][0][viewK], arr[1][1][viewK], arr[1][2][viewK] );
	printf(" %d %d %d \n", arr[2][0][viewK], arr[2][1][viewK], arr[2][2][viewK] );

}

void modifiedFloydWarshall()
{
	int n = 4;
	int arr[4][4][5] = { 0 };

	arr[0][0][0] = 0;
	arr[0][1][0] = 1;
	arr[0][2][0] = 1;
	arr[0][3][0] = 1;

	arr[1][0][0] = 0;
	arr[1][1][0] = 0;
	arr[1][2][0] = 0;
	arr[1][3][0] = 0;

	arr[2][0][0] = 0;
	arr[2][1][0] = 0;
	arr[2][2][0] = 0;
	arr[2][3][0] = 1;

	arr[3][0][0] = 0;
	arr[3][1][0] = 1;
	arr[3][2][0] = 0;
	arr[3][3][0] = 0;


	int viewK = 0;
	printf(" %d %d %d %d \n", arr[0][0][viewK], arr[0][1][viewK], arr[0][2][viewK], arr[0][3][viewK] );
	printf(" %d %d %d %d \n", arr[1][0][viewK], arr[1][1][viewK], arr[1][2][viewK], arr[1][3][viewK] );
	printf(" %d %d %d %d \n", arr[2][0][viewK], arr[2][1][viewK], arr[2][2][viewK], arr[2][3][viewK] );
	printf(" %d %d %d %d \n", arr[3][0][viewK], arr[3][1][viewK], arr[3][2][viewK], arr[3][3][viewK] );

	for( int k = 1; k <= n; k++)
	{
		for( int i = 0; i < n; i++)
		{
			for( int j = 0; j < n; j++)
			{
				arr[i][j][k] = arr[i][j][k-1] + arr[i][k-1][k-1] * arr[k-1][j][k-1];

				int viewK = 1;
				printf(" %d %d %d %d \n", arr[0][0][viewK], arr[0][1][viewK], arr[0][2][viewK], arr[0][3][viewK] );
				printf(" %d %d %d %d \n", arr[1][0][viewK], arr[1][1][viewK], arr[1][2][viewK], arr[1][3][viewK] );
				printf(" %d %d %d %d \n", arr[2][0][viewK], arr[2][1][viewK], arr[2][2][viewK], arr[2][3][viewK] );
				printf(" %d %d %d %d \n", arr[3][0][viewK], arr[3][1][viewK], arr[3][2][viewK], arr[3][3][viewK] );
			}
		}
	}

	printf("\n");

	viewK = 1;
	printf(" %d %d %d %d \n", arr[0][0][viewK], arr[0][1][viewK], arr[0][2][viewK], arr[0][3][viewK] );
	printf(" %d %d %d %d \n", arr[1][0][viewK], arr[1][1][viewK], arr[1][2][viewK], arr[1][3][viewK] );
	printf(" %d %d %d %d \n", arr[2][0][viewK], arr[2][1][viewK], arr[2][2][viewK], arr[2][3][viewK] );
	printf(" %d %d %d %d \n", arr[3][0][viewK], arr[3][1][viewK], arr[3][2][viewK], arr[3][3][viewK] );
}
