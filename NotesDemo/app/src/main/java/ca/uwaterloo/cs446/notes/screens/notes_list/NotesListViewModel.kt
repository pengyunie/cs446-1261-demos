package ca.uwaterloo.cs446.notes.screens.notes_list

import ca.uwaterloo.cs446.notes.SPLASH_SCREEN
import ca.uwaterloo.cs446.notes.model.service.AccountService
import ca.uwaterloo.cs446.notes.model.service.StorageService
import ca.uwaterloo.cs446.notes.screens.NotesAppViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NotesListViewModel @Inject constructor(
    private val accountService: AccountService,
    storageService: StorageService
) : NotesAppViewModel() {
    val notes = storageService.notes

    fun initialize(restartApp: (String) -> Unit) {
        launchCatching {
            accountService.currentUser.collect { user ->
                if (user == null) restartApp(SPLASH_SCREEN)
            }
        }
    }
}