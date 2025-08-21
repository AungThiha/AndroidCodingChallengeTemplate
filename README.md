# Android Coding Challenge Template

## Setup

###  Set JDK Version

Please, use JDK 21 in Android Studio

## Library Modules

There are several reusable library modules: `navigation`, `operation`, `network`, `coroutines` and `design`. While some are already robust, others currently contain only a few classes and are expected to grow as the application grows.

Among these, the `operation` module may seem like an unnecessary abstraction. So, the key benefits of the `operation` module are documented.

### Key Benefits of the `operation` Module

#### 1. First move toward a generic scaffold

This is the first step to building a reusable scaffold that can handle snackbars, dialogs, full-screen errors and content states for all screens. Once complete, common behaviors will be handled automatically. Developers will only need to focus on business logic, UI logic and UI.

#### 2. Eliminates the need for UseCases that just forward calls

For simple CRUD operations, there's no need to create separate UseCase classes that only call a repository method. These operations can be exposed directly from the repository as `Operation` or `SuspendOperation`. This reduces boilerplate and keeps things straightforward through functional programming.

#### 3. Decouples repositories from ViewModels

Repositories expose `Operation` or `SuspendOperation` as return types. Most UseCases are `Operation` or `SuspendOperation` themselves. ViewModels depend only on those types, not on the repositories or UseCases. This makes it easy to swap out implementations without changing the ViewModel.
