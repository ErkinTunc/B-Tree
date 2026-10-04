# B-Tree Indexing Engine in Java

A Java implementation of an **in-memory B-Tree indexing engine** for efficient
storage and retrieval of key-value datasets.

Originally developed for an Advanced Databases course, the project is being
refactored into a more complete indexing/storage-engine study with automated
testing, benchmarking, and persistent storage as future milestones.

## Features

- Key-value insertion
- Exact search
- Range queries
- Case-insensitive prefix search
- Accent-insensitive prefix search
- Leaf and internal node splitting
- Duplicate-key replacement
- `minKey` / `maxKey` based range pruning
- JUnit 5 regression tests
- Gradle build system

## Project Goals

The project focuses on practical concepts used by database indexing systems:

- B-Tree algorithms
- Data indexing and retrieval
- Tree balancing and node splitting
- Query optimization
- Regression testing
- Performance engineering
- Storage-engine fundamentals

## Documentation

More technical details are available here:

- [Architecture](docs/architecture.md)
- [Testing Strategy](docs/testing.md)
- [Project Roadmap](docs/roadmap.md)
- [Design Decisions](docs/design-decisions.md)

## How to run
Run with the run task, which assembles and executes the application
1. > ./gradlew run
  
   
   Runs the simple B-Tree insertion and search demo
   - > ./gradlew run --args="simple"
     
   Runs the B-Tree demo using the communes dataset  
   - > ./gradlew run --args="communes"


Builds the project and runs the tests

2. > ./gradlew build
3. > ./gradlew clean

  Runs the test task for all subprojects when invoked from the root project
- > ./gradlew test
   
   To see the details of the tests on the browser.
  - > build/reports/tests/test/index.html

    

## Testing

The project uses **JUnit 5** to protect insertion, search, split, range-query,
prefix-search and regression behavior during refactoring.

See [Testing Strategy](docs/testing.md) for the full test plan.

## Roadmap

Current development focuses on:

**Correctness → Refactoring → Invariant Tests → Dataset Integration → Benchmarks → Persistence**

See the [full roadmap](docs/roadmap.md).

## Tech Stack

`Java` · `Gradle` · `JUnit 5` · `Git`

## Current Status

The current version is an **in-memory B-Tree index**.

Persistent page-based storage and performance benchmarking are planned future
milestones and will only be documented as completed once implemented.
