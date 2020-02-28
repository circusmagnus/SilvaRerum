package pl.wojtach.silvarerum.views

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import pl.wojtach.silvarerum.BR

class BindingListAdapter<T, C>(
    diffCallback: DiffUtil.ItemCallback<ListItem<T>>,
    private val controller: C
) : ListAdapter<BindingListAdapter.ListItem<T>, BindingListAdapter.ViewHolder>(diffCallback) {

    override fun getItemViewType(position: Int): Int {
        return getItem(position).viewType
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ViewDataBinding>(inflater, viewType, parent, false)
        binding.setVariable(BR.controller, controller)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = getItem(position).data
        with(holder.binding) {
            setVariable(BR.model, model)
            executePendingBindings()
        }
    }

    class ViewHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root)
    data class ListItem<T>(val data: T, @LayoutRes val viewType: Int)
}