# PlanuraApp

```
com.example.notasapp
├─ data
│   ├─ local
│   │   ├─ NoteEntity.kt
│   │   ├─ NoteDao.kt
│   │   └─ AppDatabase.kt
│   ├─ remote
│   │   └─ FirebaseSource.kt
│   └─ repository
│       └─ NoteRepositoryImpl.kt
├─ domain
│   ├─ model
│   │   └─ Note.kt
│   ├─ repository
│   │   └─ INoteRepository.kt
│   └─ usecase
│       ├─ GetNotes.kt
│       ├─ SaveNote.kt
│       ├─ DeleteNote.kt
│       └─ SyncNotes.kt
├─ di
│   └─ AppModule.kt
├─ presentation
│   ├─ ui
│   │   ├─ NotesScreen.kt
│   │   └─ NoteEditorScreen.kt
│   └─ viewmodel
│       └─ NotesViewModel.kt
└─ util
└─ Mappers.kt
```