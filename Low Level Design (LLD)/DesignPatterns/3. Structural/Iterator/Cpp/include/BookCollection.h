#pragma once
#include "BookIterator.h"
#include "Book.h"

class BookCollection {
public:
    virtual void addBook(Book* book) = 0;
    virtual BookIterator* createIterator() = 0;
    virtual ~BookCollection() = default;
};
