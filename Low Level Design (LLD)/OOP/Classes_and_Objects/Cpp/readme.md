# Classes and Objects in C++

Classes and objects are key concepts in Object-Oriented Programming (OOP), and they help define the structure and behavior of data.

## What is a Class?

A **class** in C++ is a blueprint for creating objects. It defines the attributes (data members) and behaviors (member functions or methods) that the objects will have.

### Defining a Class in C++

To define a class in C++, you use the `class` keyword, followed by the class name. Here's an example of defining a class called `Book`:

```cpp
#include <iostream>
#include <string>
using namespace std;

class Book {
    // Attributes (data members)
private:
    string title;
    string author;
    int yearPublished;
    double price;

public:
    // Constructor to initialize the object
    Book(string t, string a, int y, double p) {
        title = t;
        author = a;
        yearPublished = y;
        price = p;
    }

    // Method to display book details
    void displayDetails() {
        cout << "Book Title: " << title << endl;
        cout << "Book Author: " << author << endl;
        cout << "Year Published: " << yearPublished << endl;
        cout << "Price: $" << price << endl;
    }
};
```

- **Attributes**: The `Book` class has four attributes: `title`, `author`, `yearPublished`, and `price`. These describe the characteristics of a book.
- **Constructor**: The constructor `Book(string t, string a, int y, double p)` is used to initialize the book's attributes with values when an object is created.
- **Methods**: The `displayDetails` method prints out the details of the book.

## What is an Object?

An **object** is an instance of a class. When you create an object, you instantiate the class, and the object holds its own data. Each object can call the methods defined in the class.

### Creating Objects in C++

To create an object in C++, you use the class name followed by the object name. Here's an example of creating objects from the `Book` class:

```cpp
int main() {
    // Creating objects of the Book class
    Book book1("To Kill a Mockingbird", "Harper Lee", 1960, 14.99);
    Book book2("1984", "George Orwell", 1949, 12.99);

    // Displaying details of each book
    book1.displayDetails();
    cout << "----------------------" << endl;
    book2.displayDetails();

    return 0;
}
```

1. **Instantiation**: The `Book` objects (`book1`, `book2`) are created by calling the constructor with specific values.
2. **Initialization**: The constructor `Book(string t, string a, int y, double p)` initializes the object with the provided values.
3. **Reference**: Each object is referenced by its name (`book1`, `book2`), and we can access its attributes and methods through these references.

### Key Points:

- **Attributes (Data Members)**: These are the characteristics of an object. For the `Book` class, they are `title`, `author`, `yearPublished`, and `price`.
- **Constructor**: A special method used to initialize the object with values when it is created.
- **Methods (Member Functions)**: These are the behaviors or actions that can be performed on an object. The `displayDetails` method shows the details of the book in this example.
- **Object Creation**: An object is created by calling the constructor with the necessary values.
