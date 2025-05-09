# Compiler and flags
CXX = g++
CXXFLAGS = -std=c++17 -Wall

# Directories
SRC_DIR = src
OBJ_DIR = obj
EXEC = main

# Get all the source files
SRC = $(wildcard $(SRC_DIR)/*.cpp) main.cpp

# Convert source files to object files (using the OBJ_DIR)
OBJ = $(SRC:$(SRC_DIR)/%.cpp=$(OBJ_DIR)/%.o)

# Default target: create the executable
all: $(EXEC)

# Linking the object files to create the executable
$(EXEC): $(OBJ)
	$(CXX) $(OBJ) -o $(EXEC)

# Rule to create the obj directory if it doesn't exist
$(OBJ_DIR):
	mkdir -p $(OBJ_DIR)

# Rule to compile each .cpp file to .o
$(OBJ_DIR)/%.o: $(SRC_DIR)/%.cpp | $(OBJ_DIR)
	$(CXX) $(CXXFLAGS) -c $< -o $@

# Clean up generated files
clean:
	rm -rf $(OBJ_DIR) $(EXEC)

# Phony targets (these aren't actual files)
.PHONY: all clean
