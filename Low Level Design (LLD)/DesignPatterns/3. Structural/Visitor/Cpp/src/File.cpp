#include "../include/File.h"
#include "../include/Visitor.h"

File::File(const std::string& name) : name(name) {}

std::string File::getName() const {
    return name;
}

void File::accept(Visitor* visitor) {
    visitor->visit(this);
}
