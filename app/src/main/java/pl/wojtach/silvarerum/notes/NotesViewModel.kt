package pl.wojtach.silvarerum.notes

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineStart.LAZY
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import pl.wojtach.silvarerum.NoteData
import pl.wojtach.silvarerum.NotesDao
import pl.wojtach.silvarerum.R
import pl.wojtach.silvarerum.views.BindingListAdapter

class NotesViewModel(application: Application) : AndroidViewModel(application) {

    private val notesDao = viewModelScope.async(start = LAZY) { NotesDao.getInstance(application) }

    val notesData = liveData {
        notesDao.await()
            .observeAllNotes()
            .map { notes -> notes.map { noteData -> BindingListAdapter.ListItem(noteData, R.layout.note_list_item) } }
            .collect { emit(it) }
    }

    fun addNewNote() {
        val newNoteData = NoteData(content = "")
        viewModelScope.launch { notesDao.await().insert(newNoteData) }
    }

    fun delete(note: NoteData) {
        viewModelScope.launch { notesDao.await().delete(note) }
    }
}