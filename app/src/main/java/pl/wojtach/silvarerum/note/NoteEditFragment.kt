package pl.wojtach.silvarerum.note

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import pl.wojtach.silvarerum.R
import pl.wojtach.silvarerum.databinding.FragmentNoteEditBinding

class NoteEditFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val viewModel: SingleNoteViewModel by viewModels()
        val binding: FragmentNoteEditBinding =
            DataBindingUtil.inflate(
                layoutInflater,
                R.layout.fragment_note_edit, container, false
            )
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        return binding.root
    }
}