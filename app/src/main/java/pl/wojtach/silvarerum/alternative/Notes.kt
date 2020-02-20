package pl.wojtach.silvarerum.alternative

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import pl.wojtach.silvarerum.room.NoteData
import pl.wojtach.silvarerum.room.NotesDao

class Note(
    val id: Long,
    private val dao: NotesDao
) {

    private var currentState: NoteData? = null

    val data: Flow<NoteData> = dao.observeNote(id).distinctUntilChanged().onEach { currentState = it }

    suspend fun update(newContent: String) {
        val newVersion = currentState?.copy(content = newContent) ?: NoteData(id = id, content = newContent)
        dao.update(newVersion)
    }

    suspend fun delete() {
        val toDelete = currentState ?: NoteData(id, "")
        dao.delete(toDelete)
    }
}

class Notes(private val dao: NotesDao) {

    val data: Flow<List<Note>> = dao.observeNoteIds()
        .distinctUntilChanged()
        .map { ids -> ids.map { id -> Note(id, dao) } }

    suspend fun addNewNote() {
        val newNoteData = NoteData(content = "")
        dao.insert(newNoteData)
    }
}