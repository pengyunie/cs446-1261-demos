package ca.uwaterloo.cs446.notes.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.uwaterloo.cs446.notes.ERROR_TAG
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

open class NotesAppViewModel : ViewModel() {
    fun launchCatching(block: suspend CoroutineScope.() -> Unit) = viewModelScope.launch(
        CoroutineExceptionHandler { _, throwable ->
            Log.d(ERROR_TAG, throwable.message.orEmpty())
        }, block = block
    )
}
