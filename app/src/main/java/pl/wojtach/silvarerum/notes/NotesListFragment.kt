package pl.wojtach.silvarerum.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import pl.wojtach.silvarerum.NoteData
import pl.wojtach.silvarerum.databinding.FragmentNoteListBinding
import pl.wojtach.silvarerum.views.BindingListAdapter

class NotesListFragment : Fragment() {

    val notesViewModel: NotesViewModel by viewModels()

    val addNewNoteListener = View.OnClickListener {
        notesViewModel.addNewNote()
    }

    val onDeleteListener = { noteData: NoteData -> notesViewModel.delete(noteData) }
    val onEditListener = { noteData: NoteData -> /* navigate */ }
    val onClickListener = { noteData: NoteData -> /* navigate */ }

    val adapter = BindingListAdapter(NoteDiffCalc, this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentNoteListBinding.inflate(inflater, container, false)

        with(binding) {
            //            list.adapter = NotesRecyclerViewAdapter(
//                onDelete = { noteData -> viewModel.delete(noteData) },
//                onEdit = { noteData -> /* navigate */ },
//                onClick = { noteData -> /* navigate */ }
//            )
            controller = this@NotesListFragment
            viewModel = notesViewModel
            lifecycleOwner = viewLifecycleOwner
        }

        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            NotesListFragment()
    }
}
