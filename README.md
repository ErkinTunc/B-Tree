# B-Tree Indexing Engine in Java

This project implements a **B-Tree based indexing engine in Java** for
efficient storage and retrieval of large key-value datasets.

Originally developed as part of an Advanced Databases assignment, the
project has been extended into a practical indexing engine capable of
handling real datasets and supporting advanced search operations.

The system demonstrates how databases use B-Tree structures to provide
fast and scalable data access.

---

## Project Goals

The project demonstrates:

- Implementation of a B-Tree index structure
- Efficient key-value storage
- Logarithmic search performance
- Interval and prefix queries
- Indexing of large datasets
- Foundations of database indexing systems

The goal is to simulate how database engines organize and query indexed
data efficiently.

---

## Repository Structure

    B-TREE/
    │
    ├── data/
    │   └── communes.txt          Dataset used for indexing demo
    │
    ├── docs/
    │   └── tp1.pdf               Original academic assignment
    │
    ├── src/main/java/
    │   ├── arbreb/
    │   │   └── ArbreB.java       B-Tree implementation
    │   │
    │   └── app/
    │       ├── Main.java         Program entry point
    │       └── ArbreBTests.java  Demo & test scenarios
    │
    ├── run.bat                   Windows launcher
    ├── run.sh                    Linux/macOS launcher
    ├── project_roadmap.md        Development roadmap
    └── README.md

---

## Features

Current implementation supports:

- Key-value insertion
- Key search
- Interval search
- Prefix search
- Automatic node splitting
- Balanced tree maintenance
- Dataset indexing demo
- Tree structure visualization in console

The B-Tree remains balanced automatically, ensuring efficient operations
even for large datasets.

---

## Running the Project

### Windows

Run one of the demo modes:

    .\run.bat simple

or

    .\run.bat communes

---

### Linux / macOS

    ./run.sh simple

or

    ./run.sh communes

---

## Demo Modes

### Simple Demo

    .\run.bat simple

Demonstrates step-by-step insertion into the B-Tree with console
visualization of node splits and tree structure evolution.

Also performs an interval search example.

---

### Dataset Demo

    .\run.bat communes

Loads \~35k French municipalities and builds a B-Tree index.

Outputs:

- dataset size
- index build time
- lookup performance
- prefix search results

Example:

    === B-Tree Index Report ===
    Records indexed: 34980
    Build time: 173 ms
    Lookup time: ~0 ms
    Prefix matches: 1282 results

---

## Example Usage in Code

```java
ArbreB tree = new ArbreB();

tree.ajouter("Paris", "city");
tree.ajouter("Lyon", "city");

String value = tree.recherche("Paris");
System.out.println(value);
```

---

## Dataset

The demo uses:

    data/communes.txt

containing French municipalities, allowing realistic indexing tests.

---

## Learning Outcomes

This project illustrates:

- Balanced tree behavior
- Node splitting mechanics
- Efficient data indexing
- Prefix and interval query design
- Practical database indexing concepts

---

## Future Improvements

Planned upgrades include:

- Interactive CLI query interface
- Disk-based node storage
- Performance benchmarking tools
- Unit tests
- Tree visualization improvements
- Persistent indexes between runs

---

## CV Project Description Example

> Implemented a B-tree based indexing engine in Java to efficiently
> index and query large datasets, supporting prefix and interval
> searches with logarithmic-time performance.

---

## License

Released under the MIT License.
