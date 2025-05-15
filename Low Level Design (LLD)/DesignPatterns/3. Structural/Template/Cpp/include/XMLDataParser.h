#pragma once
#include "DataParser.h"

class XMLDataParser : public DataParser {
protected:
    void readData() override;
    void processData() override;
};
