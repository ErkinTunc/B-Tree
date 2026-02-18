# B-TREE Indexing Engine in Java

This project implements a **B-Tree based indexing engine** in Java as
part of an Advanced Databases course. It has been extended beyond a
course assignment to demonstrate practical database indexing and
efficient data retrieval on large datasets.

The system indexes municipality data and supports fast search operations
using a balanced tree structure.

---

## Project Goals

The project demonstrates:

- Implementation of a B-Tree index structure
- Efficient key-value storage and retrieval
- Interval and prefix queries
- Indexing of large datasets
- Foundations of database indexing systems

The objective is to simulate how databases use B-Tree structures for
fast data access.

---

## Repository Structure

    B-TREE/
    │
    ├── data/
    │   └── communes.txt          # Dataset used for indexing
    │
    ├── docs/
    │   └── tp1.pdf               # Original assignment description
    │
    ├── src/main/java/arbreb/
    │   └── ArbreB.java           # B-tree implementation
    │
    ├── .gitignore
    ├── LICENSE
    ├── project_roadmap.md        # Development roadmap
    └── README.md

---

## Features

Current implementation supports:

- Key--value insertion
- Search by key
- Interval search
- Prefix search
- Automatic node splitting
- Balanced tree maintenance

The tree structure keeps search operations efficient even with large
datasets.

---

## Example Usage

```java
ArbreB tree = new ArbreB();

tree.ajouter("5", "val5");
tree.ajouter("10", "val10");
tree.ajouter("20", "val20");
tree.ajouter("7", "val7");

System.out.println(tree.recherche("10"));
```

---

## Running the Project

Compile sources and generate `.class` files inside a build directory:

```bash
mkdir -p build
javac -d build src/main/java/arbreb/*.java
```

Run the program:

```bash
java -cp build arbreb.ArbreB    
```

---

## Dataset

The project uses a dataset of French municipalities stored in:

    data/communes.txt

This dataset allows testing indexing performance on realistic data
sizes.

---

## Future Improvements

Planned upgrades:

- Command-line query interface
- Disk-based node storage
- Performance benchmarking
- Unit tests
- Range query optimizations
- Tree visualization

---

## Learning Outcomes

This project illustrates:

- Balanced search tree behavior
- Node splitting mechanics
- Database indexing concepts
- Efficient data retrieval techniques

---

## CV Project Description Example

> Implemented a B-tree based indexing engine in Java to efficiently
> store and query large municipality datasets, supporting
> logarithmic-time search and scalable data indexing.

---

## License

This project is released under the MIT License.
