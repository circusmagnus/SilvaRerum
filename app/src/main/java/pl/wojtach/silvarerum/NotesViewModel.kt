package pl.wojtach.silvarerum

import android.app.Application
import android.util.Log
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import pl.wojtach.silvarerum.room.Note
import pl.wojtach.silvarerum.room.NotesDao.Companion.getInstance as notesDao

private typealias Notes = List<Note>

class NotesViewModel(application: Application) : AndroidViewModel(application) {

    val afterTextChanged: SimpleTextWatcher = object : SimpleTextWatcher {
        override fun afterTextChanged(text: String) {
            val newNote = noteNewOrCached.value?.takeUnless { it.content == text }?.copy(content = text) ?: return
            viewModelScope.launch {
                notesDao(application).update(newNote)
            }
        }
    }

    val noteNewOrCached: LiveData<Note> = liveData {
        notesDao(application).observeAllNotes().collect { notes ->
            Log.d(this@NotesViewModel::class.java.simpleName, "new notes emission: $notes")
            if (notes.isEmpty()) Note(content = "").let { note -> notesDao(application).insert(note) }
            else emit(notes.first())
        }
    }

    val notes: LiveData<Notes> = liveData {
        notesDao(application).observeAllNotes().collect { notes ->
            emit(notes)
        }
    }

    val addNote: View.OnClickListener = View.OnClickListener {
        viewModelScope.launch { notesDao(application).insert(Note(content = "")) }
    }
}