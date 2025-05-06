# Aggregation in C++

## Introduction

Aggregation is a key concept in object-oriented programming (OOP) that represents a "has-a" relationship between two classes, but with a crucial distinction: the lifecycle of the contained object is independent of the container object. This means that while one class contains another, the contained object can exist independently of the container.

Aggregation allows for better modularity, code reuse, and maintainability. It is different from composition, where the contained object cannot exist without the container.

---

## What is Aggregation?

Aggregation is a form of association in OOP where an object of one class contains a reference to an object of another class. However, the contained object can exist independently of the container. This means that even if the container object is destroyed, the contained object can still be used elsewhere in the application.

### Key Characteristics of Aggregation:

- Represents a **has-a** relationship.
- The contained object **can exist independently** of the container.
- Implemented using references or pointers to objects.
- Promotes **loose coupling** between objects.

### Example: A Library and its Books

```cpp
#include <iostream>
#include <vector>
#include <string>
using namespace std;

class Book {
    string title;
    string author;
public:
    Book(string t, string a) : title(t), author(a) {}
    void displayInfo() const {
        cout << "Book: " << title << " by " << author << endl;
    }
};

class Library {
    string libraryName;
    vector<Book*> books;
public:
    Library(string name) : libraryName(name) {}

    void addBook(Book* book) {
        books.push_back(book);
    }

    void showBooks() const {
        cout << "Books in " << libraryName << ":" << endl;
        for (const auto& book : books) {
            book->displayInfo();
        }
    }
};

int main() {
    Book* b1 = new Book("1984", "George Orwell");
    Book* b2 = new Book("To Kill a Mockingbird", "Harper Lee");

    Library lib("City Central Library");
    lib.addBook(b1);
    lib.addBook(b2);

    lib.showBooks();

    // Books can still be used independently
    b1->displayInfo();

    delete b1;
    delete b2;

    return 0;
}
```

### Output:

```
Books in City Central Library:
Book: 1984 by George Orwell
Book: To Kill a Mockingbird by Harper Lee
Book: 1984 by George Orwell
```

---

## Aggregation vs Composition

| Feature      | Aggregation                                  | Composition                                             |
| ------------ | -------------------------------------------- | ------------------------------------------------------- |
| Relationship | "Has-a"                                      | "Has-a"                                                 |
| Ownership    | Contained object **can exist independently** | Contained object **cannot exist without** the container |
| Lifetime     | Contained object **outlives** the container  | Contained object **is destroyed** with the container    |
| Example      | Library and Books                            | Car and Engine                                          |

---

## Why Use Aggregation?

### 1. **Promotes Code Reusability**

- Aggregated objects can be used in multiple places without being tightly coupled to a single container class.

### 2. **Encourages Loose Coupling**

- Aggregation allows objects to interact without being dependent on the lifecycle of each other.

### 3. **Better Maintainability**

- Changes in one class do not heavily impact the other, making the codebase easier to modify and extend.

### 4. **Real-World Applicability**

- Many real-world relationships, such as a school and its teachers, a company and its employees, naturally fit the aggregation model.

---

## Aggregation with Abstract Classes

Using abstract classes, we can further enhance the flexibility of aggregation.

```cpp
#include <iostream>
#include <vector>
#include <string>
using namespace std;

// Abstract class
class Transportable {
public:
    virtual void transport() const = 0;
    virtual ~Transportable() {}
};

class Package : public Transportable {
    string id;
public:
    Package(string i) : id(i) {}
    void transport() const override {
        cout << "Transporting package ID: " << id << endl;
    }
};

class DeliveryService {
    string serviceName;
    vector<Transportable*> packages;
public:
    DeliveryService(string name) : serviceName(name) {}

    void addPackage(Transportable* pkg) {
        packages.push_back(pkg);
    }

    void deliverAll() const {
        cout << "Delivering packages via " << serviceName << ":" << endl;
        for (const auto& pkg : packages) {
            pkg->transport();
        }
    }
};

int main() {
    Package* p1 = new Package("PKG001");
    Package* p2 = new Package("PKG002");

    DeliveryService dhl("DHL");
    dhl.addPackage(p1);
    dhl.addPackage(p2);

    dhl.deliverAll();

    delete p1;
    delete p2;

    return 0;
}
```

### Output:

```
Delivering packages via DHL:
Transporting package ID: PKG001
Transporting package ID: PKG002
```

---

## When to Use Aggregation?

- When an object **can exist independently** from the container.
- When designing **loosely coupled** systems.
- When different objects need to be **shared** across multiple containers.
- When following **SOLID principles**, particularly the **Dependency Inversion Principle (DIP)**.
