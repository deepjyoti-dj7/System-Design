#pragma once

class File;
class Directory;

class Visitor {
public:
    virtual void visit(File* file) = 0;
    virtual void visit(Directory* directory) = 0;
    virtual ~Visitor() = default;
};
