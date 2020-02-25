package pl.wojtach.silvarerum.notes

import kotlinx.coroutines.flow.Flow
import pl.wojtach.silvarerum.NoteData
import pl.wojtach.silvarerum.NotesDao

class Notes(private val dao: NotesDao) {

    val data: Flow<List<NoteData>> = dao.observeAllNotes()

    suspend fun addNewNote() {
        val newNoteData = NoteData(content = "")
        dao.insert(newNoteData)
    }

    suspend fun delete(note: NoteData) {
        dao.delete(note)
    }
}