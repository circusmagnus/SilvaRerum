package pl.wojtach.silvarerum

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.databinding.BindingAdapter
import androidx.databinding.adapters.ListenerUtil

typealias SimpleTextWatcher = (String) -> Unit

@BindingAdapter("android:afterTextChanged")
fun setAfterTextListener(editText: EditText, listener: SimpleTextWatcher) {

    val new: TextWatcher? = object : TextWatcher {

        var currentText: String = editText.text.toString()
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

    val old = ListenerUtil.trackListener(editText, new, R.id.textWatcher)

    old?.let { editText.removeTextChangedListener(old) }
    new?.let { editText.addTextChangedListener(new) }
}