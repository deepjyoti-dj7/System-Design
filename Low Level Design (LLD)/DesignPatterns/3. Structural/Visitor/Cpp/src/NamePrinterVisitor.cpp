#include "../include/NamePrinterVisitor.h"
#include "../include/File.h"
#include "../include/Directory.h"

void NamePrinterVisitor::visit(File* file) {
    std::cout << "File: " << file->getName() << std::endl;
}

void NamePrinterVisitor::visit(Directory* directory) {
    std::cout << "Directory: " << directory->getName() << std::endl;
}
