cmake_minimum_required(VERSION 3.14)
project(SplitwiseApp)

set(CMAKE_CXX_STANDARD 17)
set(CMAKE_CXX_STANDARD_REQUIRED ON)

# Collect all source files
file(GLOB_RECURSE SOURCES
    "main.cpp"
    "model/*.cpp"
    "service/*.cpp"
)

add_executable(SplitwiseApp ${SOURCES})
    