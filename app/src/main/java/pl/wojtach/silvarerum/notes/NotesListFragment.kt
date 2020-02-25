package pl.wojtach.silvarerum.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import pl.wojtach.silvarerum.NoteData
import pl.wojtach.silvarerum.R

class NotesListFragment : Fragment() {

    val viewModel: NotesViewModel by viewModels()

    val addNewNoteListener = View.OnClickListener {
        viewModel.addNewNote()
    }

    val deleteNoteListener = View.OnClickListener { view ->
        val toDelete = view.tag as NoteData
        viewModel.delete(toDelete)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val recyclerView = inflater.inflate(R.layout.fragment_note_list, container, false)

        if (recyclerView is RecyclerView) {
            with(recyclerView) {
                adapter = NotesRecyclerViewAdapter(
                    onDelete = { noteData -> viewModel.delete(noteData) },
                    onEdit = { noteData -> /* navigate */ }
                )
            }
        }
        return view
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            NotesListFragment()
    }
}
