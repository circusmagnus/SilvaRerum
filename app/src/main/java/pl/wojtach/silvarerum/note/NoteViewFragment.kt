package pl.wojtach.silvarerum.note

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import pl.wojtach.silvarerum.R
import pl.wojtach.silvarerum.databinding.FragmentNoteViewBinding

class NoteViewFragment : Fragment() {

    private val args: NoteViewFragmentArgs by navArgs()
    private val viewModel: SingleNoteViewModel by viewModels(
        factoryProducer = { SingleNoteViewModel.getFactory(requireActivity().application, args.noteId) }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.note_view_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.action_edit   -> {
            val direction = NoteViewFragmentDirections.actionNoteViewFragmentToNoteEditFragment2(args.noteId)
            findNavController().navigate(direction)
            true
        }
        R.id.action_delete -> {
            viewModel.deleteNote()
            findNavController().popBackStack()
            true
        }
        else               -> false
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentNoteViewBinding.inflate(inflater, container, false)
        binding.model = viewModel
        binding.controller = this
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }
}
