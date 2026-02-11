package ca.uwaterloo.cs446.notes

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ca.uwaterloo.cs446.notes.ui.theme.NotesDemoTheme
import ca.uwaterloo.cs446.notes.screens.account_center.AccountCenterScreen
import ca.uwaterloo.cs446.notes.screens.note.NoteScreen
import ca.uwaterloo.cs446.notes.screens.notes_list.NotesListScreen
import ca.uwaterloo.cs446.notes.screens.authentication.sign_in.SignInScreen
import ca.uwaterloo.cs446.notes.screens.authentication.sign_up.SignUpScreen
import ca.uwaterloo.cs446.notes.screens.splash.SplashScreen
import kotlinx.coroutines.CoroutineScope

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun NotesApp() {
    NotesDemoTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            val snackbarHostState = remember { SnackbarHostState() }
            val appState = rememberAppState(snackbarHostState)

            Scaffold(
                snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
            ) { innerPaddingModifier ->
                NavHost(
                    navController = appState.navController,
                    startDestination = SPLASH_SCREEN,
                    modifier = Modifier.padding(innerPaddingModifier)
                ) {
                    notesGraph(appState)
                }
            }
        }
    }
}

@Composable
fun rememberAppState(
    snackbarHostState: SnackbarHostState,
    navController: NavHostController = rememberNavController(),
    snackbarManager: SnackbarManager = SnackbarManager,
    coroutineScope: CoroutineScope = rememberCoroutineScope()
): NotesAppState {
    return remember(snackbarHostState, navController, snackbarManager, coroutineScope) {
        NotesAppState(snackbarHostState, navController, snackbarManager, coroutineScope)
    }
}

fun NavGraphBuilder.notesGraph(appState: NotesAppState) {
    composable(NOTES_LIST_SCREEN) {
        NotesListScreen(
            restartApp = { route -> appState.clearAndNavigate(route) },
            openScreen = { route -> appState.navigate(route) }
        )
    }

    composable(
        route = "$NOTE_SCREEN$NOTE_ID_ARG",
        arguments = listOf(navArgument(NOTE_ID) { defaultValue = NOTE_DEFAULT_ID })
    ) {
        NoteScreen(
            noteId = it.arguments?.getString(NOTE_ID) ?: NOTE_DEFAULT_ID,
            popUpScreen = { appState.popUp() },
            restartApp = { route -> appState.clearAndNavigate(route) }
        )
    }

    composable(SIGN_IN_SCREEN) {
        SignInScreen(
            openScreen = { route -> appState.navigate(route) },
            openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) }
        )
    }

    composable(SIGN_UP_SCREEN) {
        SignUpScreen(openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) })
    }

    composable(SPLASH_SCREEN) {
        SplashScreen(openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) })
    }

    composable(ACCOUNT_CENTER_SCREEN) {
        AccountCenterScreen(restartApp = { route -> appState.clearAndNavigate(route) })
    }
}
