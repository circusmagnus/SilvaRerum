package pl.wojtach.silvarerum.alternative

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import pl.wojtach.silvarerum.room.NotesDao

class NotesViewModel(application: Application) : AndroidViewModel(application) {

    private val notes = viewModelScope.async { Notes(NotesDao.getInstance(application)) }

    val notesData = liveData {
        notes.await().data.collect { emit(it) }
    }

    val addNewNoteListener = View.OnClickListener {
        viewModelScope.launch { notes.await().addNewNote() }
    }
}