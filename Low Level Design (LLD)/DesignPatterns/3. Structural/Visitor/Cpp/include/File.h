#pragma once
#include "Element.h"
#include <string>

class File : public Element {
private:
    std::string name;
public:
    File(const std::string& name);
    std::string getName() const;
    void accept(Visitor* visitor) override;
};
