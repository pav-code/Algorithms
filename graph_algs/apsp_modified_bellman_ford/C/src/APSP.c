/*
 * APSP.c
 *
 *  Created on: Aug. 17, 2021
 *      Author: pav
 */
#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <string.h>
#include <stdbool.h>

#define FILE_COLUMNS 3
#define MIN(a,b) (((a)<(b))?(a):(b))
#define INF 100000
#define NAN0 -123456789
#define OPTIMIZATION_K 2
#define DEBUG false

struct shortestPath
{
   int source;
   int destination;
   int length;
};
typedef struct shortestPath shortestPath;

struct graphParameters
{
   int n;
   int m;
   int* rawFile;
   int* adjacencyMatrix;
   shortestPath smallestPath;
};
typedef struct graphParameters graphParameters;

enum graphLabel{GraphOne = 1, GraphTwo = 2, GraphThree = 3, GraphLarge = 4};

char* getInputSource( int enumSourceGraph );
void importGraph( char* filename, graphParameters* graph );
void formAdjacencyMatrix( graphParameters* graph );
void floydWarshall( graphParameters* graph );
void setBoundaryConditions( graphParameters* graph, int A[][ graph->n + 1 ][ graph->n + 1 ] );
void printAllShortestPaths( graphParameters* graph, int A[][ graph->n + 1 ][ graph->n + 1 ] );
bool detectNegativeCycle( graphParameters* graph, int A[][ graph->n + 1 ][ graph->n + 1 ] );
void findShortestShortestPath( graphParameters* graph, int A[][ graph->n + 1 ][ graph->n + 1 ] );

int main()
{
	printf("Start All-Pair-Shortest-Path Algorithm.\n");

	graphParameters graph;

	char* filename = getInputSource( GraphLarge );
	importGraph( filename, &graph );
	formAdjacencyMatrix( &graph );
	floydWarshall( &graph );

	return 0;
}

char* getInputSource( int enumSourceGraph )
{
	char* filename;
	if( DEBUG == true )
	{
		filename = "./src/test1.txt";
	}
	else if( enumSourceGraph == GraphOne )
	{
		filename = "./src/graph1.txt";
	}
	else if( enumSourceGraph == GraphTwo )
	{
		filename = "./src/graph2.txt";
	}
	else if( enumSourceGraph == GraphThree )
	{
		filename = "./src/graph3.txt";
	}
	else if( enumSourceGraph == GraphLarge )
	{
		filename = "./src/graphLarge.txt";
	}
	return filename;
}

void importGraph( char* filename, graphParameters* graph )
{
	FILE *myFile;
	myFile = fopen(filename, "r");

	int header[2];
	fscanf( myFile, "%d %d", &header[0], &header[1] );

	graph->n = header[0];
	graph->m = header[1];

	graph->rawFile = (int*) malloc( graph->m * sizeof(int) * FILE_COLUMNS );

	for (int i = 0; i < graph->m * FILE_COLUMNS; i = i + FILE_COLUMNS)
	{
		fscanf(myFile, "%d %d %d", &graph->rawFile[i], &graph->rawFile[i + 1], &graph->rawFile[i + 2]);
	}
	fclose(myFile);
}

void formAdjacencyMatrix( graphParameters* graph )
{
	graph->adjacencyMatrix = (int*) malloc( (graph->n + 1 ) * ( graph->n + 1 ) * sizeof(int) );

	for( int nodeA = 0; nodeA < graph->n + 1; nodeA++)
	{
		for( int nodeB = 0; nodeB < graph->n + 1; nodeB++)
		{
			int addr = nodeA * ( graph->n + 1 ) + nodeB;
			graph->adjacencyMatrix[ addr ] = NAN0;
		}
	}

	for( int edge = 0; edge < graph->m; edge++ )
	{
		int srcVertex = graph->rawFile[ 3 * edge];
		int dstVertex = graph->rawFile[ 3 * edge + 1];
		int distance = graph->rawFile[ 3 * edge + 2];
		graph->adjacencyMatrix[ srcVertex * (graph->n + 1) + dstVertex ] = distance;
	}
	free(graph->rawFile);
}

