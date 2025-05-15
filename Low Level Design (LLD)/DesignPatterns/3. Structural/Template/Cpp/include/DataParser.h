#pragma once
#include <iostream>

class DataParser {
public:
    // Template method
    void parseData();

protected:
    virtual void readData() = 0;
    virtual void processData() = 0;

    void writeData(); // common implementation
};
