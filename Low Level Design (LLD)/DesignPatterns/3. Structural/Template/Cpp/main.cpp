#include "include/CSVDataParser.h"
#include "include/XMLDataParser.h"

int main() {
    std::cout << "---- Parsing CSV ----" << std::endl;
    CSVDataParser csv;
    csv.parseData();

    std::cout << std::endl;

    std::cout << "---- Parsing XML ----" << std::endl;
    XMLDataParser xml;
    xml.parseData();

    return 0;
}
