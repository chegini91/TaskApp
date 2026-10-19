# TaskApp — MVVM Teaching Example

A minimal Android app demonstrating the MVVM architecture pattern.
Built with Kotlin + Jetpack Compose. No Room, no Retrofit — pure in-memory data.

## Project structure

```
app/src/main/java/com/example/taskapp/
├── model/
│   ├── Task.kt               ← data class (pure Kotlin, no Android)
│   └── TaskRepository.kt     ← owns data, exposes StateFlow<List<Task>>
├── viewmodel/
│   └── TaskViewModel.kt      ← bridge between UI and Repository
├── ui/
│   └── TaskScreen.kt         ← Composables: TaskScreen + TaskRow
└── MainActivity.kt           ← shell: setContent { TaskScreen() }
```

## How to open

1. Clone or unzip this project.
2. Open in **Android Studio Hedgehog or newer**.
3. Let Gradle sync finish.
4. Run on an emulator or device (minSdk 26 / Android 8.0).

### If Gradle sync fails with a bare Java version (e.g. "25.0.3")

This project uses Gradle 8.9, which needs **JDK 17–21**. Newer Android Studio
versions default to a newer bundled JDK. Fix: *Settings → Build, Execution,
Deployment → Build Tools → Gradle → Gradle JDK* → choose a JDK 21 (e.g. `jbr-21`).

## How data flows

```
User taps "Add"
  → viewModel.addTask(input)          (event goes up)
    → repository.add(title)
      → _tasks.value += Task(...)          (StateFlow emits a new list)
        → tasks by viewModel.tasks.collectAsStateWithLifecycle()
          → LazyColumn recomposes            (UI updates)
```

## Rotation test

Rotate the device or emulator. Your tasks must still be there.
If they disappear, state is in the wrong place (Composable instead of ViewModel).

## What changes when Room arrives (DB1)

Only `TaskRepository.kt` changes internally:
- the in-memory `MutableStateFlow` → Room DAO
- `add()`, `delete()`, `toggleDone()` → DAO calls
- `tasks` StateFlow → `dao.getAll()` returns `Flow<List<Task>>` directly

`TaskViewModel`, `TaskScreen`, and `MainActivity` require **zero changes**.
That is the payoff of the architecture.

## Dependencies (see gradle/libs.versions.toml)

- `androidx.lifecycle:lifecycle-viewmodel-compose` — `viewModel()` in Composables
- `androidx.lifecycle:lifecycle-runtime-compose` — `collectAsStateWithLifecycle()`
- `androidx.compose.material3` — UI components
- `Icons.Default.Delete` comes from `material-icons-core`, included with material3
