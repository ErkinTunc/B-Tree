# Project Roadmap

This roadmap shows how the project is evolving from an academic B-Tree implementation into a more complete Java indexing and storage-engine project.

The focus is not only on adding features, but on demonstrating **correctness, maintainability, measurable performance, and database/storage concepts**.

## Current Foundation

The project currently includes:

- In-memory B-Tree indexing
- Key-value insertion
- Exact search
- Range queries
- Case-insensitive and accent-insensitive prefix search
- Leaf and internal node splitting
- Duplicate-key replacement
- `minKey` / `maxKey` based range pruning
- JUnit 5 regression tests
- Gradle-based build and test workflow

The current engineering focus is to keep the implementation stable while improving internal structure and test coverage.

---

## Near-Term Milestones

### 1. Correctness & Regression Safety

Strengthen the automated test suite around:

- Split behavior
- Deep-tree searches
- Range queries across multiple leaves
- Duplicate-key handling
- Structural B-Tree invariants
- `minKey` / `maxKey` consistency

**Why it matters:** demonstrates disciplined debugging, regression testing, and algorithmic correctness.

### 2. Architecture Refactoring

Refactor the current implementation into focused components such as:

```text
ArbreB
Noeud
SplitResult
BTreeSearch
BTreePrinter
KeyNormalizer
```

**Why it matters:** improves maintainability and demonstrates separation of concerns in a non-trivial data-structure implementation.

### 3. Real Dataset Integration

Move dataset loading into a dedicated ingestion layer and expose useful tree statistics:

- Record count
- Tree height
- Node count
- Leaf count
- Fill ratio
- Build duration

**Why it matters:** connects the data structure to realistic indexing workloads.

---

## Performance Milestone

Benchmark the B-Tree against linear search on progressively larger datasets.

Planned measurements include:

- Insert throughput
- Exact lookup latency
- Range-query latency
- Prefix-query latency
- Memory usage where relevant

JMH is planned for repeatable Java benchmarks.

**Why it matters:** turns performance claims into measurable evidence.

---

## Persistence Milestone

The major evolution of the project is moving from an in-memory index toward a persistent storage engine.

Planned work includes:

```text
Page
PageId
PageManager
DiskStorage
NodeSerializer
```

The goal is to support:

- Saving the index
- Reloading it after application restart
- Page-based disk storage
- Child references through page identifiers
- Documented on-disk layout

**Why it matters:** introduces storage-engine fundamentals such as serialization, page management, and disk-oriented indexing.

---

## Developer Experience & Delivery

Planned repository improvements include:

- GitHub Actions
- Automated Gradle tests on push and pull requests
- JavaDoc for public APIs
- Spotless or Checkstyle
- Tagged releases
- Benchmark documentation
- Architecture and design-decision documentation

Suggested release progression:

```text
v0.1  Baseline implementation
v0.2  Refactored in-memory index
v0.3  Benchmarked index
v1.0  Persistent page-based index
```

---

## Long-Term Extensions

Once the core engine is stable:

- Configurable node order
- Binary search inside nodes
- Bulk loading
- Deletion
- LRU page cache
- Concurrent readers
- Crash-safety concepts / WAL
- Visualization
- Optional REST interface

These are intentionally secondary to correctness, performance, and persistence.

---

## Target Outcome

The intended final result is a **Java B-Tree storage-engine project** that demonstrates:

- Database indexing fundamentals
- Data-structure and algorithm design
- Regression and invariant testing
- Refactoring and maintainable architecture
- Performance benchmarking
- Real dataset ingestion
- Persistent page-based storage
- Professional Java build and CI workflows

The project is designed to remain focused on **indexing and storage internals**, rather than becoming a generic CRUD application.
