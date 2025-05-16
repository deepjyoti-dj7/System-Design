#include "../include/Directory.h"
#include "../include/Visitor.h"

Directory::Directory(const std::string& name) : name(name) {}

Directory::~Directory() {
    for (auto child : children) {
        delete child;
    }
}

std::string Directory::getName() const {
    return name;
}

void Directory::add(Element* element) {
    children.push_back(element);
}

void Directory::accept(Visitor* visitor) {
    visitor->visit(this);
    for (auto child : children) {
        child->accept(visitor);
    }
}
