#include "../include/Library.h"

void Library::addBook(Book* book) {
    books.push_back(book);
}

BookIterator* Library::createIterator() {
    return new ConcreteBookIterator(books);
}

Library::~Library() {
    for (Book* book : books) {
        delete book;
    }
}
