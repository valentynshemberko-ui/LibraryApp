# Library Management System - Android App

A comprehensive Android application built with Kotlin that simulates a library management system. This project demonstrates key Android development concepts, including user authentication, persistent data storage, and interactive UI components.

##Key Features

###Authentication & Session Management
* **Splash Screen:** Initial loading screen with a professional UI and 2-second delay.
* **User Registration:** Detailed form with real-time validation:
    * Mandatory fields check.
    * Improved Email validation (checks for '@' and domain dot).
    * Password length and matching confirmation.
    * Date of Birth selection via `DatePickerDialog`.
* **Secure Login:** Authorization check using `SharedPreferences`.
* **Session Persistence:** Remembers user login state across app restarts.

###Content Management
* **Book Inventory (CRUD):** * Interactive list using `RecyclerView`.
    * Add/Edit books via custom `AlertDialog` inputs.
    * Dual deletion logic: **Delete Button** and modern **Swipe-to-Delete** gesture.
* **Authors Information:** Secondary list with detailed pop-ups describing authors.

###User Profile & UX
* **Profile Settings:** Update name, email, and birth date easily.
* **Profile Photo:** Change avatar using **Camera** or **Gallery** selection.
* **Logout Safety:** Confirmation dialog to prevent accidental logouts.
* **Custom App Icon:** Professional library-themed launcher icon.

##Tech Stack
* **Language:** Kotlin
* **UI:** XML Layouts (ConstraintLayout, LinearLayout, ScrollView)
* **Storage:** SharedPreferences
* **Gesture Support:** ItemTouchHelper (Swipe gestures)

---
*Developed as part of Android Development coursework.*