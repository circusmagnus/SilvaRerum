package pl.wojtach.silvarerum.note

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import pl.wojtach.silvarerum.SimpleTextWatcher
import pl.wojtach.silvarerum.room.NoteData
import pl.wojtach.silvarerum.room.NotesDao

class SingleNoteViewModel(application: Application, private val noteId: Long) : AndroidViewModel(application) {

    private val notesDao = viewModelScope.async(start = CoroutineStart.LAZY) { NotesDao.getInstance(application) }

    val data = liveData {
        notesDao.await().observeNote(noteId).distinctUntilChanged().collect { emit(it) }
    }

    fun updateNote(newContent: String) {
        viewModelScope.launch {
            val newVersion = NoteData(id = noteId, content = newContent)
            notesDao.await().update(newVersion)
        }
    }

    val changeListener: SimpleTextWatcher = { newContent ->
        viewModelScope.launch {
            val newVersion = NoteData(id = noteId, content = newContent)
            notesDao.await().update(newVersion)
        }
    }

    fun deleteNote() {
        viewModelScope.launch {
            val toDelete = NoteData(id = noteId, content = "")
            notesDao.await().delete(toDelete)
        }
    }

    companion object {
        fun getFactory(application: Application, noteId: Long) = object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return SingleNoteViewModel(application, noteId) as T
            }
        }
    }
}