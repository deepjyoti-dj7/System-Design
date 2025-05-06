# 🟥 Dependency Inversion Principle (DIP)

### 🔹 **Definition:**

> **High-level modules should not depend on low-level modules. Both should depend on abstractions.** > **Abstractions should not depend on details. Details should depend on abstractions.**

---

## 🔸 What Does That Mean?

- High-level components (e.g. business logic) **shouldn't be tightly coupled** to low-level components (e.g. file systems, databases, APIs).
- Both should **communicate through interfaces or abstract classes**.
- Makes the system **flexible, decoupled, and easier to test and modify**.

---

## ❌ **Bad Example: High-Level Module Depends on Low-Level**

```java
class MySQLDatabase {
    public void saveData(String data) {
        System.out.println("Saving to MySQL: " + data);
    }
}

class DataManager {
    private MySQLDatabase database = new MySQLDatabase(); // tightly coupled

    public void store(String data) {
        database.saveData(data);
    }
}
```

### 👎 What’s Wrong?

- `DataManager` **can’t work without** `MySQLDatabase`.
- If you switch to `PostgreSQL`, you need to **modify `DataManager`**, breaking encapsulation.

---

## ✅ **Good Example: Depend on Abstractions**

### Step 1: Define an interface

```java
interface Database {
    void saveData(String data);
}
```

### Step 2: Implement concrete databases

```java
class MySQLDatabase implements Database {
    public void saveData(String data) {
        System.out.println("Saving to MySQL: " + data);
    }
}

class PostgreSQLDatabase implements Database {
    public void saveData(String data) {
        System.out.println("Saving to PostgreSQL: " + data);
    }
}
```

### Step 3: Use dependency injection

```java
class DataManager {
    private Database database;

    public DataManager(Database database) {
        this.database = database;
    }

    public void store(String data) {
        database.saveData(data);
    }
}
```

### 🔧 Usage:

```java
public class Main {
    public static void main(String[] args) {
        Database db = new MySQLDatabase(); // or new PostgreSQLDatabase()
        DataManager manager = new DataManager(db);
        manager.store("Important data");
    }
}
```

---

### 👍 What’s Good?

- `DataManager` works with **any database** that implements `Database`.
- Easy to **swap, test, or extend** without touching business logic.
- Follows **DIP** by depending on **abstractions**, not concrete classes.

---

## 🔸 Real-World Analogy:

Imagine a **USB port** — it doesn’t care what brand of keyboard, mouse, or flash drive you plug in.
It **depends on the interface**, not the implementation.

---
