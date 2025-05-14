#pragma once
#include "BookIterator.h"
#include <vector>

class ConcreteBookIterator : public BookIterator {
private:
    const std::vector<Book*>& books;
    size_t index;
public:
    ConcreteBookIterator(const std::vector<Book*>& books);
    bool hasNext() override;
    Book* next() override;
};
