package pl.wojtach.silvarerum.notes

import androidx.recyclerview.widget.DiffUtil
import pl.wojtach.silvarerum.NoteData
import pl.wojtach.silvarerum.views.BindingListAdapter.ListItem

object NoteDiffCalc : DiffUtil.ItemCallback<ListItem<NoteData>>() {

    override fun areItemsTheSame(oldItem: ListItem<NoteData>, newItem: ListItem<NoteData>): Boolean =
        oldItem.data.id == newItem.data.id

    override fun areContentsTheSame(oldItem: ListItem<NoteData>, newItem: ListItem<NoteData>): Boolean = oldItem == newItem
}

//class NotesRecyclerViewAdapter(
//    controller: NotesListFragment
//) : BindingListAdapter<NoteData, NotesListFragment>(NoteDiffCalc, controller)
