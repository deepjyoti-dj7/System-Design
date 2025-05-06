# Composition in C++

## Introduction

Composition is a fundamental principle of object-oriented programming (OOP) in C++. It allows objects to be constructed using other objects, promoting code reuse, flexibility, and better maintainability. Unlike inheritance, which models an "is-a" relationship, composition models a "has-a" relationship.

## What is Composition?

Composition is a design principle where one class contains instances of other classes as fields. These contained classes are often called **components**, and the class that contains them is the **composite class**. This allows for the creation of complex objects by combining simpler, reusable objects.

### Example: A Computer and its Components

Consider a `Computer` class that has several components like a `Processor`, `RAM`, and `Storage`. Instead of inheriting from these components, a `Computer` object **contains** instances of `Processor`, `RAM`, and `Storage` as fields.

```cpp
#include <iostream>
#include <string>
using namespace std;

class Processor {
private:
    string model;

public:
    Processor(string model) {
        this->model = model;
    }

    void performTask() {
        cout << model << " processor is performing a task." << endl;
    }
};

class RAM {
private:
    int size;

public:
    RAM(int size) {
        this->size = size;
    }

    void loadData() {
        cout << "Loading data into " << size << "GB RAM." << endl;
    }
};

class Storage {
private:
    string type;

public:
    Storage(string type) {
        this->type = type;
    }

    void storeData() {
        cout << "Storing data on " << type << " storage." << endl;
    }
};

class Computer {
private:
    Processor processor;
    RAM ram;
    Storage storage;

public:
    Computer(Processor processor, RAM ram, Storage storage)
        : processor(processor), ram(ram), storage(storage) {}

    void run() {
        processor.performTask();
        ram.loadData();
        storage.storeData();
        cout << "Computer is running!" << endl;
    }
};

int main() {
    Processor processor("Intel i7");
    RAM ram(16);
    Storage storage("SSD");

    Computer computer(processor, ram, storage);
    computer.run();

    return 0;
}
```

### Output:

```
Intel i7 processor is performing a task.
Loading data into 16GB RAM.
Storing data on SSD storage.
Computer is running!
```

---

## Why Prefer Composition Over Inheritance?

### 1. **Encapsulation and Flexibility**

- Composition allows for **dynamic behavior changes**. For instance, you can swap out components like `Processor`, `RAM`, and `Storage` at runtime without modifying the core `Computer` class.
- Inheritance creates rigid class hierarchies, which are harder to modify and extend without breaking existing code.

### 2. **Better Code Reusability**

- Composition encourages **code reuse** because individual components (like `Processor`, `RAM`, `Storage`) can be reused across different classes (e.g., `Laptop`, `Desktop`, `Server`) without the need for duplication.

### 3. **Avoids Inheritance Pitfalls**

- Inheritance can lead to deep and complex class hierarchies, which are harder to maintain.
- Composition avoids this by **delegating** responsibilities to objects, rather than enforcing an "is-a" relationship between classes.

### 4. **Supports Interface-Based Design**

- Composition works well with interfaces (in C++, abstract classes), allowing you to design loosely coupled systems. This promotes flexibility and decoupling between components.

---

## Composition with Interfaces (Abstract Classes in C++)

Using abstract classes (interfaces) with composition provides more flexibility and decoupling between the components and the composite class.

```cpp
#include <iostream>
#include <string>
using namespace std;

class Engine {
public:
    virtual void start() = 0; // Pure virtual function
    virtual ~Engine() {}
};

class ElectricEngine : public Engine {
public:
    void start() override {
        cout << "Electric Engine started silently." << endl;
    }
};

class HybridEngine : public Engine {
public:
    void start() override {
        cout << "Hybrid Engine started with both electric and gasoline power." << endl;
    }
};

class Vehicle {
private:
    Engine* engine; // Composition with an interface (abstract class)

public:
    Vehicle(Engine* engine) : engine(engine) {}

    void startVehicle() {
        engine->start();
        cout << "Vehicle is ready to go!" << endl;
    }
};

int main() {
    ElectricEngine electricEngine;
    HybridEngine hybridEngine;

    Vehicle electricCar(&electricEngine);
    electricCar.startVehicle();

    Vehicle hybridCar(&hybridEngine);
    hybridCar.startVehicle();

    return 0;
}
```

### Output:

```
Electric Engine started silently.
Vehicle is ready to go!
Hybrid Engine started with both electric and gasoline power.
Vehicle is ready to go!
```

In this example, the `Vehicle` class uses composition to include an `Engine` (abstract class) as a field. By using different `Engine` implementations (e.g., `ElectricEngine`, `HybridEngine`), we can change the behavior of the `Vehicle` dynamically.

---

## When to Use Composition?

- **Complex Objects**: When building complex objects that consist of multiple components, such as `Computer`, `Car`, or `House`.
- **Code Reusability**: When you want to reuse components across different classes without enforcing rigid inheritance hierarchies.
- **Dynamic Behavior**: When different behaviors need to be swapped dynamically, like using different types of engines in a vehicle.
- **Loose Coupling**: When you prefer to decouple your classes and avoid deep inheritance chains. Composition supports the **"favor composition over inheritance"** principle.

This approach offers greater flexibility, reuse, and maintainability compared to inheritance, especially when building complex systems composed of multiple independent components.
