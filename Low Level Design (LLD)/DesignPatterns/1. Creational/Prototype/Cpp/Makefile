# Compiler and flags
CXX = g++
CXXFLAGS = -std=c++17 -Wall

# Directories
OBJ_DIR = obj
EXEC = main

# Get all the source files (all .cpp files in the current directory)
SRC = $(wildcard *.cpp)

# Convert source files to object files (using the OBJ_DIR)
OBJ = $(patsubst %.cpp, $(OBJ_DIR)/%.o, $(SRC))

# Default target: create the executable
all: $(EXEC)

# Linking the object files to create the executable
$(EXEC): $(OBJ)
	$(CXX) $(OBJ) -o $(EXEC)

# Rule to create the obj directory if it doesn't exist
$(OBJ_DIR):
	mkdir -p $(OBJ_DIR)

# Rule to compile each .cpp file to .o
$(OBJ_DIR)/%.o: %.cpp | $(OBJ_DIR)
	$(CXX) $(CXXFLAGS) -c $< -o $@

# Clean up generated files
clean:
	rm -rf "$(OBJ_DIR)" "$(EXEC)"

# Phony targets (these aren't actual files)
.PHONY: all clean
