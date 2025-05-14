#include "../include/ConcreteBookIterator.h"

ConcreteBookIterator::ConcreteBookIterator(const std::vector<Book*>& books)
    : books(books), index(0) {}

bool ConcreteBookIterator::hasNext() {
    return index < books.size();
}

Book* ConcreteBookIterator::next() {
    return books[index++];
}
