#include "../include/DataParser.h"

void DataParser::parseData() {
    readData();
    processData();
    writeData();
}

void DataParser::writeData() {
    std::cout << "Writing data to output file" << std::endl;
}
