# Compiler and flags
CXX = g++
CXXFLAGS = -std=c++17 -Wall

# Directories and files
SRC = main.cpp $(wildcard */src/*.cpp)

# Object files folder
OBJ_DIR = obj

# Convert .cpp paths to .o paths under obj/
OBJ = $(patsubst %.cpp, $(OBJ_DIR)/%.o, $(SRC))

# Default target
all: main

# Link
main: $(OBJ)
	$(CXX) $(CXXFLAGS) $(OBJ) -o main

# Compile .cpp to .o, create directories as needed
$(OBJ_DIR)/%.o: %.cpp
	@mkdir -p $(dir $@)
	$(CXX) $(CXXFLAGS) -c $< -o $@

# Clean target
clean:
	rm -rf $(OBJ_DIR) main

.PHONY: all clean
