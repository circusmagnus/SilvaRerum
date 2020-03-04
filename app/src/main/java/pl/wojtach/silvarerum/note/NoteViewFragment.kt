package pl.wojtach.silvarerum.note

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import pl.wojtach.silvarerum.databinding.FragmentNoteViewBinding

/**
 * A simple [Fragment] subclass.
 */
class NoteViewFragment : Fragment() {

    private val args: NoteViewFragmentArgs by navArgs()
    private val viewModel: SingleNoteViewModel by viewModels(
        factoryProducer = { SingleNoteViewModel.getFactory(requireActivity().application, args.noteId) }
    )

    val deleteListener: View.OnClickListener = View.OnClickListener {
        viewModel.deleteNote()
        findNavController().popBackStack()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentNoteViewBinding.inflate(inflater, container, false)
        binding.model = viewModel
        binding.controller = this
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }
}
