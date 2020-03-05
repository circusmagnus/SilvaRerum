package pl.wojtach.silvarerum.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.CoroutineStart.LAZY
import kotlinx.coroutines.async
import pl.wojtach.silvarerum.databinding.FragmentNoteListBinding
import pl.wojtach.silvarerum.room.NoteData
import pl.wojtach.silvarerum.room.NotesDao
import pl.wojtach.silvarerum.views.BindingListAdapter

class NotesListFragment : Fragment() {

    private val notesViewModel: NotesViewModel by viewModels()
    private val dao = lifecycleScope.async(start = LAZY) { NotesDao.getInstance(requireContext()) }

    val addNewNoteListener = View.OnClickListener {
        //        notesViewModel.addNewNote()
        lifecycleScope.launchWhenResumed {
            val idOfNewNote = dao.await().insert(NoteData(content = ""))
            val toNoteEdit = NotesListFragmentDirections.actionNotesListFragmentToNoteEditFragment2(idOfNewNote)
            findNavController().navigate(toNoteEdit)
        }
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
