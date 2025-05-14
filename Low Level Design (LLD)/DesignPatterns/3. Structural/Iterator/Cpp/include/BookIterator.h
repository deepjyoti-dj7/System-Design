#pragma once
#include "Book.h"

class BookIterator {
public:
    virtual bool hasNext() = 0;
    virtual Book* next() = 0;
    virtual ~BookIterator() = default;
};
