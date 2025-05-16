#pragma once
#include "Element.h"
#include <vector>
#include <string>

class Directory : public Element {
private:
    std::string name;
    std::vector<Element*> children;
public:
    Directory(const std::string& name);
    ~Directory();
    std::string getName() const;
    void add(Element* element);
    void accept(Visitor* visitor) override;
};
