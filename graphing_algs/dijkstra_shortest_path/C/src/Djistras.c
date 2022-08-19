#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <stdbool.h>

#define NUM_OF_NODES 200

struct nodeAndDistStruct
{
	int node;
	int distance;
};
typedef struct nodeAndDistStruct nodeAndDist;

char *readLine(FILE *file);
void createAdjacencyList( nodeAndDist adjacencyList [NUM_OF_NODES][50] );
int* djistras( nodeAndDist adjacencyList [NUM_OF_NODES][50] );
void fillLinkedList(nodeAndDist* currentSock, nodeAndDist adjList[NUM_OF_NODES][50], int node);
bool checkIfExplored(int node, int X[]);
int remainingNodes (int X[]);
int lenLinkedList(nodeAndDist* currentSock[]);
void removeNode(int nodeToremove, int X[], nodeAndDist adjList [NUM_OF_NODES][50]);
void printShortestDistances(int* shortestDistances, int size);

struct nodeAndDistStruct djistraData[2];

nodeAndDist currentSock[50] = {0};
int main()
{
	nodeAndDist* adjacencyList = malloc( NUM_OF_NODES * 50 * sizeof(nodeAndDist) );
    createAdjacencyList( adjacencyList );
    int* shortestDistances = djistras(adjacencyList);

    printShortestDistances(shortestDistances, NUM_OF_NODES);

    return 0;
}

void createAdjacencyList( nodeAndDist adjacencyList [NUM_OF_NODES][50] ) {

	//nodeAndDist (*adjList)[50] = malloc( 200 * 50 * sizeof(nodeAndDist) );


	FILE *myFile;
	myFile = fopen("./dijkstraData.txt", "r");
	const int fileSize = NUM_OF_NODES;

	for (int count=0; count < fileSize; count++){

	//TO DO: put this in a loop
		char* input = readLine(myFile);

		char *token;
		const char search[2] = "\t";
		char* firstNode;
	/* get the first token */
		firstNode = strtok(input, search);
		adjacencyList[count][0].node = atoi( firstNode );
//
//	printf("Here are our tokens.\n");
	/* walk through other tokens */
		int edge = 1;

		token = firstNode; //strtok(input, search);//initialize token to first part of string before '\t'
		while( token != NULL ) {
		   //printf( " %s\n", token );

		   token = strtok(NULL, search);	//get next token
		   if (token == NULL)
		   {
			   break;
		   }

		   char distance[100] = {0};
		   char node[100] = {0};
		   int whichNumber = 0;
		   int i = 0, idx = 0;
		   do{
			   if( token[i] == ','){
				   whichNumber = 1;
				   idx = 0;
				   continue;
			   }

			   if( whichNumber == 0 ){
				   node[idx++] = token[i];
			   } else {
				   distance[idx++] = token[i];
			   }

		   } while( token[++i] != '\0');


		   adjacencyList[count][edge].node = atoi( node );
		   adjacencyList[count][edge].distance = atoi( distance );
	       edge++;
		}
	}
}

int* djistras( nodeAndDist adjacencyList [NUM_OF_NODES][50] ){

	 int X [NUM_OF_NODES + 1] = {0};
	 int A [NUM_OF_NODES + 1] = {0};	//array with the shortest distances to the starting node


	 int shortestDistance = 10000000;
	 int atNode = 1;
	 X[0] = atNode;
	 nodeAndDist* currentSock = malloc( 50 * sizeof(nodeAndDist) );


	 while(remainingNodes(X) < 200){
		 for (int i = 0; i < 200; i++){
			 if( X[i] != 0 ){
				 fillLinkedList( currentSock, adjacencyList, X[i]);

				 int currSockSize = lenLinkedList ( currentSock );	//how big is sock?
				 for(int j=1; j < currSockSize; j++){
					 nodeAndDist pair = currentSock[j];
					 if (pair.node != 0){
						 if(!checkIfExplored(pair.node, X)){
							 if(	A[X[i]] + pair.distance < shortestDistance	){
								 shortestDistance=(A[ X[i] ] + pair.distance );
								 atNode= pair.node;

							 }
						 }
					 }
				 }
			 }
		 }
		 X[atNode-1]=atNode;
		 removeNode(atNode, X, adjacencyList);
		 A[atNode] = shortestDistance;
		 shortestDistance = 10000000;
	 }

	 int* shortestDistances = malloc( 201 * sizeof(int) );	//array with the shortest distances to the starting node
	 memcpy( shortestDistances, A, 201 * sizeof(int) );
	 return shortestDistances;
}

void fillLinkedList(nodeAndDist* currentSock, nodeAndDist adjList[200][50], int node){
	 for(int sockCount=0; sockCount<50; sockCount++){
		currentSock[sockCount] = adjList[node-1][sockCount]; //copying into sock
	 }
}

bool checkIfExplored(int node, int X[]){
	for(int i=0; i < NUM_OF_NODES; i++){
		if(X[i]==node){
			return true;
		}
	}
	return false;
}

void removeNode(int nodeToremove, int X[], nodeAndDist adjList [NUM_OF_NODES][50]){

	for (int i=0; i < remainingNodes(X); i++){
		if(X[i] != 0){
			nodeAndDist* currentSock = &adjList [ X[i] - 1 ][0];

			for(int j=1; j < lenLinkedList(currentSock); j++) {
				if(currentSock[j].node == nodeToremove) {
					currentSock[j].distance=0;
					currentSock[j].node=0;
				}
			}
		}
	}
}

int remainingNodes(int X[]){
	int nodeCounter = 0;
	for(int i=0; i<201; i++){
		if(X[i]!=0){
			nodeCounter = nodeCounter + 1;
		}
		else{
			continue;
		}
	}
	return nodeCounter;
}

int lenLinkedList(nodeAndDist* currentSock[]){
	int maxPos = 0;
	for(int i=0; i<50; i++){
		if(currentSock[i]!=0){
			maxPos = i + 1;
		}
		else{
			continue;
		}
	}
	return maxPos;
}

char *readLine(FILE *file) {

    if (file == NULL) {
        printf("Error: file pointer is null.");
        exit(1);
    }

    int maximumLineLength = 128;
    char *lineBuffer = (char *)malloc(sizeof(char) * maximumLineLength);

    if (lineBuffer == NULL) {
        printf("Error allocating memory for line buffer.");
        exit(1);
    }

    char ch = getc(file);
    int count = 0;

    while ((ch != '\n') && (ch != EOF)) {
        if (count == maximumLineLength) {
            maximumLineLength += 128;
            lineBuffer = realloc(lineBuffer, maximumLineLength);
            if (lineBuffer == NULL) {
                printf("Error reallocating space for line buffer.");
                exit(1);
            }
        }
        lineBuffer[count] = ch;
        count++;

        ch = getc(file);
    }

    lineBuffer[count] = '\0';
    //char line[count + 1];
    char* line = (char *)malloc(sizeof(char) * (count + 1) );
    strncpy(line, lineBuffer, (count + 1));
    free(lineBuffer);
    char *constLine = line;
    return constLine;
}

void printShortestDistances(int* shortestDistances, int size)
{
	printf("Shortest distances with respect to node 1 follow.\n\n");

	int startNode = 1;
	for (int node = startNode; node < size; node++)
	{
		printf("Node #: %d \t Distance: %d\n", node, shortestDistances[node]);
	}

	printf("\nAll distances calculated. Done.");
}
