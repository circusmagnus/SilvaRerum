package pl.wojtach.silvarerum.room

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.Update
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import pl.wojtach.silvarerum.LazyAsync

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
    suspend fun getNote(id: Long): NoteData

    @Query("SELECT 'id' FROM ${NoteData.TABLE_NAME}")
    fun observeNoteIds(): Flow<List<Long>>

    companion object {
        suspend fun getInstance(appContext: Context) = NotesDb.getInstance(appContext).notesDao()
    }
}

@Database(entities = [NoteData::class], version = 1)
abstract class NotesDb : RoomDatabase() {
    abstract fun notesDao(): NotesDao

    companion object : LazyAsync<Context, NotesDb>({ appContext ->
        val dbName = "notes_db"
        withContext(Dispatchers.Default) { Room.databaseBuilder(appContext, NotesDb::class.java, dbName).build() }
    })
}