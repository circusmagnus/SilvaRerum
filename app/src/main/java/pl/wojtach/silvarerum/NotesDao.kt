package pl.wojtach.silvarerum

import android.content.Context
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import pl.wojtach.silvarerum.room.NotesDb

@Entity(tableName = NoteData.TABLE_NAME)
data class NoteData(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val content: String
) {
    companion object {
        const val TABLE_NAME = "notes"
    }
}

@Dao
interface NotesDao {

    @Insert
    suspend fun insert(note: NoteData): Long

    @Update
    suspend fun update(note: NoteData)

    @Delete
    suspend fun delete(note: NoteData)

    @Query("SELECT * FROM ${NoteData.TABLE_NAME}")
    fun observeAllNotes(): Flow<List<NoteData>>

    @Query("SELECT * FROM ${NoteData.TABLE_NAME} WHERE id = :id")
    fun observeNote(id: Long): Flow<NoteData>

    @Query("SELECT 'id' FROM ${NoteData.TABLE_NAME}")
    fun observeNoteIds(): Flow<List<Long>>

    companion object {
        suspend fun getInstance(appContext: Context) = NotesDb.getInstance(appContext).notesDao()
    }
}

