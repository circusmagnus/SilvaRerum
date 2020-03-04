package pl.wojtach.silvarerum.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import pl.wojtach.silvarerum.NoteData
import pl.wojtach.silvarerum.databinding.FragmentNoteListBinding
import pl.wojtach.silvarerum.views.BindingListAdapter

class NotesListFragment : Fragment() {

    private val notesViewModel: NotesViewModel by viewModels()

    val addNewNoteListener = View.OnClickListener {
        notesViewModel.addNewNote()
    }

    val onDeleteListener = { noteData: NoteData -> notesViewModel.delete(noteData) }

    val onEditListener = { noteData: NoteData ->
        val direction = NotesListFragmentDirections.actionNotesListFragmentToNoteEditFragment2(noteData.id)
        findNavController().navigate(direction)
    }

    val onClickListener = { noteData: NoteData ->
        val direction = NotesListFragmentDirections.actionNotesListFragmentToNoteViewFragment(noteData.id)
        findNavController().navigate(direction)
    }

    val adapter = BindingListAdapter(NoteDiffCalc, this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentNoteListBinding.inflate(inflater, container, false)

        with(binding) {
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
