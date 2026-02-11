package ca.uwaterloo.cs446.notes.model.service

import ca.uwaterloo.cs446.notes.model.Note
import kotlinx.coroutines.flow.Flow

interface StorageService {
    val notes: Flow<List<Note>>
    suspend fun createNote(note: Note)
    suspend fun readNote(noteId: String): Note?
    suspend fun updateNote(note: Note)
    suspend fun deleteNote(noteId: String)
}
