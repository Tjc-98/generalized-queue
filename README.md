# generalized-queue

A generic queue that allows removal of an element at any position in Java.

---

## About

Written in Java, this project implements a generalized queue backed by a doubly linked circular list. Elements are inserted at the back and can be removed from any position using a 1-based index. The queue state is printed after each operation, and an error message is shown when the index is out of range or the queue is empty.

## Usage

Run the program to execute the built-in test cases. The output shows the queue state after each operation in the format `[x],[y],[z]`.

## Getting Started

### Prerequisites

- Java 21 or later
- Maven

### Building

**Unix / Windows**
```
mvn compile
```

### Running

**Unix / Windows**
```
mvn exec:java -Dexec.mainClass="Main"
```

---

MIT License - see [LICENSE](LICENSE)
