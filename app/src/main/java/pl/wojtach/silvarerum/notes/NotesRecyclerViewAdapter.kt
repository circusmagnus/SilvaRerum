package pl.wojtach.silvarerum.notes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import pl.wojtach.silvarerum.NoteData
import pl.wojtach.silvarerum.databinding.NoteListItemBinding

private object NoteDiffCalc : DiffUtil.ItemCallback<NoteData>() {

    override fun areItemsTheSame(oldItem: NoteData, newItem: NoteData): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: NoteData, newItem: NoteData): Boolean = oldItem == newItem
}

class NotesRecyclerViewAdapter(
    val onDelete: (NoteData) -> Unit,
    val onEdit: (NoteData) -> Unit
) : ListAdapter<NoteData, NotesRecyclerViewAdapter.ViewHolder>(NoteDiffCalc) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = NoteListItemBinding.inflate(inflater, parent, false)
        binding.controller = this
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val noteData = getItem(position)
        with(holder.binding) {
            note = noteData
            executePendingBindings()
        }
    }

    class ViewHolder(val binding: NoteListItemBinding) : RecyclerView.ViewHolder(binding.root)
}
