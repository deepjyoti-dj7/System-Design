# 🟦 Interface Segregation Principle (ISP)

### 🔹 **Definition:**

> **Clients should not be forced to depend on interfaces they do not use.**

This means an interface should have only the methods that are **relevant to the specific client**. It’s better to have **multiple small, specific interfaces** than one large, general-purpose interface.

---

## 🔸 **Why is ISP Important?**

- Promotes **clean and focused interfaces**
- Prevents **unnecessary dependencies**
- Makes **code easier to implement, mock, and test**

---

## ❌ **Bad Example: Fat Interface**

```java
interface Worker {
    void work();
    void eat();
}

class HumanWorker implements Worker {
    public void work() {
        System.out.println("Human working");
    }

    public void eat() {
        System.out.println("Human eating");
    }
}

class RobotWorker implements Worker {
    public void work() {
        System.out.println("Robot working");
    }

    public void eat() {
        // Robots don't eat!
        throw new UnsupportedOperationException("Robots don't eat");
    }
}
```

### 👎 What’s Wrong?

- `RobotWorker` is **forced to implement** `eat()`, which it doesn’t need.
- Violates ISP by including **irrelevant behavior**.

---

## ✅ **Good Example: Split Interfaces**

```java
interface Workable {
    void work();
}

interface Eatable {
    void eat();
}

class HumanWorker implements Workable, Eatable {
    public void work() {
        System.out.println("Human working");
    }

    public void eat() {
        System.out.println("Human eating");
    }
}

class RobotWorker implements Workable {
    public void work() {
        System.out.println("Robot working");
    }
}
```

### 🔧 Usage:

```java
public class Factory {
    public static void main(String[] args) {
        Workable human = new HumanWorker();
        human.work();

        Workable robot = new RobotWorker();
        robot.work();
    }
}
```

---

### 👍 What’s Good?

- `RobotWorker` now **implements only what it needs**.
- Interfaces are **focused**, and each class **depends only on what it uses**.
- Promotes **modular, decoupled design**.

---

## 🔸 **Real-World Analogy:**

Imagine a **remote control** with 20 buttons, but you only ever use 3.
Wouldn’t a **simplified remote** make more sense? That’s ISP.

---
