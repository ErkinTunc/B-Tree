# B-Tree Implementation in Java 🌳

This project is an **educational implementation of a B-Tree** in Java, for the *Advanced Databases* course.  
It demonstrates how to manage **balanced search trees** that support efficient **insertion** and **lookup** operations.

---

## 📌 Features

- Implementation of a **B-Tree** with configurable maximum number of keys (`M`).
- Supports:
  - Key lookup (`recherche`)
  - Key-value insertion (`ajouter`, `ajouterRec`)
  - Automatic node splitting (`splitFeuille`, `splitInterne`)
- Includes **tests** with small and large trees.

---

## 📐 UML Diagram

```

class ArbreB {
  - Noeud racine
  - int M
  
  + ajouter(String cle, String valeur)
  - ajouterRec(Noeud n, String cle, String valeur) : Pair
  + recherche(String cle) : String
}

class Noeud {
  + boolean estFeuille
  + int taille
  + String[] cles
  + String[] valeurs
  + Noeud[] enfants
}

ArbreB "1" *-- "1" Noeud : racine
Noeud "1" *-- "*" Noeud : enfants

```

---

## 🛠️ Classes and Methods

### **`ArbreB`**
Represents the **B-Tree** itself.
- `ajouter(cle, valeur)` → Public method to insert a key-value pair into the tree.
- `ajouterRec(Noeud n, cle, valeur)` → Recursive insertion helper that manages splits.
- `recherche(cle)` → Searches for a key in the tree and returns its associated value.
- `splitFeuille(Noeud n, cle, valeur)` → Splits a full leaf node, promotes the median key.
- `splitInterne(Noeud n)` → Splits a full internal node, promotes the median key.

### **`Noeud`**
Represents both **leaf nodes** and **internal nodes**.
- Attributes:
  - `estFeuille` → true if the node is a leaf.
  - `cles[]` → array of keys stored in the node.
  - `valeurs[]` → (for leaves) associated values.
  - `enfants[]` → (for internal nodes) child pointers.
  - `taille` → number of keys currently in the node.

---

## 🚀 Usage

Compile and run:

```bash
javac ArbreB.java
java ArbreB
```

### Example

```java
ArbreB btree = new ArbreB(3); // M=3
btree.ajouter("5", "val5");
btree.ajouter("10", "val10");
btree.ajouter("20", "val20");
btree.ajouter("7", "val7"); // triggers split

System.out.println(btree.recherche("10")); // → val10
```

---

## 📚 Educational Goal

This project helps understand:
- How **B-Trees** keep data balanced.
- How **splits** propagate upwards.
- How databases implement **index structures** (B-Tree, B+Tree) for fast lookups.

---
