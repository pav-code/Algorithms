/*
 * Main.cpp
 *
 *  Created on: Oct. 5, 2021
 *      Author: KLIMENTg
 */

#include "../includes/Main.h"

Main::Main() {

}

int main() {
	std::cout << "TSP Begin." << std::endl;

	std::string filename = "";
	if( DEBUG )
	{
		filename = "./data/testData3.txt";
	}
	else
	{
		filename = "./data/data.txt";
	}

	BellmanHeldKarp BHK = BellmanHeldKarp( filename );

	BHK.printMinTourCostFloor();

	return 0;
}

Main::~Main() {
	// TODO Auto-generated destructor stub
}

/*
 * CartesianCoordinates ok(X,Y) != CartesianCoordinates ok = new CartesianCoordinates(X,Y) (c++ vs java)
 */

