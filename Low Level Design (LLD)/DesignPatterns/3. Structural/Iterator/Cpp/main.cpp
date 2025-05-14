#include <iostream>
#include "./include/Library.h"

int main() {
    Library library;
    library.addBook(new Book("Design Patterns"));
    library.addBook(new Book("Effective C++"));
    library.addBook(new Book("Clean Code"));

    BookIterator* iterator = library.createIterator();

    while (iterator->hasNext()) {
        Book* book = iterator->next();
        std::cout << "Book: " << book->getTitle() << std::endl;
    }

    delete iterator;
    return 0;
}
