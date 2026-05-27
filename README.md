An educational Android project focused on implementing the MVP architecture pattern and local SQLite persistent storage using Room Database.

# MoojiFood 🍔

This repository is a native Android project developed 3 years ago, specifically designed to practice, implement, and showcase core software engineering patterns and local persistence in Kotlin. Rather than focusing on complex UI features, the primary goal of this project was to establish a solid, decoupled architecture and safe database operations.

---

<div align="center">
  <video src="https://github.com/mooji-m/MoojiFood/assets/124574973/45c28128-14e0-4065-9368-3bd1b1731b5a
" width="280" controls>
    Your browser does not support the video tag.
  </video>
</div>

---

## 🏗️ Architectural Blueprint & Implementation

The core value of this codebase lies in its clean separation of concerns and structural implementations:

- **MVP (Model-View-Presenter) Pattern:** Built to decouple business logic from Android UI controllers. The implementation relies on strict contracts (`MainScreenContract`) ensuring that the Activity (`View`) only handles rendering, while the `Presenter` orchestrates database transactions.
- **Robust Database Abstraction (Room):** Features a generic abstraction layer utilizing an abstract `BaseDao<T>` interface. This ensures clean code reusability for standard SQLite operations (Insert, Update, Delete) before expanding into specialized queries within `FoodDao`.
- **Memory-Conscious Main Thread Operations:** Uses configured builders (`allowMainThreadQueries`) for structural prototyping, combined with dynamic adapter updates (`notifyItemInserted`, `notifyItemRemoved`) to sync the UI smoothly with the local data state.
- **Kotlin Language Features:** Leverages custom Context Extension Functions (`Context.showToast`) and safe ViewBinding implementations to avoid memory leaks and boilerplate code.

---

## 🛠️ Tech Stack Specs

- **Language:** 100% Kotlin with `JavaVersion.VERSION_1_8` compatibility.
- **Persistence:** Room Database (SQLite Object Mapping).
- **UI Architecture:** ViewBinding, Custom Material Dialogs, and dynamic `RecyclerView.Adapter` state management.
- **Image Handling:** `Glide` framework integrated with `glide-transformations` for programmatic image processing (Rounded Corners).

---

## 📂 Codebase Structure

The project maps clean packaging standard guidelines:
- **`room/` & `model/`**: Houses the abstract relational database structure (`MyDatabase`), data entity definitions (`Food`), and data access objects (`FoodDao`).
- **`mainScreen/`**: Contains the presentation layer mechanics, interface agreements, and UI list adapters (`FoodAdapter`).
- **`util/`**: Centralized extension utilities and global constants.

---

## 🚀 How to Run

1. Clone this repository:
   ```bash
   git clone [https://github.com/mooji-m/MoojiFood.git](https://github.com/mooji-m/MoojiFood.git

2. Open the project inside Android Studio.

3. Sync Gradle files, build, and run the application on your emulator or physical device.

📝 Developer Note
Note on Project Scope: This repository serves as a snapshot of my technical timeline and foundational journey into Android architecture. It is intentionally kept simple in terms of design and scope to highlight the underlying engineering mechanics: decoupling logic via MVP, structural data routing, and safe CRUD operations. Left open-source as an honest architectural reference point.