void floydWarshall( graphParameters* graph )
{
	int ( *A )[ graph->n + 1 ][ graph->n + 1 ] = malloc( sizeof(int[ OPTIMIZATION_K ][ graph->n + 1 ][ graph->n + 1 ]) );

	setBoundaryConditions( graph, A );

	for( int k = 1; k <= graph->n; k++)
	{
		for( int i = 1; i <= graph->n; i++)
		{
			for( int j = 1; j <= graph->n; j++)
			{
				int currK = k % OPTIMIZATION_K;
				int prevK = ( k-1 ) % OPTIMIZATION_K;

				A[ currK ][ i ][ j ] = MIN(
						A[prevK][i][j],

						A[prevK][i][k] +
						A[prevK][k][j] );
			}
		}
	}

	printAllShortestPaths( graph, A );

	bool negativeCycle = detectNegativeCycle( graph, A );

	if( negativeCycle == false )
	{
		findShortestShortestPath( graph, A );
	}
	free(A);
}

void setBoundaryConditions( graphParameters* graph, int A[][ graph->n + 1 ][ graph->n + 1 ] )
{
	memset( A, 0, OPTIMIZATION_K * pow( graph->n + 1, 2) );

	for( int i = 1; i <= graph->n; i++)
	{
		for( int j = 1; j <= graph->n; j++)
		{
			if( i == j )
			{
				A[0][i][j] = 0;
			}
			else if( graph->adjacencyMatrix[ i * ( graph->n + 1 ) + j ] != NAN0 )
			{
				A[0][i][j] =
						graph->adjacencyMatrix[ i * ( graph->n + 1 ) + j ];
			}
			else
			{
				A[0][i][j] = INF;
			}
		}
	}
	free( graph->adjacencyMatrix );
}

bool detectNegativeCycle( graphParameters* graph, int A[][ graph->n + 1 ][ graph->n + 1 ] )
{
	int finalKvalue = graph->n % OPTIMIZATION_K;

	bool negativeCycle = false;
	for( int i = 1; i <= graph->n; i++)
	{
		if( A[ finalKvalue ][i][i] < 0 )
		{
			negativeCycle = true;
		}
	}
	if( negativeCycle == true ){
		printf("Negative Cycle Detection: True.\n" );
	} else {
		printf("Negative Cycle Detection: False.\n" );
	}

	return( negativeCycle );
}

void printAllShortestPaths( graphParameters* graph, int A[][ graph->n + 1 ][ graph->n + 1 ] )
{
	int finalKvalue = graph->n % OPTIMIZATION_K;
	for( int i = 1; i <= graph->n; i++)
	{
		for( int j = 1; j <= graph->n; j++)
		{
			if( A[finalKvalue][i][j] < INF - 1000 )
			{
				printf("To Source %d, Destination %d, Min-Dist: %d \n", i,j, A[finalKvalue][i][j] );
			}
		}
	}
}

void findShortestShortestPath( graphParameters* graph, int A[][ graph->n + 1 ][ graph->n + 1 ] )
{
	shortestPath path;
	path.length = INF;
	int finalKvalue = graph->n % 2;

	for( int i = 1; i <= graph->n; i++)
	{
		for( int j = 1; j <= graph->n; j++)
		{
			if( A[finalKvalue][i][j] < path.length )
			{
				path.source = i;
				path.destination = j;
				path.length = A[finalKvalue][i][j];
			}
		}
	}
	printf("The shortest-shortest path is between: Source: %d, Destination: %d. Length: %d",
			path.source,
			path.destination,
			path.length);

	graph->smallestPath.source = path.source;
	graph->smallestPath.destination = path.destination;
	graph->smallestPath.length = path.length;
}
