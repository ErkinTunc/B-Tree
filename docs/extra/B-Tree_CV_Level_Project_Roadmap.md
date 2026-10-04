# B-Tree Project Roadmap — CV-Level Database / Storage Engine Project

## Objective

Transform the current academic B-tree implementation into a professional, CV-ready project that demonstrates:

- Data indexing
- Efficient data retrieval
- Database fundamentals
- Software engineering practices
- Performance engineering
- Persistence and storage concepts
- Testing and regression debugging
- Clean architecture and maintainability

The final project should resemble a minimal indexing/storage engine rather than a simple data structure assignment.

---

## Phase 0 — Correctness Baseline

### Goal

Stabilize behavior before deeper refactoring.

### Tasks

- Keep all baseline JUnit tests.
- Fix the stale `minKey/maxKey` propagation bug.
- Preserve the already-fixed duplicate key replacement behavior.
- Ensure:
  - insertion works
  - exact search works
  - range search works
  - prefix search works
  - leaf split works
  - internal split works
- Remove obsolete code only after tests confirm it is unused.

### Exit Criteria

- `./gradlew test` or `./gradlew.bat test`
- All current tests pass.
- No known regression remains untracked.

### CV Value

Regression testing, debugging, invariant reasoning, test-driven refactoring.

---

## Phase 1 — Core Architecture Refactoring

### Goal

Break the monolithic `ArbreB.java` into focused components.

### Target Structure

```text
arbreb/
├── ArbreB.java
├── Noeud.java
├── SplitResult.java
├── BTreeSearch.java
├── BTreePrinter.java
└── KeyNormalizer.java
```

### Responsibilities

```text
ArbreB
├── public API
├── insertion orchestration
└── root ownership

Noeud
├── keys
├── values / children
├── size
└── range metadata

SplitResult
└── promotedKey + rightNode

BTreeSearch
├── exact search
├── range search
└── prefix search

BTreePrinter
└── console/debug representation

KeyNormalizer
└── case/accent normalization
```

### Rule

After every small extraction, run the full test suite.

### Exit Criteria

- Behavior unchanged.
- All tests remain green.
- Public API remains stable or intentionally versioned.

### CV Value

Separation of concerns, refactoring discipline, maintainable architecture.

---

## Phase 2 — Professional Test Suite

### Goal

Move from basic unit coverage to a serious regression/invariant suite.

### Add Tests For

- Multiple leaf splits
- Multiple internal splits
- Search after deep splits
- Interval across several leaves
- Inclusive interval boundaries
- Empty interval result
- Prefix search across several leaves
- Duplicate replacement after splits
- Randomized insertion order
- 1k–10k key correctness smoke tests

### Structural Invariants

Verify automatically:

```text
keys ordered
node capacity respected
children count valid
all leaves same depth
minKey/maxKey consistent
```

### Exit Criteria

- No `println`-based validation.
- Core invariants automatically checked.
- Regression failures are reproducible.

### CV Value

JUnit 5, regression testing, property/invariant reasoning.

---

## Phase 3 — Public API Cleanup

### Goal

Expose a small, consistent, professional API.

### Suggested API

```java
insert(String key, String value)
search(String key)
rangeSearch(String from, String to)
prefixSearch(String prefix)
size()
```

Optional compatibility wrappers may preserve the current French API temporarily.

### Optional Advanced Step

Only if fully understood and tested:

```java
public final class BTree<K extends Comparable<K>, V>
```

Do not introduce generics only for appearance.

### Exit Criteria

- Small API surface.
- Consistent naming.
- Public methods documented.

---

## Phase 4 — Dataset Layer

### Goal

Separate dataset ingestion from demo/test code.

### Target Structure

```text
dataset/
├── DatasetLoader.java
├── CommuneRecord.java
└── CommuneParser.java
```

### Features

- Load `communes.txt`
- Bulk import
- Deterministic parsing
- Report:
  - record count
  - build duration
  - tree height
  - node count
  - leaf count
  - fill ratio

### Suggested Stats Component

```text
BTreeStats
├── size
├── height
├── nodeCount
├── leafCount
└── fillRatio
```

### CV Value

Data ingestion pipeline + indexing.

---

## Phase 5 — Benchmarking

### Goal

Demonstrate measurable performance instead of making unverified claims.

### Compare

```text
Linear scan
vs
B-tree exact lookup
```

### Measure

- 1k records
- 10k records
- 35k records
- 100k+ records

### Metrics

- Insert throughput
- Exact lookup latency
- Range query latency
- Prefix query latency
- Memory usage where meaningful

### Preferred Tool

Use JMH for serious Java microbenchmarks.

### README Output Example

```text
Dataset | Linear | B-tree | Speedup
10k     | ...    | ...    | ...
35k     | ...    | ...    | ...
100k    | ...    | ...    | ...
```

### CV Value

Performance engineering and evidence-based optimization.

