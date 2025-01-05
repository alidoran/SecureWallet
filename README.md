```
MvvmKmpCleanArchitecture
├── **core**                       // :core module (shared resources, domain, and data layers)
│   ├── **data**                   // Shared data layer for app-wide logic
│   │   ├── **local**              // Local data sources module
│   │   │   ├── dao                // Data Access Objects
│   │   │   ├── entities           // Database entities
│   │   │   └── database           // Room database setup
│   │   ├── **remote**             // Remote data sources module
│   │   │   ├── api                // API service interfaces
│   │   │   ├── dto                // Data Transfer Objects
│   │   │   └── network            // Networking setup (e.g., Retrofit)
│   │   ├── **repository**         // Repository module for shared logic
│   │   │   └── repository         // Shared repositories (e.g., AuthRepository)
│   │   └── **mapper**             // Shared mappers (DTO to domain models)
│   │
│   ├── **domain**                 // Shared domain layer for app-wide business logic
│   │   ├── model                  // Shared domain models (e.g., User, Product)
│   │   ├── result                 // Data and Domain result
│   │   ├── repository             // Shared repository interfaces
│   │   └── usecase                // Shared use cases (e.g., AuthenticateUserUseCase)
│   │
│   └── **ui**                     // Base UI
│   │   ├── state                  // Shared UIs states
│   │   └── theme                  // Themes, colors, typography
│
├── **features**   
│   ├── **feature1**               // :feature1 module (e.g., Checkout)
│   │   ├── data                   // Feature-specific data logic
│   │   │   ├── repository         // Feature-specific repository
│   │   │   └── model              // Feature-specific data models
│   │   ├── domain                 // Feature-specific domain logic
│   │   │   ├── model              // Feature-specific domain models
│   │   │   ├── repository         // Feature-specific repository interface
│   │   │   └── usecase            // Feature-specific use cases (e.g., ValidateOrderUseCase)
│   │   ├── ui                     // Feature-specific UI components
│   │   │   ├── screen             // Composables for feature screens (e.g., CheckoutScreen)
│   │   │   ├── component          // Reusable UI components specific to the feature
│   │   │   ├── navigation         // Feature-specific navigation setup
│   │   │   └── state              // Feature states
│   │   ├── viewmodel              // Feature-specific ViewModel
│   │   └── state                  // UI state management (e.g., sealed classes)
│   │
│   ├── **feature2**               // :feature2 module (e.g., Dashboard)
│   │   ├── The same structure as feature1
│
├── **composeApp**                 // Shared Compose code (cross-platform UI)
│   ├── src
│   │   ├── **androidMain**        // Android-specific implementations
│   │   ├── **commonMain**         // Shared code
│   │   │   └── ui                 // Global UI components and navigation (commonMain)
│   │   │       ├── screen         // App-wide screens (e.g., SplashScreen, OnboardingScreen)
│   │   │       ├── component      // Reusable app-wide components (e.g., AppBar, LoadingIndicator)
│   │   │       ├── navigation     // App-level navigation setup (e.g., root navigation graph)
│   │   │       └── state          // App-level state
│   │   ├── **desktopMain**         // Desktop-specific implementations
│   │   └── **iosMain**             // iOS-specific implementations
│   ├── build.gradle.kts        // Compose App build configuration
│
├── **di**                         // Dependency Injection for shared resources
│   ├── DataModule                 // Shared data dependencies (e.g., Retrofit, API services, repositories)
│   ├── DomainModule               // Shared domain dependencies (e.g., use cases, domain models)
│   └── NetworkModule              // Network setup (optional, if you want to separate network DI further)
│
├── **foundation**                 // :foundation module (shared logic and utilities)
│   ├── utils                      // Shared utility classes (e.g., extensions, constants)
│   ├── network                    // Network error handling, base network functionality
│   ├── baseviewmodel              // Base ViewModel classes for consistent state management
│   └── baserepository             // Base repository classes for common logic (e.g., data fetching, error handling)
│
├── iosApp                      // iOS entry point
│   └── iosApp.swift            // iOS app entry point
│
└── settings.gradle.kts         // Gradle settings configuration
```