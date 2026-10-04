# Design Decisions

This document summarizes the main engineering decisions behind the B-Tree project and the trade-offs they introduce.

The goal is to make the implementation choices understandable without requiring a full code review.

## 1. B-Tree as the Core Index Structure

The project uses a B-Tree-style index rather than a standard Java collection because the objective is to study and implement database-indexing behavior directly.

This makes the project useful for exploring:

- ordered key storage
- logarithmic-style tree traversal
- node splitting
- internal separator keys
- range-query pruning
- future page-based persistence

The current version is in-memory. Persistent storage is a planned milestone, not a completed feature.

## 2. Separate Leaf Insertion from Internal Child Routing

A key design decision was to separate two operations that initially shared the same helper:

```text
positionInsertion(...)
positionEnfant(...)
```

They look similar, but their equality rules are different.

### Leaf insertion

For a leaf, the implementation needs the first position where:

```text
existingKey >= newKey
```

This allows an existing key to be detected and its value replaced.

### Internal-node routing

For an internal node, equality must continue to the right child.

After a leaf split, the promoted separator is the first key of the right leaf. Therefore, searching for a key equal to that separator must route to the right subtree.

This separation fixed duplicate-key handling without breaking searches after node splits.

**Engineering value:** one helper no longer carries two subtly different responsibilities.

## 3. Duplicate Keys Replace Values

The index treats keys as logically unique.

Inserting:

```text
Paris -> 75
Paris -> 75000
```

results in one logical record:

```text
Paris -> 75000
```

A dedicated regression test protects this behavior.

This keeps the public behavior closer to a key-value index than a multi-map.

## 4. `minKey` / `maxKey` Metadata for Range Pruning

Each subtree stores range metadata:

```text
minKey
maxKey
```

Range queries can skip a subtree when its entire key range lies outside the requested interval.

Conceptually:

```text
if subtree.maxKey < query.min
or subtree.minKey > query.max
    skip subtree
```

This reduces unnecessary traversal.

### Trade-off

The optimization is only correct if the metadata is updated whenever the subtree changes.

A regression test was added after discovering that a child insertion without a split could leave parent metadata stale, causing valid results to be pruned.

**Engineering lesson:** optimization metadata becomes part of the correctness contract, not just a performance detail.

## 5. Prefix Search Normalizes Keys at Comparison Time

Prefix search is designed to be:

- case-insensitive
- accent-insensitive

The implementation normalizes both the query prefix and stored keys before comparison.

For example:

```text
E
e
é
```

can match the same normalized prefix.

The original key is preserved for storage and output.

### Why prefix pruning is currently disabled

The tree is ordered by original strings, while prefix matching uses normalized strings.

Because those two orderings are not guaranteed to be identical, using the existing `minKey` / `maxKey` pruning logic for normalized prefix search could skip valid matches.

The current implementation therefore favors correctness and traverses the relevant tree structure without that optimization.

**Trade-off:** simpler correctness now, optimization later.

## 6. Regression-First Refactoring

The project is being refactored incrementally rather than rewritten from scratch.

The workflow is:

```text
capture behavior
    ↓
reproduce bugs
    ↓
add regression tests
    ↓
apply a small fix
    ↓
run the full test suite
    ↓
refactor structure
```

This approach was used for both duplicate-key behavior and stale range metadata.

The purpose is to make architecture changes measurable and reversible.

## 7. Keep the Public API Stable During Internal Refactoring

The current refactoring plan separates responsibilities into components such as:

```text
ArbreB
Noeud
SplitResult
BTreeSearch
BTreePrinter
KeyNormalizer
```

The intention is to improve internal cohesion without unnecessarily breaking existing callers.

Public API cleanup can happen separately and deliberately after behavior is stable.

## 8. Dataset Logic Should Not Live Inside the Tree

Dataset loading and parsing are planned to move into dedicated components rather than remaining mixed with demo code.

Planned direction:

```text
DatasetLoader
CommuneRecord
CommuneParser
```

This keeps the B-Tree focused on indexing responsibilities and makes dataset ingestion independently testable.

## 9. Benchmark Before Claiming Performance

The project does not treat theoretical complexity as a substitute for measurement.

Planned benchmarks will compare:

```text
linear scan
vs
B-Tree lookup
```

across progressively larger datasets.

Metrics will include:

- insert throughput
- exact lookup latency
- range-query latency
- prefix-query latency

JMH is planned for repeatable Java benchmarking.

No performance improvement will be presented as a project result until it has been measured.

## 10. Page-Based Persistence Is a Planned Architectural Upgrade

The current implementation stores nodes as Java objects in memory.

The planned storage-engine phase introduces concepts such as:

```text
Page
PageId
PageManager
DiskStorage
NodeSerializer
```

The goal is to move from object references toward page identifiers and a documented on-disk representation.

This is intentionally postponed until the in-memory implementation is well-tested and structurally clean.

**Reason:** persistence should build on a stable index, not hide correctness problems underneath storage complexity.

## Known Trade-offs and Current Limitations

The current version intentionally accepts several limitations:

- storage is in-memory
- keys and values are currently string-based
- node order is fixed by the current implementation
- prefix search does not yet use normalized-order pruning
- page-based persistence is not yet implemented
- structural invariant testing is still being expanded

These are tracked as engineering milestones rather than presented as completed capabilities.

## What These Decisions Demonstrate

The project is intended to show more than a working data structure.

The design decisions emphasize:

- algorithmic reasoning
- debugging through regression tests
- separation of responsibilities
- correctness before optimization
- explicit trade-offs
- measurable performance
- a path from an academic implementation toward storage-engine concepts
