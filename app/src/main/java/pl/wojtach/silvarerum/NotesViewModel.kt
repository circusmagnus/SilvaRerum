package pl.wojtach.silvarerum

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import pl.wojtach.silvarerum.room.Note
import pl.wojtach.silvarerum.room.NotesDao
import pl.wojtach.silvarerum.room.NotesDb

class NotesViewModel(application: Application) : AndroidViewModel(application) {

    private val db = Room.databaseBuilder(application, NotesDb::class.java, "notes_db").build()
    private val notesDao: NotesDao = db.notesDao()

    val afterTextChanged: SimpleTextWatcher = object : SimpleTextWatcher {
        override fun afterTextChanged(text: String) {
            val newNote = noteNewOrCached.value?.takeUnless { it.content == text }?.copy(content = text) ?: return
            viewModelScope.launch {
                notesDao.update(newNote)
            }
        }
    }

    val noteNewOrCached: LiveData<Note> = liveData {
        notesDao.observeAllNotes().collect { notes ->
            Log.d(this@NotesViewModel::class.java.simpleName, "new notes emission: $notes")
            if (notes.isEmpty()) Note(content = "").let { note -> notesDao.insert(note) }
            else emit(notes.first())
        }
    }
}