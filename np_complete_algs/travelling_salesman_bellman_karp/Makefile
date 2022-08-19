CC = g++

TARGET = TravellingSaleman

BUILD_DIRECTORY = Makebuild

CFLAGS = -g -Wall

all: $(TARGET)
	
$(TARGET): Main.o BellmanHeldKarp.o CartesianCoordinates.o Subsets.o
	$(CC) $(CFLAGS) -o $(TARGET) $(BUILD_DIRECTORY)/Main.o $(BUILD_DIRECTORY)/BellmanHeldKarp.o $(BUILD_DIRECTORY)/CartesianCoordinates.o $(BUILD_DIRECTORY)/Subsets.o

Main.o: src/Main.cpp includes/Main.h includes/BellmanHeldKarp.h
	$(CC) $(CFLAGS) -o $(BUILD_DIRECTORY)/Main.o -c src/Main.cpp
   
BellmanHeldKarp.o: includes/BellmanHeldKarp.h includes/Subsets.h includes/CartesianCoordinates.h
	$(CC) $(CFLAGS) -o $(BUILD_DIRECTORY)/BellmanHeldKarp.o -c src/BellmanHeldKarp.cpp

CartesianCoordinates.o: includes/CartesianCoordinates.h
	$(CC) $(CFLAGS) -o $(BUILD_DIRECTORY)/CartesianCoordinates.o -c src/CartesianCoordinates.cpp

Subsets.o: includes/Subsets.h
	$(CC) $(CFLAGS) -o $(BUILD_DIRECTORY)/Subsets.o -c src/Subsets.cpp
	
clean:
	$(RM) $(TARGET)
	
# Notes:

# target:  dependency1 dependency2 ... dependencyn
#      <tab> command

# a build command to build myprogram executable from myprogram.o and mylib.lib
# all:myprogram.o mylib.o
#        gcc –o myprogram myprogram.o mylib.o


