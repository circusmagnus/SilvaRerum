package pl.wojtach.silvarerum.room

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val content: String
)

@Dao
interface NotesDao {

    @Insert
    suspend fun insert(note: Note): Long

    @Update
    suspend fun update(note: Note)

    @Query("SELECT * FROM notes")
    suspend fun queryAllNotes(): List<Note>

    @Query("SELECT * FROM notes")
    fun observeAllNotes(): Flow<List<Note>>

    companion object {

        suspend fun getInstance(appContext: Context) = NotesDb.getInstance(appContext).notesDao()
    }
}

@Database(entities = [Note::class], version = 1)
abstract class NotesDb : RoomDatabase() {
    abstract fun notesDao(): NotesDao

    companion object {

        private var dbInstance: NotesDb? = null

        private const val NOTES_DB_NAME = "notes_db"

        suspend fun getInstance(appContext: Context): NotesDb = Mutex().withLock {
            dbInstance ?: Room.databaseBuilder(appContext, NotesDb::class.java, NOTES_DB_NAME).build()
        }
    }
}