#pragma once
#include "BookCollection.h"
#include "ConcreteBookIterator.h"
#include <vector>

class Library : public BookCollection {
private:
    std::vector<Book*> books;
public:
    void addBook(Book* book) override;
    BookIterator* createIterator() override;
    ~Library();
};
