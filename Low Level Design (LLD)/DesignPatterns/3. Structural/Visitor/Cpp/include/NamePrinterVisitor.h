#pragma once
#include "Visitor.h"
#include <iostream>

class NamePrinterVisitor : public Visitor {
public:
    void visit(File* file) override;
    void visit(Directory* directory) override;
};
