package ca.uwaterloo.cs446.notes.screens.note

import ca.uwaterloo.cs446.notes.NOTE_DEFAULT_ID
import ca.uwaterloo.cs446.notes.SPLASH_SCREEN
import ca.uwaterloo.cs446.notes.model.Note
import ca.uwaterloo.cs446.notes.model.service.AccountService
import ca.uwaterloo.cs446.notes.model.service.StorageService
import ca.uwaterloo.cs446.notes.screens.NotesAppViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val accountService: AccountService,
    private val storageService: StorageService
) : NotesAppViewModel() {
    // Backing property to avoid state updates from other classes
    private val _note = MutableStateFlow(DEFAULT_NOTE)
    val note: StateFlow<Note> = _note.asStateFlow()

    fun initialize(noteId: String, restartApp: (String) -> Unit) {
        launchCatching {
            _note.value = storageService.readNote(noteId) ?: DEFAULT_NOTE
        }

        observeAuthenticationState(restartApp)
    }

    private fun observeAuthenticationState(restartApp: (String) -> Unit) {
        launchCatching {
            accountService.currentUser.collect { user ->
                if (user == null) restartApp(SPLASH_SCREEN)
            }
        }
    }

    fun updateNote(newText: String) {
        _note.value = _note.value.copy(text = newText)
    }

    fun saveNote(popUpScreen: () -> Unit) {
        launchCatching {
            if (_note.value.id == NOTE_DEFAULT_ID) {
                storageService.createNote(_note.value)
            } else {
                storageService.updateNote(_note.value)
            }
        }

        popUpScreen()
    }

    fun deleteNote(popUpScreen: () -> Unit) {
        launchCatching {
            storageService.deleteNote(_note.value.id)
        }

        popUpScreen()
    }

    companion object {
        private val DEFAULT_NOTE = Note(NOTE_DEFAULT_ID, "My Note")
    }
}