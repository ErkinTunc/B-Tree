# Project Roadmap --- From Academic TP to CV-Level Database Project

## Objective

Transform the current academic B-tree implementation into a
professional, CV-ready project demonstrating skills in:

-   Data indexing
-   Efficient data retrieval
-   Database fundamentals
-   Software engineering practices
-   Performance optimization

The final project should resemble a minimal indexing engine rather than
a simple data structure assignment.

------------------------------------------------------------------------

## Phase 1 --- Repository Cleanup (Foundation)

### Goals

Make the repository professional and maintainable.

### Tasks

-   Adopt standard Java project structure:

        project/
        ├── src/main/java/...
        ├── src/test/java/...
        ├── data/
        ├── docs/
        └── README.md

-   Remove compiled `.class` files from repository.

-   Update `.gitignore`.

-   Add package structure.

-   Separate logic, models, and utilities.

-   Improve code readability and naming.

### Result

Repository becomes understandable and reusable.

------------------------------------------------------------------------

## Phase 2 --- Usable Query Interface

### Goals

Turn the code into a usable indexing tool.

### Tasks

Add CLI commands:

    search <key>
    insert <key>
    delete <key>
    prefix <pattern>
    stats

Example:

    > search Paris
    Found in 3 ms

### Result

Project becomes interactive and demonstrable.

------------------------------------------------------------------------

## Phase 3 --- Dataset Integration

### Goals

Work with realistic data.

### Tasks

-   Use large datasets (municipalities, cities, etc.).
-   Load data into the B-tree.
-   Support bulk import.
-   Support persistence between runs.

### Result

Project handles realistic workloads.

------------------------------------------------------------------------

## Phase 4 --- Performance Benchmarking

### Goals

Prove optimization benefits.

### Tasks

Measure: - Linear search time - B-tree search time - Insert
performance - Memory usage

Output example:

    Dataset size: 100000 records
    Linear search: 850 ms
    B-tree search: 3 ms

### Result

Performance gains become measurable and demonstrable.

------------------------------------------------------------------------

## Phase 5 --- Disk-Based Storage (Major Upgrade)

### Goals

Simulate real database indexing.

### Tasks

-   Store nodes on disk.
-   Load nodes on demand.
-   Implement page-based storage.
-   Reduce memory dependency.

### Result

Project evolves toward a mini indexing engine.

This phase provides the strongest CV value.

------------------------------------------------------------------------

## Phase 6 --- Testing & Reliability

### Goals

Ensure correctness.

### Tasks

Create tests for: - Insert - Delete - Search - Tree balancing - Edge
cases

### Result

Project reliability increases.

------------------------------------------------------------------------

## Phase 7 --- Documentation Upgrade

### Goals

Make project self-explanatory.

README should include: - Problem statement - Architecture overview -
Algorithms used - Dataset description - Performance results - Usage
examples

### Result

Recruiters can understand project quickly.

------------------------------------------------------------------------

## Phase 8 --- Optional Advanced Features

If time allows:

-   Range queries
-   Prefix search optimization
-   Visualization of B-tree
-   REST API interface
-   Concurrent access simulation

------------------------------------------------------------------------

## Suggested Timeline

  Week   Goal
  ------ ------------------------------
  1      Repository cleanup + CLI
  2      Dataset loading + benchmarks
  3      Disk storage implementation
  4      Tests + documentation

------------------------------------------------------------------------

## Final Expected Outcome

The project should demonstrate:

-   Algorithmic understanding
-   Database indexing concepts
-   Performance engineering
-   Clean project organization
-   Real-world usability

------------------------------------------------------------------------

## CV Description Example

> Designed and implemented a B-tree based indexing engine in Java to
> efficiently index and query large datasets, reducing search complexity
> from linear to logarithmic time and supporting persistent storage.

------------------------------------------------------------------------

## Next Step

Start with repository restructuring and CLI integration.
