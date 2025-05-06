# Abstraction in C++

## Introduction

**Abstraction** is one of the four fundamental principles of Object-Oriented Programming (OOP). It allows you to hide **implementation details** while exposing only the necessary parts of an object. This helps in reducing complexity and increasing maintainability.

Abstraction in C++ is mainly achieved using:

1. **Abstract Classes**
2. **Interfaces (Pure Virtual Functions)**

---

## What is Abstraction?

**Abstraction** means showing only the **essential details** and hiding the **implementation**. It allows programmers to focus on **what an object does** rather than **how it does it**.

### Key Benefits of Abstraction

- **Reduces complexity**: Hides unnecessary implementation details.
- **Increases code reusability**: Encourages the reuse of abstracted logic.
- **Enhances security**: Protects internal object details from unintended modifications.
- **Improves maintainability**: Makes code easier to manage and update.

---

## 1. Abstraction Using Abstract Classes

An **abstract class** in C++ is a class that **cannot be instantiated**. It is used to define common behavior that multiple subclasses should implement.

### Example: Abstract Class in C++

```cpp
#include <iostream>
using namespace std;

// Abstract class
class Appliance {
protected:
    string name;
public:
    Appliance(string n) : name(n) {}
    virtual void turnOn() = 0; // Pure virtual function
    void displayName() {
        cout << "Appliance: " << name << endl;
    }
};

// Subclass implementing the abstract method
class Refrigerator : public Appliance {
public:
    Refrigerator(string n) : Appliance(n) {}
    void turnOn() override {
        cout << "Refrigerator is now cooling..." << endl;
    }
};

int main() {
    Appliance* fridge = new Refrigerator("LG Double Door");
    fridge->displayName();
    fridge->turnOn();
    delete fridge;
    return 0;
}
```

### Output

```
Appliance: LG Double Door
Refrigerator is now cooling...
```

---

## 2. Abstraction Using Interfaces (Pure Virtual Functions)

An **interface** in C++ is created using a class that contains **only pure virtual functions**.

### Example: Interface in C++

```cpp
#include <iostream>
using namespace std;

// Defining an interface
class Drawable {
public:
    virtual void draw() = 0; // Pure virtual function
};

// Implementing the interface in Circle class
class Circle : public Drawable {
public:
    void draw() override {
        cout << "Drawing a Circle." << endl;
    }
};

// Implementing the interface in Rectangle class
class Rectangle : public Drawable {
public:
    void draw() override {
        cout << "Drawing a Rectangle." << endl;
    }
};

int main() {
    Drawable* shape1 = new Circle();
    shape1->draw();

    Drawable* shape2 = new Rectangle();
    shape2->draw();

    delete shape1;
    delete shape2;
    return 0;
}
```

### Output

```
Drawing a Circle.
Drawing a Rectangle.
```

---

## Abstract Class vs Interface: Key Differences

| Feature              | Abstract Class                              | Interface (Pure Virtual Functions) |
| -------------------- | ------------------------------------------- | ---------------------------------- |
| Methods              | Can have abstract and concrete methods      | Only pure virtual methods          |
| Fields               | Can have member variables                   | Should not have data members       |
| Constructor          | Can have constructors                       | Cannot have constructors           |
| Multiple Inheritance | Not recommended                             | Supported                          |
| Access Modifiers     | Can have private, protected, public members | Methods are public by default      |

---

## Real-World Example: Notification System

Abstraction is widely used in real-world applications, such as notification handling.

### Example: Notification System with Abstraction

```cpp
#include <iostream>
using namespace std;

// Abstract class for Notification
class Notification {
protected:
    string recipient;
public:
    Notification(string r) : recipient(r) {}
    virtual void send() = 0; // Abstract method
};

// Implementing notification types
class EmailNotification : public Notification {
public:
    EmailNotification(string r) : Notification(r) {}
    void send() override {
        cout << "Email sent to " << recipient << endl;
    }
};

class PushNotification : public Notification {
public:
    PushNotification(string r) : Notification(r) {}
    void send() override {
        cout << "Push notification sent to " << recipient << endl;
    }
};

int main() {
    Notification* n1 = new EmailNotification("john@example.com");
    n1->send();

    Notification* n2 = new PushNotification("User123");
    n2->send();

    delete n1;
    delete n2;
    return 0;
}
```

### Output

```
Email sent to john@example.com
Push notification sent to User123
```

---

## Why Use Abstraction in Notification Systems?

- Multiple notification types can be introduced without modifying the system.
- Encourages **open/closed principle**: classes are open for extension but closed for modification.
- Simplifies usage: client code only interacts with the abstraction, not the concrete implementations.

---

## Summary Table

| Concept        | Description                                                 |
| -------------- | ----------------------------------------------------------- |
| Abstraction    | Hides implementation, shows only necessary features         |
| Abstract Class | Partially abstract, can include implemented methods         |
| Interface      | Fully abstract, only pure virtual functions                 |
| Use Case       | When you want different implementations of a common concept |

---

## Final Thoughts

Abstraction is a critical part of designing scalable and maintainable C++ systems. It helps you decouple your components and work at a higher level of thinking, allowing for better design decisions and less tangled code.

---
