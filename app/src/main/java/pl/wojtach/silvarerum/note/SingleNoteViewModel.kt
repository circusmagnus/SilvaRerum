package pl.wojtach.silvarerum.note

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import pl.wojtach.silvarerum.NoteData
import pl.wojtach.silvarerum.NotesDao
import pl.wojtach.silvarerum.SimpleTextWatcher

class SingleNoteViewModel(application: Application, private val noteId: Long) : AndroidViewModel(application) {

    private val note = viewModelScope.async {
        Note(
            noteId,
            NotesDao.getInstance(application)
        )
    }

    val data: LiveData<NoteData> = liveData {
        note.await().data.collect { emit(it) }
    }

    val changeListener: SimpleTextWatcher = { newContent ->
        viewModelScope.launch {
            note.await().update(newContent)
        }
    }

    val deleteListener: View.OnClickListener = View.OnClickListener {
        viewModelScope.launch { note.await().delete() }
    }
}