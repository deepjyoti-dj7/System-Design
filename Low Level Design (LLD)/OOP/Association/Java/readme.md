# Association in Object-Oriented Programming (OOP)

## Introduction

Association is a fundamental concept in object-oriented programming (OOP) that defines a relationship between two or more objects. It represents how objects interact with each other while maintaining their independence.

Association is **not inheritance**—rather, it is a relationship between objects that allows communication while ensuring they remain loosely coupled.

## What is Association?

Association defines a connection between two classes, where one class is linked to another. The association can be **one-to-one**, **one-to-many**, **many-to-one**, or **many-to-many**. Objects in an association can exist independently of each other.

### Key Characteristics of Association:

- Represents a **uses-a** or **knows-a** relationship.
- Objects in an association **can exist independently**.
- Can be **unidirectional** or **bidirectional**.
- Promotes **modularity** and **code reusability**.

---

### Example: A Doctor and Patients (Java)

```java
import java.util.*;

class Patient {
    private String name;

    public Patient(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

class Doctor {
    private String name;
    private List<Patient> patients;

    public Doctor(String name) {
        this.name = name;
        this.patients = new ArrayList<>();
    }

    public void addPatient(Patient p) {
        patients.add(p);
    }

    public void showPatients() {
        System.out.println(name + " treats:");
        for (Patient p : patients) {
            System.out.println(" - " + p.getName());
        }
    }
}

public class AssociationExample {
    public static void main(String[] args) {
        Patient p1 = new Patient("Alice");
        Patient p2 = new Patient("Bob");

        Doctor d1 = new Doctor("Dr. House");
        d1.addPatient(p1);
        d1.addPatient(p2);

        d1.showPatients();
    }
}
```

### Output:

```
Dr. House treats:
 - Alice
 - Bob
```

---

## Types of Association

### 1. **One-to-One Association**

- Each object of class A is associated with one object of class B.
- Example: A `Passport` belongs to one `Citizen`.

### 2. **One-to-Many Association**

- One object of class A can be associated with multiple objects of class B.
- Example: A `Library` has many `Books`.

### 3. **Many-to-One Association**

- Multiple objects of class A can be associated with one object of class B.
- Example: Many `Employees` work in one `Company`.

### 4. **Many-to-Many Association**

- Multiple objects of class A can be associated with multiple objects of class B.
- Example: `Doctors` and `Patients`.

---

## Why Use Association?

### 1. **Promotes Code Reusability**

- Objects can be reused across multiple associations without duplication.

### 2. **Encourages Loose Coupling**

- Objects interact without depending on the internal implementation of each other.

### 3. **Improves Maintainability**

- Changing one object does not heavily impact others, making code easier to manage.

### 4. **Better System Design**

- Allows modeling of real-world relationships between entities effectively.

---

## Association vs Aggregation vs Composition

| Feature             | Association              | Aggregation                                  | Composition                                             |
| ------------------- | ------------------------ | -------------------------------------------- | ------------------------------------------------------- |
| Relationship        | "Knows-a"                | "Has-a"                                      | "Has-a"                                                 |
| Object Independence | Objects are independent  | Contained object **can exist independently** | Contained object **cannot exist without** the container |
| Lifetime            | Objects exist separately | Contained object **outlives** the container  | Contained object **is destroyed** with the container    |
| Example             | Doctor and Patient       | University and Professor                     | Car and Engine                                          |

---

## Bidirectional Association

### Example: Course and Student (Java)

```java
import java.util.*;

class Student {
    private String name;

    public Student(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

class Course {
    private String title;
    private List<Student> students;

    public Course(String title) {
        this.title = title;
        this.students = new ArrayList<>();
    }

    public void addStudent(Student s) {
        students.add(s);
    }

    public void showStudents() {
        System.out.println("Students in " + title + ":");
        for (Student s : students) {
            System.out.println(" - " + s.getName());
        }
    }

    public String getTitle() {
        return title;
    }
}

public class BidirectionalAssociationExample {
    public static void main(String[] args) {
        Course course = new Course("Java Programming");
        Student s1 = new Student("John");
        Student s2 = new Student("Emily");

        course.addStudent(s1);
        course.addStudent(s2);

        course.showStudents();
    }
}
```

### Output:

```
Students in Java Programming:
 - John
 - Emily
```

---

## Final Thoughts

Association is vital for building relationships between classes in object-oriented systems. By understanding and applying associations properly, you can design flexible, maintainable, and modular software architectures.