---

## Phase 6 — Persistence

### Goal

Turn the in-memory index into a persistent indexing engine.

### Phase 6A — Minimum Persistence

Implement:

```text
save tree
load tree
```

Use a documented binary or structured file format.

### Phase 6B — Page-Based Storage

Introduce:

```text
Page
PageId
PageManager
DiskStorage
NodeSerializer
```

Possible design:

```text
4 KB page
page header
key count
keys
values / child page IDs
```

Use page IDs instead of direct object references where appropriate.

### Exit Criteria

```text
program restart
-> index reload
-> same queries return same results
```

### CV Value

Storage-engine fundamentals, file I/O, serialization, page-based persistence.

---

## Phase 7 — CLI

### Goal

Provide a clean demonstration interface after the engine is technically solid.

### Suggested Commands

```text
btree load data/communes.txt
btree search Paris
btree range Lyon Paris
btree prefix ch
btree stats
btree benchmark
```

Deletion is optional and should not block persistence/benchmark work.

### CV Value

Usability and demonstrability.

---

## Phase 8 — CI and Engineering Quality

### Goal

Make repository quality visible and repeatable.

### Add

- GitHub Actions
- `./gradlew clean test` on push / pull request
- JavaDoc for public API
- Spotless or Checkstyle
- Tagged releases
- Clean Git history
- Small, purpose-driven commits

### Suggested Releases

```text
v0.1 baseline
v0.2 refactored in-memory engine
v0.3 benchmarked engine
v1.0 persistent index
```

### CV Value

Professional software engineering workflow.

---

## Phase 9 — README and Architecture Documentation

### README Structure

```text
# Java B-Tree Indexing Engine

What it does
Why I built it
Architecture
Algorithms
Complexity
Dataset
Benchmarks
Persistence format
How to run
How to test
Engineering decisions
Known limitations
```

### Architecture Diagram

```text
CLI
 |
 v
BTree API
 |-------------------|
 v                   v
Insertion         Search
 |                   |
 v                   v
Nodes ---------- Range metadata
 |
 v
PageManager
 |
 v
Disk
```

### Design Decisions Document

Create `docs/design-decisions.md` covering:

- Why B-tree?
- Why separator equality goes right?
- Why `minKey/maxKey` metadata?
- Why fixed-size pages?
- Why not `TreeMap`?
- Why separate insertion position from child routing?

### CV Value

Technical communication and design reasoning.

---

## Phase 10 — Optional Stretch Features

Only after the core project is strong:

- Deletion
- Generic keys/values
- Binary search inside nodes
- Configurable node order
- LRU page cache
- Concurrent readers
- REST API
- Visualization
- Crash recovery / WAL

Avoid turning the project into a generic CRUD application. Preserve the storage/indexing focus.

---

# Priority Order

```text
NOW
 ↓
1. Fix stale minKey/maxKey
 ↓
2. All tests green
 ↓
3. Split ArbreB responsibilities
 ↓
4. Structural invariant tests
 ↓
5. Dataset abstraction + stats
 ↓
6. Benchmarks
 ↓
7. Persistence
 ↓
8. Page-based storage
 ↓
9. CLI
 ↓
10. CI + README + architecture docs
 ↓
CV-ready v1.0
```

---

# CV Readiness Levels

## Tier A — CV-Ready

- Clean Gradle project
- JUnit regression suite
- Insertion/search/range/prefix
- Split correctness
- Clean architecture
- Real dataset
- Benchmarks
- README
- GitHub Actions

Suitable for internship / junior software engineering applications.

## Tier B — Strong CV Project

Tier A plus:

- Persistence
- Page-based disk storage
- Serialization
- Reload between executions
- Storage statistics
- Benchmarked persistent/in-memory behavior

This clearly separates the project from a standard Java data structure assignment.

## Tier C — Advanced Storage/Data Engineering Project

Tier B plus:

- Page cache
- Configurable page size
- Bulk loading
- Deletion
- Crash-safety concepts
- Larger benchmark suite

At this level, the project begins to resemble a small storage-engine study.

---

# CV Description

## Current / In-Memory Version

> **Java B-Tree Index** — Implemented and regression-tested an in-memory B-tree index supporting exact, range, and accent-insensitive prefix queries; debugged split routing and duplicate-key handling using JUnit 5.

## Persistent Version

Use only after persistence is actually implemented:

> **Java B-Tree Storage Engine** — Designed a page-based B-tree indexing engine in Java with persistent storage, range/prefix queries, regression testing, and benchmarked lookup performance on real datasets.

---

# Project Success Metrics

## Correctness

- All normal/regression/invariant tests green.
- No known bug remains undocumented.

## Engineering

- Responsibilities separated.
- CI enabled.
- Clean commit history.
- Public API documented.

## Evidence

- Real dataset.
- Reproducible benchmarks.
- Persistent reload demo.
