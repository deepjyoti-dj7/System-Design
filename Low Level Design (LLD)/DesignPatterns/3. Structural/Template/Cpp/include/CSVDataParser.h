#pragma once
#include "DataParser.h"

class CSVDataParser : public DataParser {
protected:
    void readData() override;
    void processData() override;
};
