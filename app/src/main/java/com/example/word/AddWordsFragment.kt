package com.example.word


import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_add_words.*

/**
 * A simple [Fragment] subclass.
 */
class AddWordsFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add_words, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireActivity()
        val button = activity.findViewById<Button>(R.id.buttonSubmit).also { it.isEnabled = false }
        val editTextEng = activity.findViewById<EditText>(R.id.editTextEng).also { it.requestFocus() }
        val editTextChinese = activity.findViewById<EditText>(R.id.editTextChinese)
        val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(editTextEng, 0)
        val textWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                button.isEnabled = editTextEng.text.toString().isNotBlank() && editTextChinese.text.toString().isNotBlank()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        }
        editTextEng.addTextChangedListener(textWatcher)
        editTextChinese.addTextChangedListener(textWatcher)
        val wordViewModel: WordViewModel = ViewModelProviders.of(activity).get(WordViewModel::class.java)
        buttonSubmit.setOnClickListener {
            val eng = editTextEng.text.toString().trim()
            val chinese = editTextChinese.text.toString().trim()
            wordViewModel.insertWords(WordEntity(null, eng, chinese, true))
            Navigation.findNavController(it).navigateUp()
            imm.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }
}