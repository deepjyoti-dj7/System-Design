#pragma once
#include <string>

class Book {
private:
    std::string title;
public:
    Book(const std::string& title);
    std::string getTitle() const;
};
