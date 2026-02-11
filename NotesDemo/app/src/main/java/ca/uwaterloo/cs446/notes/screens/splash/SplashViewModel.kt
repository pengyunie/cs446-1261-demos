package ca.uwaterloo.cs446.notes.screens.splash

import ca.uwaterloo.cs446.notes.NOTES_LIST_SCREEN
import ca.uwaterloo.cs446.notes.SPLASH_SCREEN
import ca.uwaterloo.cs446.notes.model.service.AccountService
import ca.uwaterloo.cs446.notes.screens.NotesAppViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
  private val accountService: AccountService
) : NotesAppViewModel() {

  fun onAppStart(openAndPopUp: (String, String) -> Unit) {
    if (accountService.hasUser()) openAndPopUp(NOTES_LIST_SCREEN, SPLASH_SCREEN)
    else createAnonymousAccount(openAndPopUp)
  }

  private fun createAnonymousAccount(openAndPopUp: (String, String) -> Unit) {
    launchCatching {
      accountService.createAnonymousAccount()
      openAndPopUp(NOTES_LIST_SCREEN, SPLASH_SCREEN)
    }
  }
}
