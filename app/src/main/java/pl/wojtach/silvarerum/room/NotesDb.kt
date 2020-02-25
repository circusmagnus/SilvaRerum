package pl.wojtach.silvarerum.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import pl.wojtach.silvarerum.LazyAsync
import pl.wojtach.silvarerum.NoteData
import pl.wojtach.silvarerum.NotesDao

@Database(entities = [NoteData::class], version = 1)
abstract class NotesDb : RoomDatabase() {
    abstract fun notesDao(): NotesDao

    companion object : LazyAsync<Context, NotesDb>({ appContext ->
        val dbName = "notes_db"
        withContext(Dispatchers.Default) {
            Room.databaseBuilder(
                appContext,
                NotesDb::class.java,
                dbName
            ).build()
        }
    })
}