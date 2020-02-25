package pl.wojtach.silvarerum.note

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import pl.wojtach.silvarerum.NoteData
import pl.wojtach.silvarerum.NotesDao

class Note(
    val id: Long,
    private val dao: NotesDao
) {

    val data: Flow<NoteData> = dao.observeNote(id).distinctUntilChanged()

    suspend fun update(newContent: String) {
        val newVersion = NoteData(id = id, content = newContent)
        dao.update(newVersion)
    }

    suspend fun delete() {
        val toDelete = NoteData(id, "")
        dao.delete(toDelete)
    }
}