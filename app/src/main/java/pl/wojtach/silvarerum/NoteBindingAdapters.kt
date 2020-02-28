package pl.wojtach.silvarerum

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.databinding.BindingAdapter
import androidx.databinding.adapters.ListenerUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

//@BindingAdapter("listData")
//fun <T, VH: RecyclerView.ViewHolder> RecyclerView.setData(data: List<T>?) {
//    (this.adapter as ListAdapter<T, VH>).submitList(data ?: emptyList())
//}

@BindingAdapter("adapter", "listData")
fun <T, VH : RecyclerView.ViewHolder> RecyclerView.setListAdapter(adapter: ListAdapter<T, VH>, data: List<T>?) {
    this.adapter = adapter
    adapter.submitList(data)
}

typealias SimpleTextWatcher = (String) -> Unit

@BindingAdapter("afterTextChanged")
fun EditText.setAfterTextListener(listener: SimpleTextWatcher) {

    val new: TextWatcher? = object : TextWatcher {

        var currentText: String = this@setAfterTextListener.text.toString()
            set(value) {
                if (field != value) {
                    listener(value)
                    field = value
                }
            }

        override fun afterTextChanged(s: Editable) {
            currentText = s.toString()
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }
    }

    val old = ListenerUtil.trackListener(this, new, R.id.textWatcher)

    old?.let { this.removeTextChangedListener(old) }
    new?.let { this.addTextChangedListener(new) }
}