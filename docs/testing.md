# Testing Strategy

This document describes how the B-Tree implementation is tested before and during refactoring.

The main goal is to protect existing behavior, reproduce known bugs, and prevent structural regressions while the project evolves from an academic implementation into a more maintainable indexing engine.

## Test Philosophy

The project follows a **regression-first refactoring approach**:

1. Capture current behavior with automated tests.
2. Reproduce known bugs with dedicated regression tests.
3. Apply the smallest possible fix.
4. Run the full test suite after each meaningful change.
5. Add structural invariant tests before larger architectural changes.

Tests rely on **JUnit 5 assertions**, not manual inspection of console output.

## Current Automated Coverage

### Insertion

Current tests cover:

- Simple key-value insertion
- Missing-key lookup
- Unsorted insertions
- Leaf-node splitting
- Internal-node splitting
- Duplicate-key replacement

### Search

Current tests cover:

- Exact search
- Empty-tree search
- Basic range search
- Basic prefix search
- Case-insensitive prefix matching

### Regression Scenarios

Dedicated regression tests are used for previously identified failure modes:

- **Duplicate key replacement**  
  Re-inserting an existing key should replace its value instead of creating a second logical record.

- **Stale `minKey` / `maxKey` metadata**  
  A key inserted into a child without causing a split must still remain reachable by range queries. This protects against incorrect subtree pruning caused by outdated metadata.

These tests are kept even after a bug is fixed so the same regression cannot silently return.

## Planned Structural Invariant Tests

Behavioral tests are not enough for a tree-based index. The next testing layer will validate internal B-Tree invariants such as:

- Keys remain ordered inside every node
- Node capacity limits are respected
- Internal-node child ordering is consistent with separator keys
- All leaves remain at the expected depth
- Splits do not lose keys or values
- `minKey` and `maxKey` match the real subtree range

Some of these checks require controlled access to internal structure, so they will be added after the architecture is refactored without exposing implementation details unnecessarily.

## Planned Dataset & Integration Tests

Integration tests will use deterministic datasets separately from unit tests.

Planned scenarios include:

- Loading a small fixed dataset
- Verifying known lookups from `communes.txt`
- Comparing range-query results with independently computed expected results
- Testing prefix queries with accented and non-accented names

Loading a dataset successfully is **not** considered a test by itself; integration tests must contain assertions.

## Running the Tests

Run the full test suite with Gradle:

```bash
./gradlew test
```

On Windows:

```powershell
.\gradlew.bat test
```

Detailed HTML reports are generated at:

```text
build/reports/tests/test/index.html
```

## Refactoring Rule

After every small refactoring step:

```text
change
  ↓
run tests
  ↓
compare failures
  ↓
continue only if behavior is understood
```

A refactoring is considered safe only when expected behavior remains protected by the test suite and any known failure is explicitly documented.

## Next Testing Milestones

- Add multiple leaf/internal split scenarios
- Add search-after-deep-split tests
- Add multi-leaf range queries
- Add inclusive and empty-range cases
- Add prefix queries across several leaves
- Add randomized insertion-order tests
- Add 1k–10k key correctness smoke tests
- Add structural invariant validation

The long-term goal is to make correctness measurable before introducing benchmarking and persistent storage.
